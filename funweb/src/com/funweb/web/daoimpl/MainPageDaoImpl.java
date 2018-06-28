package com.funweb.web.daoimpl;

import java.util.List;

import com.funweb.web.dao.MainPageDao;
import com.funweb.web.dto.NewsNotice;

import jdbccontext.JdbcContext;
import jdbccontext.creator.JdbcContextCreator;

public class MainPageDaoImpl implements MainPageDao {

	private JdbcContext jdbcContext;
	
	private String format;
	
	
	
	
	public MainPageDaoImpl() {
		this(null);
	}
	
	
	
	
	public MainPageDaoImpl(String format) {
		this(JdbcContextCreator.getJdbcContext(), format);
	}
	
	
	
	
	
	/* JdbcContext를 인자로 받는 생성자를 만든 이유는 
	 * 이 클래스 객체를 사용하는 곳에서 JdbcContext 
	 * 클래스 내부에 있는 트랜잭션 기능을 사용하기 위해서다 */
	public MainPageDaoImpl(JdbcContext jdbcContext, String format) {
		this.jdbcContext = jdbcContext;
		this.format = format;
	}
	
	
	
	
	
	@Override
	public List<NewsNotice> getNewsNotice(int limit) {
		return jdbcContext.queryForObjects(
					"SELECT BoardNo, No, Title, CreateDate "
			      + "FROM ("
			      + "SELECT BoardNo, No, Title, CreateDate FROM Notice "
			      + "UNION ALL "
			      + "SELECT BoardNo, No, Title, CreateDate FROM PublicNews) A "
			      + "ORDER BY 4 DESC LIMIT " + limit,
					rs -> {
						NewsNotice dto = new NewsNotice();
						dto.setBoardNo(rs.getInt(1));
						dto.setNo(rs.getInt(2));
						dto.setTitle(rs.getString(3));
						dto.setCreateDate(rs.getTimestamp(4), format);
						return dto;
					});
	}

}
