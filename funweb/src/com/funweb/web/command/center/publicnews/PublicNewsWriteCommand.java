package com.funweb.web.command.center.publicnews;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.funweb.web.icommand.ICommand;
import com.funweb.web.model.BoardModel;

public class PublicNewsWriteCommand implements ICommand {
 
	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		
		@SuppressWarnings("rawtypes")
		BoardModel model = new BoardModel(request);
		model.writeBoard("publicNews/publicNewsWrite.jsp");
		
		return "/center/board.jsp";
		
	}

}
