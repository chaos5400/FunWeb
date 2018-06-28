package com.funweb.web.command.main;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.funweb.web.icommand.ICommand;
import com.funweb.web.util.RequestUtils;

public class MainPageCommand implements ICommand {

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		// Notice와 PublicNews에서 최신 5개의 글을 읽어들여 
		// request 영역 news_notice 영역에 저장한다.
		RequestUtils.setNewsNotice(request, 5);
		
		// 메인화면으로 이동
		return "/index.jsp";
		
	}

}
