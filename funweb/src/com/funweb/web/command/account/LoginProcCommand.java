package com.funweb.web.command.account;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.funweb.web.icommand.ICommand;
import com.funweb.web.util.LoginManager;
import com.funweb.web.util.RequestUtils;

public class LoginProcCommand implements ICommand {

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		String userId = request.getParameter("id");
		
		if(userId == null) {
			RequestUtils.setMessage(request, "유효하지 않은 접근입니다.");
			return "/index.do";
		}
		
		// 로그인 작업 진행
		LoginManager loginManager = LoginManager.getInstance();
		loginManager.login(request, userId);
		
		return "/index.do";
	}

}
