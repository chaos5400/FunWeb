package com.funweb.web.dao;

import java.util.List;

public interface TempImageDao {
	
	/** 임시 이미지 경로 저장 테이블의 모든 경로를 가져온다. */
	List<String> getAllTempImagePath();
	
	/** 이미지 실제 경로 저장 */
	void insertTempImagePath(int seq, String uploadPath);
	
	/** 시퀀스에 해당하는 글의 저장된 임시 이미지 경로를 삭제한다. */
	void deleteTempImagePath(int seq);
	
	/** 임시 이미지 경로 테이블을 초기화 시킨다. */
	void truncateImagePathTable();
	
}
