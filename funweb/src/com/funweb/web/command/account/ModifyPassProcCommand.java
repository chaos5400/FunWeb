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

public class ModifyPassProcCommand implements ICommand {

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		int idx = LoginManager.getIdx(request);
		String originPass = request.getParameter("originPass");
		String newPass = request.getParameter("newPass");
		
		AccountDao dao = new AccountDaoImpl();
		
		if(!originPass.equals(dao.getPassword(idx))) {
			// 입력한 기존의 비밀번호가 틀리다면 다시 비밀번호 입력 창으로 이동한다.
			RequestUtils.setMessage(request, "기존 비밀번호가 틀립니다. 다시 입력하여 주십시오."); 
			return "/modifyPass.do";
		}
		
		if(newPass.equals(originPass)) {
			// 새 비밀번호가 입력한 기존의 비밀번호와 같은 비밀번호라면 다시 비밀번호 입력 창으로 이동한다.
			RequestUtils.setMessage(request, "기존 비밀번호와 다른 비밀번호를 입력하여 주십시오."); 
			return "/modifyPass.do";
		}
		
		// DB에 접속하여 새로운 비밀번호를 저장한다.
		dao.updateNewPassword(idx, newPass);
		
		// 내 정보 페이지로 이동한다.
		RequestUtils.setMessage(request, "비밀번호가 변경되었습니다.");
		return "/info.do";
		
	}

}
