package com.funweb.web.task;

import java.util.TimerTask;

import com.funweb.web.dao.ServerCheckDao;
import com.funweb.web.daoimpl.ServerCheckDaoImpl;
import com.funweb.web.model.BoardModel;

public class ServerCheckTask extends TimerTask {

	ServerCheckDao scDao = new ServerCheckDaoImpl();
	
	@Override
	public void run() {
		// DB 테이블 초기화(truncate)를 위하여 
		// 테이블 초기화시 FK 제약조건을 검사하지 않도록 한다.
		scDao.setFKChecksZero();
		
		// 임시 파일 삭제 작업 수행
		BoardModel.deleteAllTempImages();
		
		// DB 테이블 초기화(truncate) 작업이 끝난 후
		// FK 제약조건 검사를 원래대로 되돌린다.
		scDao.setFKChecksOne();
	}
	
}
