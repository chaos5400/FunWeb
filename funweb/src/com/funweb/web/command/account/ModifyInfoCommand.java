package com.funweb.web.command.account;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.funweb.web.icommand.ICommand;
import com.funweb.web.util.RequestUtils;

public class ModifyInfoCommand implements ICommand {

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		// 회원 정보를 가져와서 accountInfo 라는 속성이름으로 저장한다.
		RequestUtils.setAccountInfo(request);
		
		return "/member/info/modifyInfo.jsp";
		
	}

}
