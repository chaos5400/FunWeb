package com.funweb.web.daoimpl;

import com.funweb.web.dao.ServerCheckDao;

import jdbccontext.JdbcContext;
import jdbccontext.creator.JdbcContextCreator;

public class ServerCheckDaoImpl implements ServerCheckDao {

	private JdbcContext jdbcContext;
	
	
	public ServerCheckDaoImpl() {
		this(JdbcContextCreator.getJdbcContext());
	}
	
	
	/* JdbcContext를 인자로 받는 생성자를 만든 이유는 
	 * 이 클래스 객체를 사용하는 곳에서 JdbcContext 
	 * 클래스 내부에 있는 트랜잭션 기능을 사용하기 위해서다 */
	public ServerCheckDaoImpl(JdbcContext jdbcContext) {
		this.jdbcContext = jdbcContext;
	}
	
	
	
	@Override
	public void setFKChecksZero() {
		jdbcContext.executeUpdate("SET FOREIGN_KEY_CHECKS = 0");
	}

	
	
	@Override
	public void setFKChecksOne() {
		jdbcContext.executeUpdate("SET FOREIGN_KEY_CHECKS = 1");
	}

}
