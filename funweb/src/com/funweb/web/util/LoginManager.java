package com.funweb.web.util;

import java.util.Enumeration;
import java.util.Hashtable;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionBindingEvent;
import javax.servlet.http.HttpSessionBindingListener;

import com.funweb.web.dao.AccountDao;
import com.funweb.web.daoimpl.AccountDaoImpl;
import com.funweb.web.dto.Account;
import com.funweb.web.exception.DoesNotExistUserException;
import com.funweb.web.exception.EmailDoesNotActiveException;
import com.funweb.web.exception.LoginFailException;
import com.funweb.web.exception.IsUsingUserException;
import com.funweb.web.exception.NotEqualPassException;

import jdbccontext.exception.NoQueryDataException;

public class LoginManager implements HttpSessionBindingListener {

	private static LoginManager loginManager = null;
	
	private Hashtable<HttpSession, String> loginUsers = new Hashtable<>();
	
	
	private LoginManager() {
	}
	
	
	
	/* 싱글톤 패턴 사용 */
	public static LoginManager getInstance() {
		if(loginManager == null) {
			synchronized (LoginManager.class) {
				if(loginManager == null) {
					loginManager = new LoginManager();
				}
			}
		}
		return loginManager;
	}
	
	
	
	
	
	public static Integer getIdx(HttpServletRequest request) {
		try {
			int res = (Integer) request.getSession().getAttribute("idx");
			return res;
		} catch (NullPointerException e) {
			return -1;
		}
	}
	
	
	
	
	
	public static String getUserID(HttpServletRequest request) {
		try {
			String res =(String) request.getSession().getAttribute("userId");
			return res;
		} catch (NullPointerException e) {
			return null;
		}
	}
	
	
	
	
	
	public static String getUserRole(HttpServletRequest request) {
		try {
			String res = (String) request.getSession().getAttribute("userRole");
			return res;
		} catch (NullPointerException e) {
			return null;
		}
	}
	
	
	
	
	
	/**
	 * <p>세션이 등록될 때 호출되는 메서드이다.
	 * 
	 * <p>즉, 로그인 성공시에 호출된다.
	 */
	@Override
	public void valueBound(HttpSessionBindingEvent event) {
		
		// 세션과 세션 이름을 해쉬테이블에 저장한다.
		loginUsers.put(event.getSession(), event.getName());
		
		// 계정 아이디를 사용하여 유저 권한과 Idx값을 가져온다.
		AccountDao dao = new AccountDaoImpl();
		Account dto = dao.getLoginInfo(event.getName());
		
		// 세션 영역에 로그인 후 필요한 값들을 저장한다.
		// Idx, UserId, UserRole
		event.getSession().setAttribute("idx", dto.getIdx());
		event.getSession().setAttribute("userId", event.getName());
		event.getSession().setAttribute("userRole", dto.getUserRole());
		
		System.out.println(event.getName() + "님이 로그인 하셨습니다.");
		
	}



	
	/**
	 * <p>세션이 삭제(invalidate())될 때 호출되는 메서드이다.
	 * 
	 * <p>세션이 타임아웃되어 invalidate()될 때에도 호출된다.
	 */
	@Override
	public void valueUnbound(HttpSessionBindingEvent event) {
		loginUsers.remove(event.getSession());
		System.out.println(event.getName() + "님이 로그아웃 하셨습니다.");
	}
	
	
	
	
	
	/**
	 * <p>로그인 작업을 진행한다.
	 * 
	 * <p>접속중인 계정인지 먼저 확인하여 접속중인 계정이라면
	 * 기존 연결을 종료하고 새로 접속한다.
	 * 
	 * <p>하나의 계정으로 동시 접속을 허용하지 않는다!
	 * 
	 * @param userId	접속을 시도할 계정의 아이디
	 */
	public void login(HttpServletRequest request, String userId) {
		// 이미 접속중인 계정이라면 세션에서 삭제시킨다.
		if (this.isUsing(userId)) {
			this.removeSession(userId);
		}

		// 아무런 예외도 발생하지 않았다면 정상적으로 로그인 한다.
		request.getSession().setAttribute(userId, this);
	}
	
	
	
	
	
	/**
	 * 로그아웃 작업을 진행한다.
	 */
	public void logout(HttpServletRequest request) {
		request.getSession().invalidate();
	}
	
	
	
	
	
	/**
	 * <p>계정의 아이디를 사용하여 해당 아이디의 계정을
	 * 세션에서 삭제한다.
	 * 
	 * @param userId 세션에서 삭제할 계정의 아이디
	 */
	public void removeSession(String userId) {
		Enumeration<HttpSession> e = loginUsers.keys();
		HttpSession session = null;
		
		while (e.hasMoreElements()) {
			
			session = e.nextElement();
			
			if (loginUsers.get(session).equals(userId)) {
				// 세션이 invalidate될때 HttpSessionBindingListener를
				// 구현하는 클레스의 valueUnbound()함수가 호출된다.
				session.invalidate();
			}
			
		}
		
	}
	

	
	
	
	/**
	 * <p>로그인 유효성 검사를 실시하여 로그인 작업이 유효한지를 판단한다.
	 *
	 * <p>만약, 유효성 검사에 실패한다면 각각의 상황에 맞는 예외를 발생시켜
	 * 로그인에 실패하였음을 알려준다.
	 * 
	 * <ul>예외의 종류
	 * 	<li> DoesNotExistUserException 사용자가 입력한 계정이 존재하지 않음
	 * 	<li> NotEqualPassException 사용자가 입력한 패스워드가 일치하지 않음
	 * 	<li> EmailDoesNotActiveException 이메일 인증을 완료하지 않았음
	 * 	<li> IsUsingUserException 이미 접속중인 계정
	 * </ul>
	 * 
	 * @param userId 	유효성 검사를 실시할 계정의 아이디
	 * @param pass		유효성 검사를 실시할 계정의 패스워드
	 * @throws LoginFailException 로그인에 실패할 때 각 상황에 맞는 예외
	 */
	public void isValid(String userId, String pass) throws LoginFailException {
		
		try {
			
			// 로그인 검사시 필요한 정보들을 가져온다.
			AccountDao dao = new AccountDaoImpl();
			Account dto = dao.getLoginValidationInfo(userId);
			
			// 패스워드가 일치하지 않으면
			if(!pass.equals(dto.getPassword())) {
				throw new NotEqualPassException();
			}
			
			// 이메일 인증을 완료하였는지 확인한다.
			if(!dto.isEMailActive()) {
				// 이메일 인증을 완료하지 않았다면
				throw new EmailDoesNotActiveException();
			}

			// 이미 사용중인 아이디인지 확인한다.
			if(this.isUsing(userId)) {
				throw new IsUsingUserException();
			}
			
		} catch (NoQueryDataException e) {
			
			throw new DoesNotExistUserException(e);
			
		}
		
	}
	
	
	
	
	
	
	/**
	 * <p>계정의 아이디를 사용하여 내부 해쉬 테이블에 등록되어 있는 유저인지 확인한다.
	 * 
	 * @param userId 접속중인지 확인할 계정의 아이디
	 * @return 접속중이면 true, 아니면 false
	 */
	private boolean isUsing(String userId) {
		return loginUsers.containsValue(userId);
	}
	
}
