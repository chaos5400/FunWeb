package com.funweb.web.command.account;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.funweb.web.dao.AccountDao;
import com.funweb.web.daoimpl.AccountDaoImpl;
import com.funweb.web.icommand.ICommand;
import com.funweb.web.util.RequestUtils;
import com.funweb.web.util.LoginManager;

public class DeleteAccountProcCommand implements ICommand {

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		int idx = LoginManager.getIdx(request);
		String pass = request.getParameter("pass");

		AccountDao dao = new AccountDaoImpl();
		
		// 패스워드가 일치하지 않으면
		if (!pass.equals(dao.getPassword(idx))) {
			// 다시 패스워드 입력창으로 이동한다.
			RequestUtils.setMessage(request, "패스워드가 일치하지 않습니다.");
			return "/confirmPassword.do";
		}
		
		// DB에 저장된 회원 정보를 삭제한다.
		dao.deleteAccount(idx);
		
		RequestUtils.setMessage(request, "회원 탈퇴가 완료 되었습니다.");
		
		// 회원탈퇴 후 로그아웃을 진행한다. 
		return "/logout.do";
		
	}

}
