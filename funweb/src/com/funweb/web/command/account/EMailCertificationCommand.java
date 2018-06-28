package com.funweb.web.command.account;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.funweb.web.dao.AccountDao;
import com.funweb.web.daoimpl.AccountDaoImpl;
import com.funweb.web.icommand.ICommand;
import com.funweb.web.util.RequestUtils;


public class EMailCertificationCommand implements ICommand {

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {

		// uri 에 적힌 token 값을 얻어온다.
		String token = request.getParameter("token");
		
		AccountDao dao = new AccountDaoImpl();
		
		// 얻어온 토큰을 사용하여 계정을 활성화 시킨다.
		if(!dao.activeAccountByToken(token)) {
			// 계정 활성화 실패시 토큰값이 유효하지 않은 것으로 간주하고 경고메시지를 던져주고 메인 페이지로 이동한다.
			RequestUtils.setMessage(request, "유효하지 않은 접근입니다.<br>메일 재발송을 통해 유효한 링크를 얻으시기 바랍니다.");
			return "/index.do";
		}
		
		// 이메일 인증을 완료하면 메시지를 저장하고 메인화면으로 이동한다.
		RequestUtils.setMessage(request, "이메일 인증이 완료되었습니다.<br>로그인 후 이용하여 주십시오.");
		return "/index.do";
		
	}

}
