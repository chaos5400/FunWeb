package com.funweb.web.dao;

import java.util.List;

import jdbccontext.JdbcContext;
import jdbccontext.creator.JdbcContextCreator;
import jdbccontext.exception.NoQueryDataException;
import jdbccontext.exception.NoUpdateDataException;

public interface BoardDao<T> {
	
	final JdbcContext jdbcContext = JdbcContextCreator.getJdbcContext();
	
	
	
	/** 
	 * 글을 작성한 계정의 인덱스 값을 가져온다. 
	 * 
	 * @param 글 번호
	 */
	int getIdx(int no);
	
	
	
	/**
	 * 게시판의 총 글의 개수를 반환한다.
	 * 
	 * @return 게시판의 총 글의 개수
	 */
	int getCountRows() throws NoQueryDataException;
	
	
	
	/** 
	 * 테이블의 offset부터 limit 개수만큼의 글 목록을 리스트 형태로 반환
	 * 
	 * @param offset 데이터를 읽어올 테이블의 인덱스
	 * @param limit 읽어올 행의 개수
	 * @return T타입 객체의 리스트
	 */
	List<T> getBoardList(int offset, int limit) throws NoQueryDataException;
	
	
	
	/** 
	 * 입력한 내용을 DB에 저장하고 auto_increment 값을 반환한다. 
	 * 
	 * @param dto DB에 저장할 게시글의 정보를 담고있는 객체
	 * @return DB에 입력한 행의 AUTO_INCREMENT 값
	 */
	int writeBoard(T dto) throws NoUpdateDataException;
	
	
	
	/** 
	 * 임시 이미지 경로 저장 테이블에 있는 이미지 경로를 특정 게시판의 이미지 경로 저장 테이블로 이동시킨다.
	 * no는 새로 생성한 게시판의 No값이고 seq는 작성중이던 글의 시퀀스 값이다.
	 * 
	 * @param no 생성한 게시글의 번호
	 * @param seq 작성하던 글의 시퀀스
	 * @return 이동한 이미지 경로 개수
	 */
	int copyImagePath(int no, int seq);
	
	
	
	/** 
	 * 각 게시판 글의 No 값에 해당하는 이미지 경로들을 리스트 형태로 가져온다. 
	 * 
	 * @param no 글 번호
	 * @return 게시글의 첨부된 이미지 경로 리스트
	 */
	List<String> getImagePath(int no);
	
	
	
	/** 
	 * 게시글의 첨부된 이미지 경로를 DB 이미지 경로 저장 테이블에서 삭제한다. 
	 * 
	 * @param no 글 번호
	 */
	void deleteImagePath(int no);
	
	
	
	/** 
	 * 글 번호를 이용하여 해당 게시글 정보를 가져온다. 
	 * 
	 * @param no 글 번호
	 * @return 게시글 정보를 담은 객체
	 */
	T getBoard(int no) throws NoQueryDataException;
	
	
	
	/** 
	 * 글 번호에 해당하는 게시글을 삭제한다. 
	 * 
	 * @param no 글 번호
	 * @return 성공하였다면 1, 실패하였다면 0
	 */
	int deleteBoard(int no);
	
	
	
	/** 
	 * 조회수를 1 증가시킨다.
	 * 
	 * @param no 글 번호
	 * @return 성공하였다면 1, 실패하였다면 0
	 */
	int upRead(int no);
	
	
	
	/** 
	 * 작성 글의 시퀀스 값을 얻는다. 
	 * 
	 * @return 새로 작성하는 글의 시퀀스 값
	 */
	static int getSequence() {
		int seq = 0;
		synchronized(BoardDao.class) {
			seq = jdbcContext.insertAndGetGeneratedKeys("INSERT INTO BoardSequence Values()");
		}
		return seq;
	}
	
}
