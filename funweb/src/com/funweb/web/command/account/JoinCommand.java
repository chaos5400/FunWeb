package com.funweb.web.command.account;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.funweb.web.dao.AccountDao;
import com.funweb.web.daoimpl.AccountDaoImpl;
import com.funweb.web.dto.Account;
import com.funweb.web.icommand.ICommand;
import com.funweb.web.util.EMailUtils;
import com.funweb.web.util.RequestUtils;
import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException;

import jdbccontext.JdbcContext;
import jdbccontext.creator.JdbcContextCreator;
import jdbccontext.exception.DataAccessException;
import jdbccontext.exception.NoQueryDataException;


public class JoinCommand implements ICommand {

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		
		/* 회원가입 폼으로부터 입력받은 회원 정보를 Account 객체에 저장한다. */
		Account dto = new Account();
		dto.setUserID(request.getParameter("id"));
		dto.setPassword(request.getParameter("pass"));
		dto.setFirstName(request.getParameter("fname"));
		dto.setLastName(request.getParameter("lname"));
		dto.setAddress(	// 입력받은 address값을 '&'로 연결하여 데이터베이스에 저장한다.
						request.getParameter("sample4_postcode"),
						request.getParameter("sample4_roadAddress"),
						request.getParameter("sample4_jibunAddress"),
						request.getParameter("sample4_detail"));
		dto.setPhoneNumber(request.getParameter("phone"));
		dto.setMobilePhoneNumber(request.getParameter("mobile"));
		dto.setUserRole("MEMBER");	// 회원가입 폼에서 회원가입된 유저는 "MEMBER" 권한을 갖는다.
		
		
		
		/* 회원 정보를 DB에 저장한다. */
		JdbcContext jdbcContext = JdbcContextCreator.getJdbcContext();
		AccountDao dao = new AccountDaoImpl(jdbcContext);
		
		/* 
		 * 토큰값이 중복될 경우 최대 50회 까지 반복하며 그래도 실패한다면 서버 과부화를 막기 위해
		 * 에러 메시지를 띄우고 메인화면으로 이동한다.
		 */
		String token = null;
		/***** 트랜잭션 시작 *****/
		jdbcContext.setTransactinal(true);
		for(int i = 0; i < 50; i++) {
			
			try {
				
				try {
					token = dao.insertNewAccount(dto);
				} catch (NoQueryDataException e) {
					// 메일 발송시 필요한 토큰값이 생성되지 않더라도 메일 재발송 기능이 있으므로 회원가입을 진행한다.
				}
				jdbcContext.commit(); // 성공시 커밋
				break; // 토큰이 중복되지 않고 정상적으로 DB에 데이터 입력이 완료되었으면 루프를 빠져나간다.
				
			} catch (DataAccessException e) { // 토큰값이 중복되었을 경우
											  // 50회 까지 루프를 돌린다.
				jdbcContext.rollback(); // 실패시 롤백
				/* 
				   JdbcContext 안에 따로  MySQLIntegrityConstraintViolationException 처리를 하지 않은 이유는 
				      데이터베이스에 종속적인 예외이기 때문이다. 대신 instanceof를 이용해 예외를 처리하였다.
				      해당 예외는 토큰값이 중복되었을 경우에 발생한다.
				*/
				if(!(e.getCause() instanceof MySQLIntegrityConstraintViolationException)) { // 예상치 못한 예외 발생시!
					RequestUtils.setMessage(request, 
							  "회원가입 진행 도중 알 수 없는 예외가 발생하였습니다."
							+ "<br><br>잠시 후 다시 시도하여 주십시오.");
					return "/index.do";
				}
				
			}
			
		}
		/***** 트랜잭션 종료 *****/
		
		
		
		/* 인증용 이메일을 전송한다. */
		if(token != null) {
			EMailUtils eu = new EMailUtils(); 
			eu.sendMailForAuthentication(dto.getUserID(), token, request.getContextPath());
		}
		
		RequestUtils.setMessage(request, "회원가입을 축하합니다!<br><br>"
				+ "계정 활성을 위하여 메일로 발송된 인증메일의 링크를 클릭하여 주십시오.");
		
		return "/index.do";
		
	}

}
