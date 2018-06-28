package com.funweb.web.dao;

public interface ReplyBoardDao<T> extends BoardDao<T> {
	
	/**
	 * 게시글의 그룹 번호와 게시글 번호가 일치하는
	 * 게시글 작성자 계정의 인덱스 값을 가져온다.
	 * 
	 * @param no 게시글 번호
	 * @return 게시글 작성자 계정의 인덱스
	 */
	int getIdxWithGroupNo(int no);
	
	
	
	
	/**
	 * 게시글의 패스워드를 반환
	 * 
	 * @param no 글 번호
	 * @return 게시글 패스워드
	 */
	String getBoardPassword(int no);
	
	
	
	
	
	/**
	 * <p>게시글의 답글을 DB에 저장한다.
	 * 
	 * <p>답글을 작성중인 글의 번호를 2번째 매개변수로 넘겨받아
	 * 테이블에 저장해둔다. 이 글 번호는 해당 글이 지워졌을 시
	 * 그 밑에 달린 답글을 전부 삭제하기 위해서다. 
	 * 
	 * @param dto 답글의 정보를 담은 객체
	 * @param no 답글을 작성중인 글의 번호
	 * @return DB에 입력한 행의 AUTO_INCREMENT 값
	 */
	int replyBoard(T dto, int replyNo);
	
	
	
	
	
	/**
	 * 게시글의 BGroup, Step, Indent 값을 가져온다.
	 * 
	 * @param 글 번호
	 * @return BGroup, Step, Indent 값
	 */
	T getReplyInfo(int no);
	
}
