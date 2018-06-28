package com.funweb.web.model;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.funweb.web.dao.BoardDao;
import com.funweb.web.dao.ReplyBoardDao;
import com.funweb.web.dao.TempImageDao;
import com.funweb.web.daoimpl.TempImageDaoImpl;
import com.funweb.web.exception.InCorrectUserException;
import com.funweb.web.util.FileUtils;
import com.funweb.web.util.LoginManager;
import com.funweb.web.util.RequestUtils;

import jdbccontext.exception.NoQueryDataException;
import jdbccontext.exception.NoUpdateDataException;

public class BoardModel<T> {
	
	private HttpServletRequest request;
	
	private BoardDao<T> dao;// DB 작업을 처리할 DAO 객체
	private int no;     	// 게시글의 번호
	
	
	public BoardModel(HttpServletRequest request, BoardDao<T> dao) {
		this.dao = dao;
		this.request = request;
		this.no = RequestUtils.getNo(request);
	}
	
	
	
	
	public BoardModel(HttpServletRequest request) {
		this.request = request;
	}
	
	
	
	
	
	/**
	 * 임시 이미지를 서버에서 전부 삭제하고, DB에 저장되어 있는
	 * 임시 이미지 경로를 전부 삭제한다.
	 */
	public static void deleteAllTempImages() {
		
		TempImageDao dao = new TempImageDaoImpl();
		
		List<String> paths = null;
		
		try {
			// DB에서 모든 임시 이미지 경로들을 가져온다.
			paths = dao.getAllTempImagePath();
		} catch (NoQueryDataException e) {
			return;
		} 
		
		FileUtils.deleteFiles(paths);
		
		// DB에서 모든 임시 이미지 경로들을 삭제한다.
		dao.truncateImagePathTable();
		
	}
	
	
	
	
	
	
	/**
	 * <p>게시글을 삭제한다.
	 * 
	 * <p>게시글 작성자만 삭제할 수 있다.
	 * 
	 * <p>게시글에 첨부된 이미지를 서버에서 삭제한다.
	 * 
	 * @throws InCorrectUserException 올바른 유저가 아닐시
	 * @throws NoQueryDataException 게시글이 존재하지 않는다면
	 * @throws NoUpdateDataException 게시글이 정상적을 삭제되지 못하였다면
	 */
	public void deleteBoard() throws InCorrectUserException, NoQueryDataException, NoUpdateDataException {
		
		deleteContext();
		
		// 게시글 삭제는 이미지 첨부 여부와 상관없이 반드시 해야한다.
		// DB의 임시 이미지 경로 컬럼에 ON DELETE CASCADE 옵션을 주었기 때문에
		// 게시글 삭제시 임시 이미지 경로들도 같이 삭제된다.
		
		if (0 == dao.deleteBoard(no)) { // 삭제된 데이터가 없다면
			throw new NoUpdateDataException();
		}
		
	}
	
	
	

	
	public void deleteReplyBoard() {
		
		deleteContext();
		
		// 게시글 삭제는 이미지 첨부 여부와 상관없이 반드시 해야한다.
		// DB의 임시 이미지 경로 컬럼에 ON DELETE CASCADE 옵션을 주었기 때문에
		// 게시글 삭제시 임시 이미지 경로들도 같이 삭제된다.
		if (0 == dao.deleteBoard(no)) { // 삭제된 데이터가 없다면
			throw new NoUpdateDataException();
		}
		
	}
	
	
	
	
	
	private void deleteContext() {
		
		/***** 게시글 작성자인지 확인 ****/
		boolean isEqualUser = this.isEqualUser();
		if(!isEqualUser) { // 게시글 작성자가 아니라면
			throw new InCorrectUserException(); // 예외 발생!
		}
		/***** 게시글 작성자인지 확인 ****/
		
		List<String> paths = null;
		
		try {
			// DB에서 해당하는 게시글의 이미지 경로들을 가져온다.
			paths = dao.getImagePath(no);
			
			// 해당 경로의 이미지들을 전부 삭제한다.
			FileUtils.deleteFiles(paths);
		} catch (NoQueryDataException e) { // 첨부된 이미지가 없더라도 진행한다.
		}
		
	}
	
	
	
	
	
	/**
	 * 게시글의 목록을 가져와 request영역 paginationResult 속성으로 저장한다.
	 * 
	 * @param article 본문 페이지
	 * @param maxResult 한 화면에 출력할 글의 개수
	 * @param pagePerBlock 한 블럭당 페이지 수
	 * @return 페이지 번호
	 */
	public int listBoard(String article, int maxResult, int pagePerBlock) {
		
		int page = RequestUtils.getPage(request);

		
		
		// 글 목록 리스트와 페이지 리스트를 가져와 request 영역에 paginationResult 속성으로 저장한다.
		RequestUtils.setPaginationResult(
									request,
									new PaginationResult<T>(
												dao, 			// DB에 접속할 DAO객체(글 생성 날짜 형식)
												page,			// 현재 페이지
												maxResult,		// 한 화면에 출력할 최대 글 개수
												pagePerBlock));	// 한 블럭당 페이지 수

		// 고객문의 게시판으로 이동
		RequestUtils.setArticle(request, article);
		
		return page;
		
	}
	
	
	
	
	
	/**
	 * <p>멤버 유저만 글 쓰기 기능을 제공한다.
	 * 관리자의 경우에는 글을 쓸 수가 없다.
	 * 
	 * <p>게시글의 목록을 가져와 request영역 paginationResult 속성으로 저장한다.
	 * 
	 * @param article 본문 페이지
	 * @param maxResult 한 화면에 출력할 글의 개수
	 * @param pagePerBlock 한 블럭당 페이지 수
	 * @return 페이지 번호
	 */
	public int listMemberBoard(String article, int maxResult, int pagePerBlock) {
		/***** 멤버 유저인지 확인 *****/
		RequestUtils.isMemberUser(request, this.isMemberUser());
		/***** 멤버 유저인지 확인 *****/
		return this.listBoard(article, maxResult, pagePerBlock);
	}
	
	
	
	
	
	/**
	 * 게시판 글을 DB에서 가져와 request영역 read 속성에 저장하고
	 * 본문 페이지를 request영역 article 속성에 저장한다.
	 * 
	 * @param article 본문 페이지
	 * @throws NoQueryDataException 게시글이 존재하지 않는다면
	 */
	public void readBoard(String article) throws NoQueryDataException {

		// 파라미터로 들어온 no를 이용하여 DB에서 데이터를 검색하여 저장한다.
		// 그리고 ReadCount를 1증가한다.
		RequestUtils.setRead(request, dao.getBoard(no));
		dao.upRead(no);
		
		
		// 본문 페이지를 지정한다.
		RequestUtils.setArticle(request, article);
		
		
		/***** 게시글 작성자인지 확인 ****/
		boolean isEqualUser = this.isEqualUser();
		RequestUtils.isEqualUser(request, isEqualUser);
		/***** 게시글 작성자인지 확인 ****/
		
	}
	
	
	
	
	
	
	/**
	 * 답글 기능이 있는 게시판을 읽어들인다.
	 * 
	 * @param article 본문 페이지
	 * @throws NoQueryDataException 게시글이 존재하지 않는다면
	 * @throws BoardPassNotEqualException 게시글 패스워드가 일치하지 않는다면
	 */
	public void readReplyBoard(String article) throws NoQueryDataException {
		
		/* 게시글을 읽어들이고 */
		this.readBoard(article);
		
		/***** 답글 작성이 가능한 유저인지 확인 *****/
		boolean isReplyUser = this.isReplyUser();
		RequestUtils.isReplyUser(request, isReplyUser);
		/***** 답글 작성이 가능한 유저인지 확인 *****/
		
	}
	
	
	
	
	
	
	/**
	 * 로그인한 계정의 패스워드와
	 * 
	 * @return
	 * @throws NoQueryDataException
	 */
	public boolean isEqualPass() throws NoQueryDataException, NullPointerException {
		
		String pass1 = ((ReplyBoardDao<T>) dao).getBoardPassword(no);
		String pass2 = request.getParameter("pass");
		
		if(pass2 == null) {
			throw new NullPointerException();
		}
		
		if(!pass1.equals(pass2)) { // 일치하지 않는다면
			return false;
		}
		
		return true;
		
	}
	
	
	
	
	
	/**
	 * 글 쓰기 페이지 이동시 필요한 데이터를 request 영역에 저장한다.
	 * 
	 * @param article 본문 페이지
	 */
	public void writeBoard(String article) {
		/* 글의 시퀀스 값을 request 영역에 저장한다. */
		RequestUtils.setSequence(request);

		RequestUtils.setArticle(request, article);
	}
	
	
	
	
	
	/**
	 * <p>글 쓰기 작업을 수행한다.(dto객체의 데이터를 DB에 저장한다.)
	 * 
	 * @param dto 게시글 정보가 저장된 객체
	 * @throws NoUpdateDataException 데이터가 정상적으로 저장되지 못했을 시
	 */
	public void writeProcBoard(T dto) throws NoUpdateDataException {
		int key = dao.writeBoard(dto);
		copyImages(key);
	}
	
	
	
	
	
	
	private void copyImages(int key) {
		/** 임시로 저장된 이미지 경로들을 해당 게시판의 이미지 경로 저장 테이블로 복사한다. */
		int seq = RequestUtils.getSequence(request);
		int res = dao.copyImagePath(
						key,
						seq);
		
		if(res != 0) { // 복사된 이미지 경로가 없을 경우
					   // 삭제할 임시 이미지 경로가 없다는 뜻이므로 삭제 작업을 스킵한다.
			
			// 해당 글의 임시 이미지 경로들을 DB에서 삭제한다.
			TempImageDao dao2 = new TempImageDaoImpl();
			dao2.deleteTempImagePath(seq);

		}
		/** 임시로 저장된 이미지 경로들을 해당 게시판의 이미지 경로 저장 테이블로 복사한다. */
	}
	
	
	
	
	
	
	/**
	 * 멤버만 글을 쓸 수 있는 곳에 올바르지 않은 유저가 접근하면
	 * InCorrectUserException 예외를 강제로 발생시킨다.
	 * 
	 * @param dto 게시글 정보가 저장된 객체
	 * @throws InCorrectUserException 올바른 유저가 아닐시
	 * @throws NoUpdateDataException 데이터가 정상적으로 저장되지 못했을 시
	 */
	public void writeProcOnlyMember(T dto) throws InCorrectUserException, NoUpdateDataException {
		if(!this.isMemberUser()) { // 멤버 유저가 아니라면
			throw new InCorrectUserException();
		}
		this.writeProcBoard(dto);
	}
	
	
	
	
	
	
	public void replyProcBoard(T dto, int replyNo) throws NoUpdateDataException {
		/***** 답글 작성이 가능한 유저인지 확인 *****/
		boolean isReplyUser = this.isReplyUser();
		if(!isReplyUser) { return; } // 답글작성이 불가능한 유저라면 return 한다.
		/***** 답글 작성이 가능한 유저인지 확인 *****/
		int key = ((ReplyBoardDao<T>) dao).replyBoard(dto, replyNo);
		copyImages(key);
	}
	
	
	
	
	
	/**
	 * 접속한 계정이 게시글을 작성한 계정이라면 true, 아니라면 false 반환
	 * 
	 * @return 게시글을 작성한 계정이라면 true, 아니면 false
	 * @throws NoQueryDataException 게시글이 존재하지 않는다면
	 */
	public boolean isEqualUser() throws NoQueryDataException {
		
		int userIdx = this.getUserIdx();
		
		if(userIdx == -1) { // 로그인하지 않았다면
			return false;	
		}
		
		int boardIdx = this.getBoardIdx();
		
		return userIdx == boardIdx;
		
	}
	
	
	
	
	
	
	/**
	 * 접속한 계정이 홈페이지의 멤버라면 true, 아니라면 false 반환
	 * 관리자의 경우 false를 반환한다.
	 * 
	 * @return 멤버라면 true, 아니면 false
	 */
	public boolean isMemberUser() {
		return "MEMBER".equals(LoginManager.getUserRole(request));
	}
	
	
	
	
	
	
	/**
	 * 답글을 쓸 수 있는 유저인지 확인하여 답글을 쓸 수 있는 유저라면 true, 아니면 false 반환
	 * 
	 * @return 답글 쓰기 가능한 유저면 true, 아니면 false
	 * @throws NoQueryDataException 게시글이 존재하지 않는다면
	 */
	public boolean isReplyUser() throws NoQueryDataException {

		// 로그인하지 않았다면 답글을 쓸 수 없다.
		int userIdx = this.getUserIdx();
		if(userIdx == -1) { 
			return false;
		}
		
		int boardIdx = this.getBoardIdx();
		
		// 본인의 게시글이 아니어야 하고,
		if(userIdx != boardIdx) {
			
			// 관리자일 경우에는 본인의 게시글이 아니면 어느 글에라도 답글 작성이 가능하다.
			if("ADMIN".equals(LoginManager.getUserRole(request))) {
				return true;
			}
			
			// 일반유저라면
			// 본인이 문의했던 글의 답글에 새로 답글 쓰기가 가능하다.
			int InquiryIdx =((ReplyBoardDao<T>) dao).getIdxWithGroupNo(no);
			if(InquiryIdx == userIdx) {
				return true;
			}
			
		}
		
		// 어디에도 해당되지 않으면 false
		return false;
		
	}

	
	
	
	
	public boolean isReplyReader() throws NoQueryDataException {
		
		/****** 게시글을 패스워드 입력없이 보려면 *****/ 
		// 로그인한 유저가 아니라면 패스워드를 입력해야 한다.
		int userIdx = this.getUserIdx();
		if(userIdx == -1) {
			return false;
		}
		
		// 관리자의 경우 모든 게시글을 볼 수 있다.
		if("ADMIN".equals(LoginManager.getUserRole(request))) {
			return true;
		}
		
		// 자기가 작성한 글의 그룹에 속한다면 모두 패스워드 입력없이 볼 수 있다.
		// => 관리자가 답변한 글도 볼수 있다.
		int InquiryIdx = ((ReplyBoardDao<T>) dao).getIdxWithGroupNo(no);
		if(InquiryIdx == userIdx) {
			return true;
		}
		
		// 그 외에는 패스워드 입력이 필요하다.
		return false;
		
	}
	
	
	
	
	
	/**
	 * <p>로그인한 계정의 인덱스를 반환한다.
	 * 로그인 되지 않았다면 -1을 반환한다.
	 * 
	 * @return 로그인한 상태이면 계정의 인덱스, 로그인한 유저가 아니라면 -1
	 */
	private int getUserIdx() {
		return LoginManager.getIdx(request);
	}
	
	
	
	
	
	
	/**
	 * <p>게시글 작성자의 인덱스를 반환한다.
	 * 
	 * <p>만약 게시글이 존재하지 않을 경우 NoQueryDataException 이
	 * 발생한다.
	 * 
	 * @return 게시글 작성자의 인덱스
	 * @throws NoQueryDataException 게시글이 존재하지 않는다면
	 */
	private int getBoardIdx() throws NoQueryDataException {
		return dao.getIdx(this.no);
	}
	
}
