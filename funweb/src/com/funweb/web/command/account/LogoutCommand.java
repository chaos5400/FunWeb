package com.funweb.web.command.account;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.funweb.web.icommand.ICommand;
import com.funweb.web.util.LoginManager;

public class LogoutCommand implements ICommand {

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		
		// 세션에서 계정을 삭제한다.
		LoginManager loginManager = LoginManager.getInstance();
		loginManager.logout(request);
		
		return "/index.do";
		
	}

}
