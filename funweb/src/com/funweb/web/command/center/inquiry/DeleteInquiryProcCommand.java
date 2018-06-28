package com.funweb.web.command.center.inquiry;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.funweb.web.dao.BoardDao;
import com.funweb.web.daoimpl.InquiryDaoImpl;
import com.funweb.web.dto.Inquiry;
import com.funweb.web.error.ErrorPage;
import com.funweb.web.exception.InCorrectUserException;
import com.funweb.web.icommand.ICommand;
import com.funweb.web.model.BoardModel;
import com.funweb.web.util.RequestUtils;

import jdbccontext.exception.NoQueryDataException;
import jdbccontext.exception.NoUpdateDataException;

public class DeleteInquiryProcCommand implements ICommand {

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		try {
			BoardDao<Inquiry> dao = new InquiryDaoImpl();
			
			BoardModel<Inquiry> model = new BoardModel<Inquiry>(request, dao);
			
			model.deleteBoard();
		} catch (NoQueryDataException e) { // 게시글이 존재하지 않을 때
			RequestUtils.setMessage(request, 
					"게시글이 존재하지 않습니다.");
		} catch (InCorrectUserException e) { // 올바르지 않은 유저가 작업을 수행했을 때
			return ErrorPage.FORBIDDEN_ERROR_PAGE;
		} catch (NoUpdateDataException e) {
			RequestUtils.setMessage(request, 
					  "게시글이 올바르게 삭제되지 못하였습니다.<br><br>"
					+ "잠시 후 다시 시도하여 주십시오.");
		}
		
		return "/inquiry.do";
	}

}
