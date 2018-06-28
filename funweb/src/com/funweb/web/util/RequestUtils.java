package com.funweb.web.util;



import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.funweb.web.dao.BoardDao;
import com.funweb.web.dao.MainPageDao;
import com.funweb.web.daoimpl.AccountDaoImpl;
import com.funweb.web.daoimpl.MainPageDaoImpl;
import com.funweb.web.dto.NewsNotice;
import com.funweb.web.model.PaginationResult;

import jdbccontext.exception.NoQueryDataException;

public class RequestUtils {

	
	/**
	 * Request 영역의 속성에 2번째 인자로 넘겨진 메시지를 저장한다.
	 */
	public static void setMessage(HttpServletRequest request, String msg) {
		request.setAttribute("message", msg);
	}

	
	
	
	
	/**
	 * Request 영역의 message 속성의 값을 반환한다.
	 */
	public static String getMessage(HttpServletRequest request) {
		return (String) request.getAttribute("message");
	}
	
	
	
	
	
	public static String getParamMessage(HttpServletRequest request) {
		return request.getParameter("message");
	}
	
	
	
	
	
	/**
	 * Request 영역의 message 속성을 삭제한다.
	 */
	public static void removeMessage(HttpServletRequest request) {
		request.removeAttribute("message");
	}
	
	
	
	
	/**
	 * <p>메인 페이지에 News & Notice에 출력할 글 목록을 request 영역
	 * news_notice 속성에 저장한다.
	 * 
	 * @param limit 출력할 글의 개수
	 */
	public static void setNewsNotice(HttpServletRequest request, int limit) {
		MainPageDao dao = new MainPageDaoImpl("yyyy.MM.dd");
		List<NewsNotice> list = null;
		try {
			list = dao.getNewsNotice(limit);
		} catch (NoQueryDataException e) { // 데이터가 없어도 진행한다.
		}
		request.setAttribute("news_notice", list);
	}
	
	
	
	
	/**
	 * request 영역에 accountInfo 라는 속성으로 회원정보를 저장한다.
	 */
	public static void setAccountInfo(HttpServletRequest request) {
		request.setAttribute("accountInfo", new AccountDaoImpl().getAccountInfo(
				LoginManager.getIdx(request)));
	}
	
	
	
	
	/**
	 * request 영역에 paginationResult 라는 속성으로 공지사항의 글 리스트를 저장한다.
	 */
	public static <T> void setPaginationResult(HttpServletRequest request, PaginationResult<T> paginationResult) {
		request.setAttribute("paginationResult", paginationResult);
	}
	
	
	
	
	
	/**
	 * request영역 read 속성에 dto객체를 저장한다.
	 */
	public static <T> void setRead(HttpServletRequest request, T dto) {
		request.setAttribute("read", dto);
	}
	
	
	
	
	/**
	 * request영역 article 속성에 본문에 나타낼 jsp페이지를 저장한다.
	 */
	public static void setArticle(HttpServletRequest request, String article) {
		request.setAttribute("article", article);
	}
	
	
	
	
	
	
	/**
	 * 이미 접속중인 계정이 있을 경우 confirm 창을 띄어주기 위해 confirmBreakExistingUser 속성에
	 * 두 번째 인자로 전달된 message를 저장한다.
	 */
	public static void setConfirmBreakExistingUser(HttpServletRequest request, String message) {
		request.setAttribute("confirmBreakExistUserMsg", message);
	}
	
	
	
	
	/**
	 * <p>작성하는 글에 고유한 시퀀스 값을 부여하기 위해 sequence속성에 시퀀스 값을 저장한다.
	 * 
	 * <p>이 시퀀스는 업로드한 이미지에 부여되고, 글을 저장할 시 시퀀스 값을 사용하여
	 * 해당 작성 글의 이미지 경로들을 임시 DB 테이블에서 해당하는 게시판의 DB 테이블로 옮기는데 사용된다.
	 */
	public static void setSequence(HttpServletRequest request) { 
		request.setAttribute("sequence", BoardDao.getSequence());
	}
	
	
	
	
	
	/**
	 * request 영역의 sequence 파라미터의 값을 반환한다.
	 */
	public static int getSequence(HttpServletRequest request) {
		try {
			int seq = Integer.parseInt(request.getParameter("sequence"));
			return seq;
		} catch (NumberFormatException e) {
			return -1;
		}
	}
	
	
	

	/**
	 * request 영역의 no 파라미터의 값을 반환한다.
	 */
	public static int getNo(HttpServletRequest request) {
		try {
			int no = Integer.parseInt(request.getParameter("no"));
			return no;
		} catch (NumberFormatException e) {
			return -1;
		}
	}
	
	
	
	
	/**
	 * request 영역의 page 파라미터의 값을 반환한다.
	 */
	public static int getPage(HttpServletRequest request) {
		try {
			int page = Integer.parseInt(request.getParameter("page"));
			return page;
		} catch(NumberFormatException e) {
			// page 속성의 값이 숫자가 아니거나, page 속성이 존재하지 않을 경우에도
			// 예외 처리 없이 남은 로직을 정상적으로 실행한다. 
			// 이 경우 page 파라미터의 값은 1이 될 것이다.
			return 1;
		}
	}
	
	
	
	
	
	/**
	 * 현재 접속한 계정이 해당 게시글의 작성자라면 request 영역 isEqualUser 속성에
	 * true 를 설정하고, 아니라면 false 를 설정한다.
	 */
	public static void isEqualUser(HttpServletRequest request, boolean isEqualUser) {
		request.setAttribute("isEqualUser", isEqualUser);
	}
	
	
	
	
	/**
	 * 해당 게시글의 답글이 작성 가능한 유저라면 request 영역 isReplyUser 속성에
	 * true 를 설정하고, 아니라면 false 를 설정한다.
	 */
	public static void isReplyUser(HttpServletRequest request, boolean isReplyUser) {
		request.setAttribute("isReplyUser", isReplyUser);
	}
	
	
	
	/**
	 * <p>멤버 권한을 가진 유저라면 request 영역 isMemberUser 속성에
	 * true 를 설정하고, 아니라면 false를 설정한다.
	 * 
	 * <p>관리자의 경우 false를 설정한다.
	 */
	public static void isMemberUser(HttpServletRequest request, boolean isMemberUser) {
		request.setAttribute("isMemberUser", isMemberUser);
	}
	
}
