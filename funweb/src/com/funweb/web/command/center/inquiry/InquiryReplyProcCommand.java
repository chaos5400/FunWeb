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
import com.funweb.web.util.LoginManager;
import com.funweb.web.util.RequestUtils;

import jdbccontext.exception.NoQueryDataException;
import jdbccontext.exception.NoUpdateDataException;

public class InquiryReplyProcCommand implements ICommand {

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		/* 작성한 글을 DB에 저장한다. */
		ReplyBoardDao<Inquiry> rdao = new InquiryDaoImpl();
		BoardModel<Inquiry> model = new BoardModel<Inquiry>(request, rdao);
		
		
		
		// DB에서 게시글의 BGroup, Step, Indent 값을 가져온다.
		// 가져온 객체에 추가적으로 폼 데이터를 저장하여 답변쓰기 작업을 한다.
		int no = RequestUtils.getNo(request);
		if(no == -1) { // 글 번호가 없을 시
			RequestUtils.setMessage(request, "잘못된 접근입니다. 다시 시도하여 주십시오.");
			return "inquiry.do";
		}
		
		Inquiry dto = null;
		
		/***** 답글 작성중 취소할 수 있으므로 BGroup, Step, Indent는 실제 DB 쓰기 작업이 이뤄지기전에 DB에서 가져온다. *****/
		try {
			dto = rdao.getReplyInfo(no);
		} catch (NoQueryDataException e) { // 게시글이 존재하지 않을 경우
			RequestUtils.setMessage(request, "게시글이 존재하지 않습니다.");
			return "inquiry.do";
		}
		
		dto.setIdx(LoginManager.getIdx(request));
		dto.setTitle(request.getParameter("title"));
		dto.setContent(request.getParameter("content"));
		dto.setWriter(LoginManager.getUserID(request));
		dto.setPassword(request.getParameter("pass"));
		
		
		try {
			model.replyProcBoard(dto, no);
		} catch (NoUpdateDataException e) { // 글이 정상적으로 저장되지 못했을 때
			RequestUtils.setMessage(request, "글이 정상적으로 저장되지 못하였습니다. 다시 시도하여 주십시오.");
			return "/inquiry.do";
		}
		
		return "/inquiry.do";
		
	}

}
