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
import com.funweb.web.util.RequestUtils;

import jdbccontext.exception.NoQueryDataException;

public class InquiryReadCommand implements ICommand {

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		ReplyBoardDao<Inquiry> rdao = new InquiryDaoImpl("yyyy.MM.dd hh:mm:ss");
		BoardModel<Inquiry> model = new BoardModel<Inquiry>(request, rdao);
		
		try {
			// 본문 페이지를 매개변수로 넘겨주고,
			// 화면에 표시할 글을 DB에서 가져와 request 영역에 저장한다.
			model.readReplyBoard("inquiry/inquiryRead.jsp");
		} catch (NoQueryDataException e) { // 게시글이 존재하지 않을 때
			RequestUtils.setMessage(request, 
					"게시글이 존재하지 않습니다.");
		}
		
		return "/center/board.jsp";
		
	}

}
