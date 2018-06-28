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

public class NoticeListCommand implements ICommand {

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		BoardDao<Notice> dao = new NoticeDaoImpl("yyyy.MM.dd");
		BoardModel<Notice> model = new BoardModel<>(request, dao);
		
		int page = model.listBoard("notice/noticeList.jsp", 15, 10);
		
		return "/center/board.jsp?page=" + page;

	}

}
