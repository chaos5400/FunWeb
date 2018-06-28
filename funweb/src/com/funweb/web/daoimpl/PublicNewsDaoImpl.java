package com.funweb.web.daoimpl;

import java.util.List;

import com.funweb.web.dao.BoardDao;
import com.funweb.web.dto.PublicNews;

import jdbccontext.JdbcContext;
import jdbccontext.creator.JdbcContextCreator;



public class PublicNewsDaoImpl implements BoardDao<PublicNews> {

	
private JdbcContext jdbcContext;
	
	private String format;	// 데이터베이스 내 저장된 '글 생성 날짜'의 형식을 지정할 포맷 변수
	
	
	
	public PublicNewsDaoImpl() {
		this(null);
	}
	
	
	
	public PublicNewsDaoImpl(String format) {
		this(JdbcContextCreator.getJdbcContext(), format);
	}
	
	
	
	public PublicNewsDaoImpl(JdbcContext jdbcContext, String format) {
		this.jdbcContext = jdbcContext;
		this.format = format;
	}
	
	
	
	
	
	@Override
	public int getIdx(int no) {
		return jdbcContext.queryForObject(
					"SELECT Idx FROM PublicNews WHERE No = " + no, 
					rs -> {
						return rs.getInt(1);
					});
	}

	
	
	
	
	
	@Override
	public int getCountRows() {
		return jdbcContext.countRows("PublicNews");
	}


	
	
	
	@Override
	public List<PublicNews> getBoardList(int offset, int limit) {
		return jdbcContext.queryForObjects(
					"SELECT No, Title, Writer, CreateDate, ReadCount "
				  + "FROM PublicNews ORDER BY 1 DESC LIMIT " + limit + " OFFSET " + offset, 
					rs -> {
						PublicNews dto = new PublicNews();
						dto.setNo(rs.getInt(1));
						dto.setTitle(rs.getString(2));
						dto.setWriter(rs.getString(3));
						dto.setCreateDate(rs.getTimestamp(4), format);
						dto.setReadCount(rs.getInt(5));
						return dto;
					});
		
	}
	
	
	
	
	
	
	@Override
	public int writeBoard(PublicNews dto) {
		return jdbcContext.insertAndGetGeneratedKeys(
				"INSERT INTO PublicNews (Idx, Title, Content, Writer) VALUES (?, ?, ?, ?)",
				dto.getIdx(), dto.getTitle(), dto.getContent(), dto.getWriter());
	}
	
	
	
	
	@Override
	public int copyImagePath(int no, int seq) {
		return jdbcContext.executeUpdate(
			    "INSERT INTO PublicNewsImages (No, Path) "
			  + "SELECT ?, Path FROM BoardTempImages WHERE Seq = ?", no, seq);
	}
	
	
	
	
	
	@Override
	public List<String> getImagePath(int no) {
		return jdbcContext.queryForObjects(
				"SELECT Path FROM PublicNewsImages WHERE No = " + no, 
				rs -> {
					return rs.getString(1);
				});
	}
	
	
	
	
	
	@Override
	public void deleteImagePath(int no) {
		jdbcContext.executeUpdate("DELETE FROM PublicNewsImages WHERE No = " + no);
	}
	
	
	
	
	
	@Override
	public PublicNews getBoard(int no) {
		return jdbcContext.queryForObject(
						"SELECT Title, Content, Writer, CreateDate, ReadCount "
					+ 	"FROM PublicNews WHERE No = " + no,
						rs -> {
							PublicNews dto = new PublicNews();
							dto.setTitle(rs.getString(1));
							dto.setContent(rs.getString(2));
							dto.setWriter(rs.getString(3));
							dto.setCreateDate(rs.getTimestamp(4), format);
							dto.setReadCount(rs.getInt(5));
							return dto;
						});
	}
	
	
	
	
	
	
	@Override
	public int deleteBoard(int no) {
		return jdbcContext.executeUpdate("DELETE FROM PublicNews WHERE No = " + no);
	}
	
	
	
	
	@Override
	public int upRead(int no) {
		return jdbcContext.executeUpdate("UPDATE PublicNews Set ReadCount = ReadCount + 1 WHERE No = " + no);
	}

}
