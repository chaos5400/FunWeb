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

public class InquiryConfirmUserCommand implements ICommand {

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		ReplyBoardDao<Inquiry> rdao = new InquiryDaoImpl();
		BoardModel<Inquiry> model = new BoardModel<>(request, rdao);
		
		try {
			if(!model.isReplyReader()) { 	// 게시글을 패스워드 없이 볼수 없는 유저라면
				RequestUtils.setArticle(request, "inquiry/confirmPass.jsp"); // 패스워드 확인 페이지로 이동한다.
				return "/center/board.jsp";
			}
		} catch (NoQueryDataException e) { // 게시글이 존재하지 않는다면
			RequestUtils.setMessage(request, 
					"게시글이 존재하지 않습니다.");
			return "inquiry.do";
		}
		
		// 게시글 읽기 페이지로 이동한다.
		return "inquiryRead.do";
		
	}

}
