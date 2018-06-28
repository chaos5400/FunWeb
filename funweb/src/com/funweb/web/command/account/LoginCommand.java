package com.funweb.web.command.account;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.funweb.web.exception.DoesNotExistUserException;
import com.funweb.web.exception.EmailDoesNotActiveException;
import com.funweb.web.exception.IsUsingUserException;
import com.funweb.web.exception.NotEqualPassException;
import com.funweb.web.icommand.ICommand;
import com.funweb.web.util.RequestUtils;
import com.funweb.web.util.LoginManager;

public class LoginCommand implements ICommand {

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		
		// 로그인 정보를 받아서 존재하는 아이디인지 확인하고 패스워드가 일치하는지 확인한다.
		String userId = request.getParameter("id");
		String pass = request.getParameter("pass");
		
		try {
			
			// 로그인에 대한 유효성 검사를 실시한다.
			LoginManager loginManager = LoginManager.getInstance();
			loginManager.isValid(userId, pass);
			
			// 유효성 검사를 모두 통과하였다면 정상적으로 로그인한다.
			return "/loginProc.do";
			
		} catch (DoesNotExistUserException e) { 	// 계정 정보가 존재하지 않으면
			
			RequestUtils.setMessage(request, "계정 정보가 존재하지 않습니다.");
			return "/login.do";
			
		} catch (NotEqualPassException e) {			// 패스워드가 일치하지 않으면
			
			RequestUtils.setMessage(request, "비밀번호가 일치하지 않습니다.");
			return "/login.do";
			
		} catch (EmailDoesNotActiveException e) { 	// 이메일 인증을 완료하지 않았다면
			
			// 이메일 인증 페이지로 이동한다.
			return "/emailauthentication.do";
			
		} catch (IsUsingUserException e) {			// 이미 접속해 있는 계정이면
			
			// 로그인 페이지로 이동하여 기존 접속을 끊고 접속할 것인지
			// 아니면 기존 접속을 유지하고 새로운 접속을 거부할 것인지 확인하는
			// 확인 메시지를 띄어준다.
			RequestUtils.setConfirmBreakExistingUser(request, 
						"<br>이미 접속중인 계정입니다.<br><br>"
					  + "접속중인 계정을 로그아웃하고 새로 접속하시겠습니까?");
			return "/login.do";
			
		}
		
	}

}
