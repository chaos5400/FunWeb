package com.funweb.web.model;

import java.util.List;

import com.funweb.web.dao.BoardDao;

import jdbccontext.exception.NoQueryDataException;


public class PaginationResult<T> { 
	
	private int currentPage;					// 현재 페이지
	private int totalRecord;					// 전체 글 수
	private int totalPage;						// 전체 페이지수
	private List<T> boardList;					// 글 데이터를 저장할 변수
	
	private int nowBlock;						// 현재 페이지 블럭
	private int totalBlock;						// 총 페이지 블럭
	private int beginPage;						// 시작 페이지
	private int endPage;						// 끝 페이지
	
	
	/**
	 * <p>페이징 처리를 하는 함수이다.
	 * 
	 * <p>화면에 출력할 글 목록 리스트를 DB에서 가져와서 멤버 변수에 저장하고,
	 * 글 페이지 목록에 출력할 글 페이지 시작 번호와 끝 번호를 구하여 멤버 변수에 저장한다.
	 * 
	 * @param dao			DB관련 처리를 할 DAOIMPL 객체
	 * @param page			선택된 페이지
	 * @param maxResult		한 페이지에 출력할 최대 글 개수
	 * @param pagePerBlock	하단 페이지 목록에 출력할 최대 페이지 수
	 */
	public PaginationResult(BoardDao<T> dao, int page,
			int maxResult, int pagePerBlock) {
		
		// 전체 글 수와 beginIndex부터 endIndex까지의 인덱스에 해당하는 데이터를
		// DB에서 가져와 멤버 변수 list에 저장한다.
		this.totalRecord = dao.getCountRows();
		
		// 총 페이지수를 구한다.
		this.totalPage = (int) Math.ceil((double) totalRecord / maxResult);
		
		// 현재 페이지가 총 페이지수를 넘는다면 총 페이지수를 현재페이지로 지정한다.
		this.currentPage = page > totalPage ? totalPage : page;
		
		// 현재 페이지가 1보다 작다면 1로 지정한다.
		if(currentPage < 1) currentPage = 1;
		
		// 페이지의 인덱스를 얻는다.
		int pageIndex = currentPage - 1;
		
		
		int offset = pageIndex * maxResult;		// 시작 인덱스(non-exclude)
		
		
		try {
			this.boardList = (List<T>) dao.getBoardList(offset, maxResult);
		} catch (NoQueryDataException e) {
			// 글 목록에 글이 하나도 없더라도 상관없이 진행한다.
			currentPage = 0;
			this.boardList = null;
		}
		
		
		// 현재 페이지 블럭을 구한다.
		this.nowBlock = pageIndex / pagePerBlock;
		// 총 페이지 블럭을 구한다.
		this.totalBlock = (totalPage - 1) / pagePerBlock;
		
		
		// 화면에 출력할 시작 페이지와 끝 페이지를 구한다.
		this.beginPage = nowBlock * pagePerBlock + 1;
		this.endPage = beginPage + pagePerBlock - 1;
		
		
		// 마지막 페이지가 총 페이지수를 넘었다면 마지막 페이지를 총 페이지수로 지정한다.
		if(endPage > totalPage) { 
			endPage = totalPage;
		}
		
	}


	
	

	public int getCurrentPage() {
		return currentPage;
	}


	public int getTotalRecord() {
		return totalRecord;
	}


	public int getTotalPage() {
		return totalPage;
	}

	
	public List<T> getBoardList() {
		return boardList;
	}


	public int getNowBlock() {
		return nowBlock;
	}


	public int getTotalBlock() {
		return totalBlock;
	}


	public int getBeginPage() {
		return beginPage;
	}


	public int getEndPage() {
		return endPage;
	}

}
