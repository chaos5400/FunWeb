package com.funweb.web.command.center.publicnews;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.funweb.web.dao.BoardDao;
import com.funweb.web.daoimpl.PublicNewsDaoImpl;
import com.funweb.web.dto.PublicNews;
import com.funweb.web.icommand.ICommand;
import com.funweb.web.model.BoardModel;

public class PublicNewsListCommand implements ICommand {
	 
	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		BoardDao<PublicNews> dao = new PublicNewsDaoImpl("yyyy.MM.dd");
		BoardModel<PublicNews> model = new BoardModel<>(request, dao);
		
		int page = model.listBoard("publicNews/publicNewsList.jsp", 15, 10);
		
		return "/center/board.jsp?page=" + page;

	}

}
