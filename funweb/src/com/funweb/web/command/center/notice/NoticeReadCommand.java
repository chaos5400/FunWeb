package com.funweb.web.command.center.notice;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.funweb.web.dao.BoardDao;
import com.funweb.web.daoimpl.NoticeDaoImpl;
import com.funweb.web.dto.Notice;
import com.funweb.web.icommand.ICommand;
import com.funweb.web.model.BoardModel;
import com.funweb.web.util.RequestUtils;

import jdbccontext.exception.NoQueryDataException;

public class NoticeReadCommand implements ICommand {

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		 
		BoardDao<Notice> dao = new NoticeDaoImpl("yyyy.MM.dd hh:mm:ss");
		BoardModel<Notice> model = new BoardModel<Notice>(request, dao);
		
		try {
			// 본문 페이지를 매개변수로 넘겨주고,
			// 화면에 표시할 글을 DB에서 가져와 request 영역에 저장한다.
			model.readBoard("notice/noticeRead.jsp");
		} catch (NoQueryDataException e) { // 게시글이 존재하지 않을 때
			RequestUtils.setMessage(request, 
					"게시글이 존재하지 않습니다.");
		}
		
		return "/center/board.jsp";
		
	}

}
