package com.funweb.web.command.center.inquiry;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.funweb.web.dao.ReplyBoardDao;
import com.funweb.web.daoimpl.InquiryDaoImpl;
import com.funweb.web.dto.Inquiry;
import com.funweb.web.icommand.ICommand;
import com.funweb.web.model.BoardModel;

public class InquiryListCommand implements ICommand {

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		ReplyBoardDao<Inquiry> dao = new InquiryDaoImpl("yyyy.MM.dd");
		BoardModel<Inquiry> model = new BoardModel<>(request, dao);
		
		int page = model.listMemberBoard("inquiry/inquiryList.jsp", 15, 10);
		
		return "/center/board.jsp?page=" + page;

	}

}
