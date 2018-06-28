package com.funweb.web.daoimpl;

import java.util.List;

import com.funweb.web.dao.TempImageDao;

import jdbccontext.JdbcContext;
import jdbccontext.creator.JdbcContextCreator;

public class TempImageDaoImpl implements TempImageDao {
	
	private JdbcContext jdbcContext;
	
	
	
	
	
	public TempImageDaoImpl() {
		this(JdbcContextCreator.getJdbcContext());
	}

	
	
	
	
	public TempImageDaoImpl(JdbcContext jdbcContext) {
		this.jdbcContext = jdbcContext;
	}
	
	
	
	
	
	/**
	 * 모든 임시 이미지 경로들을 가져와 리스트 형태로 반환한다.
	 */
	@Override
	public List<String> getAllTempImagePath() {
		return jdbcContext.queryForObjects(
				"SELECT Path FROM BoardTempImages", 
				rs -> {
					return rs.getString(1);
				});
	}
	
	
	
	
	
	/**
	 * DB에 이미지 저장 경로와 작성하고 있는 글의 시퀀스 값을 저장한다.
	 * 
	 * @param no			작성 글의 시퀀스 값
	 * @param uploadPath	이미지 업로드 경로
	 */
	@Override
	public void insertTempImagePath(int seq, String uploadPath) {
		jdbcContext.executeUpdate(
				"INSERT INTO BoardTempImages(Seq, Path) VALUES(?, ?)", seq, uploadPath);
	}
	
	
	
	
	
	/**
	 * 작성 글의 시퀀스 값이 일치하는 DB에 저장된 이미지 경로 전부를 삭제한다.
	 * 
	 * @param seq 작성 글의 시퀀스 값
	 */
	@Override
	public void deleteTempImagePath(int seq) {
		jdbcContext.executeUpdate("DELETE FROM BoardTempImages WHERE Seq = " + seq);
	}
	
	
	
	
	
	/**
	 * 임시 이미지 경로 테이블을 초기화시킨다.
	 */
	@Override
	public void truncateImagePathTable() {
		jdbcContext.executeUpdate("Truncate BoardSequence");
	}
	
}
