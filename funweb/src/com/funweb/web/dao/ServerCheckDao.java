package com.funweb.web.dao;

public interface ServerCheckDao {

	/** 테이블들을 초기화(truncate)하기 위해 SET FOREIGN_KEY_CHECKS = 0 를 수행한다. */
	void setFKChecksZero();
	
	/** 테이블 초기화(truncate) 작업이 끝난 후 SET FOREIGN_KEY_CHECKS = 1 을 수행한다. */
	void setFKChecksOne();
	
}
