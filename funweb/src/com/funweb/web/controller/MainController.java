package com.funweb.web.controller;

import java.io.IOException;
import java.net.URLEncoder;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.funweb.web.commandfactory.CommandAndViewFactory;
import com.funweb.web.error.ErrorPage;
import com.funweb.web.icommand.ICommand;
import com.funweb.web.scheduler.ServerCheckScheduler;
import com.funweb.web.util.LoginManager;
import com.funweb.web.util.RequestUtils;






@WebServlet("*.do")
public class MainController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private ActionForward actionForward = null;
	
	
	public MainController() {
	}

	
	@Override
	public void init() throws ServletException {
		@SuppressWarnings("unused")
		// 서버 점검 스케쥴러를 등록한다.
		ServerCheckScheduler scheduler = new ServerCheckScheduler();
	}
	
	
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		service(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		service(request, response);
	}

	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		
		request.setCharacterEncoding("UTF-8");
		
		
		/* 루트 경로를 뺀 나머지 주소를 구한다. */
		String url = request.getRequestURI();
		String conPath = request.getContextPath();
		String com = url.substring(conPath.length());
		
		System.out.println("com : " + com);
		
		
		/* 먼저 로그인 하지 않아도 접근 가능한 작업을 처리하고 뷰페이지를 얻어온다. */
		String viewPage = processNonSecurity(request, response, com);
		
		
		
		/* 선택된 페이지가 없다면 */
		if (viewPage == null) {
			/* 로그인된 유저만을 위한 작업을 처리하고 뷰페이지를 얻어온다. */
			viewPage = processForLoginUser(request, response, com);
			/* 그래도 선택된 페이지가 없다면 관리자만을 위한 작업을 처리하고 뷰페이지를 얻어온다. */
			if(viewPage == null) {
				viewPage = processForAdmin(request, response, com);
			}
		}
		
		
		
		/***************************************************** 
		 * 글쓰기, 글삭제, 회원탈퇴 등 폼 Submit 이 수행된 후에는 
		 * 리다이렉트 하여 기존의 request 객체를 버려서 
		 * 리다이렉트 된 페이지에서 새로고침(F5)키를 눌렀을 때
		 * 이전 작업이 반복 실행되지 않도록 한다. 
		 *****************************************************/
		if(actionForward.isRedirect()) {
			viewPage = conPath + viewPage;
			
			/* 리다이렉트시 메시지를 클라이언트까지 가져가기 위해서 메시지가 있다면 파라미터에 추가시킨다. */
			String message = RequestUtils.getMessage(request);
			if((message = RequestUtils.getMessage(request)) != null);
			else if((message = RequestUtils.getParamMessage(request)) != null);
			
			if(message != null) {
				viewPage += "?message=" + URLEncoder.encode(message, "UTF-8");
			}
			
			response.sendRedirect(viewPage);
		} else {
			RequestDispatcher dispatcher = request.getRequestDispatcher(viewPage);
			dispatcher.forward(request, response);
		}

	}

	
	
	
	
	/**
	 * <p>관리자를 위한 뷰 페이지를 얻어서 반환한다.
	 * 로그인 되지 않은 사용자가 접근하려 할 시 403 에러 페이지로 이동한다.
	 * 
	 * @return 뷰 페이지
	 */
	private String processForAdmin(HttpServletRequest request, HttpServletResponse response, String com) 
			throws ServletException, IOException {
		
		boolean isNotAdmin = !"ADMIN".equals(LoginManager.getUserRole(request));
		
		/* 관리자가 아닌 유저가 접근시 */
		if(isNotAdmin) { return ErrorPage.FORBIDDEN_ERROR_PAGE; }
		
		actionForward = CommandAndViewFactory.getViewPageForAdmin(com);
		
		String viewPage = actionForward.getViewPage();
		
		// 아무런 페이지도 얻지 못했다면 해당하는 url이 없었다는 것이므로
		// 커맨드 객체를 사용하여 뷰페이지를 얻어온다.
		if(viewPage == null) {
			
			actionForward = CommandAndViewFactory.getCommandForAdmin(com);
			
			ICommand command = actionForward.getCommand();
			
			if(command != null) { // 얻어온 커맨드 객체가 있다면
				
				viewPage = command.execute(request, response);
				
			}
			
		}
		
		return viewPage;
		
	}
	
	
	
	
	
	
	/**
	 * <p>로그인된 유저를 위한 뷰 페이지를 얻어서 반환한다.
	 * 로그인 되지 않은 사용자가 접근하려 할 시 403 에러 페이지로 이동한다.
	 * 
	 * @return 뷰 페이지
	 */
	private String processForLoginUser(HttpServletRequest request, HttpServletResponse response, String com) 
			throws ServletException, IOException {
		
		boolean isNotLoginUser = (LoginManager.getUserID(request) == null);
		
		/* 로그인 되지 않은 사용자 접근시 */
		if(isNotLoginUser) { return ErrorPage.FORBIDDEN_ERROR_PAGE; }
		
		actionForward = CommandAndViewFactory.getViewPageForLoginUser(com);
		
		String viewPage = actionForward.getViewPage();
		
		// 아무런 페이지도 얻지 못했다면 해당하는 uri가 없었다는 것이므로
		// 커맨드 객체를 사용하여 뷰페이지를 얻어온다.
		if(viewPage == null) {
			
			actionForward = CommandAndViewFactory.getCommandForLoginUser(com);
			
			ICommand command = actionForward.getCommand();
			
			if(command != null) { // 얻어온 커맨드 객체가 있다면
				
				viewPage = command.execute(request, response);
				
			}
			
		}
		
		return viewPage;
		
	}

	
	
	
	
	/**
	 * <p>로그인 여부와 상관없이 접근할 수 있는 뷰페이지를
	 * 얻어서 반환한다.
	 * 
	 * @return 뷰 페이지
	 */
	private String processNonSecurity(HttpServletRequest request, HttpServletResponse response, String com)
			throws ServletException, IOException {
		
		actionForward = CommandAndViewFactory.getViewPageNonSecurity(com);
		
		String viewPage = actionForward.getViewPage();
		
		// 아무런 페이지도 얻지 못했다면 해당하는 url이 없었다는 것이므로
		// 커맨드 객체를 사용하여 뷰페이지를 얻어온다.
		if(viewPage == null) {
			
			actionForward = CommandAndViewFactory.getCommandNonSecurity(com);
			
			ICommand command = actionForward.getCommand();
			
			if(command != null) { // 얻어온 커맨드 객체가 있다면
				viewPage = command.execute(request, response);
			}
		}
		
		return viewPage;
		
	}

}
