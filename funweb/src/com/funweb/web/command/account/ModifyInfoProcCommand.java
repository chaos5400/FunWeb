package com.funweb.web.command.account;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.funweb.web.daoimpl.AccountDaoImpl;
import com.funweb.web.dto.Account;
import com.funweb.web.icommand.ICommand;
import com.funweb.web.util.LoginManager;

public class ModifyInfoProcCommand implements ICommand {

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		// 폼에서 입력받은 정보를 Account 객체에 저장한다.
		Account a = new Account();
		a.setUserID(LoginManager.getUserID(request));
		a.setFirstName(request.getParameter("fname"));
		a.setLastName(request.getParameter("lname"));
		a.setAddress( // 입력받은 address값을 '&'로 연결하여 address 변수에 저장한다.
						request.getParameter("sample4_postcode"),
						request.getParameter("sample4_roadAddress"),
						request.getParameter("sample4_jibunAddress"),
						request.getParameter("sample4_detail"));
		a.setPhoneNumber(request.getParameter("phone"));
		a.setMobilePhoneNumber(request.getParameter("mobile"));
		
		
		
		// 입력받은 정보를 DB에 업데이트 한다.
		new AccountDaoImpl().updateAccountInfo(a);
		
		// 내 정보 페이지로 이동한다.
		return "/info.do";
		
	}

}
