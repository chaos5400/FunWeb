package com.funweb.web.daoimpl;

import java.util.List;

import com.funweb.web.dao.BoardDao;
import com.funweb.web.dto.Notice;

import jdbccontext.JdbcContext;
import jdbccontext.creator.JdbcContextCreator;

public class NoticeDaoImpl implements BoardDao<Notice> {

	private JdbcContext jdbcContext;
	
	private String format;	// 데이터베이스 내 저장된 '글 생성 날짜'의 형식을 지정할 포맷 변수
	
	
	
	
	
	public NoticeDaoImpl() {
		this(null);
	}
	
	
	
	public NoticeDaoImpl(String format) {
		this(JdbcContextCreator.getJdbcContext(), format);
	}
	
	
	
	public NoticeDaoImpl(JdbcContext jdbcContext, String format) {
		this.jdbcContext = jdbcContext;
		this.format = format;
	}
	
	
	
	
	
	@Override
	public int getIdx(int no) {
		return jdbcContext.queryForObject(
					"SELECT Idx FROM Notice WHERE No = " + no, 
					rs -> {
						return rs.getInt(1);
					});
	}
	
	
	
	
	
	@Override
	public int getCountRows() {
		return jdbcContext.countRows("Notice");
	}


	
	
	
	@Override
	public List<Notice> getBoardList(int offset, int limit) {
		return jdbcContext.queryForObjects(
					"SELECT No, Title, Writer, CreateDate, ReadCount "
				  + "FROM Notice ORDER BY 1 DESC LIMIT " + limit + " OFFSET " + offset, 
					rs -> {
						Notice dto = new Notice();
						dto.setNo(rs.getInt(1));
						dto.setTitle(rs.getString(2));
						dto.setWriter(rs.getString(3));
						dto.setCreateDate(rs.getTimestamp(4), format);
						dto.setReadCount(rs.getInt(5));
						return dto;
					});
		
	}
	
	
	
	
	
	
	@Override
	public int writeBoard(Notice dto) {
		return jdbcContext.insertAndGetGeneratedKeys(
				"INSERT INTO Notice (Idx, Title, Content, Writer) VALUES (?, ?, ?, ?)",
				dto.getIdx(), dto.getTitle(), dto.getContent(), dto.getWriter());
	}
	
	
	
	
	@Override
	public int copyImagePath(int no, int seq) {
		return jdbcContext.executeUpdate(
			    "INSERT INTO NoticeImages (No, Path) "
			  + "SELECT ?, Path FROM BoardTempImages WHERE Seq = ?", no, seq);
	}
	
	
	
	
	
	@Override
	public List<String> getImagePath(int no) {
		return jdbcContext.queryForObjects(
				"SELECT Path FROM NoticeImages WHERE No = " + no, 
				rs -> {
					return rs.getString(1);
				});
	}
	
	
	
	
	
	@Override
	public void deleteImagePath(int no) {
		jdbcContext.executeUpdate("DELETE FROM NoticeImages WHERE No = " + no);
	}
	
	
	
	
	
	@Override
	public Notice getBoard(int no) {
		return jdbcContext.queryForObject(
						"SELECT Title, Content, Writer, CreateDate, ReadCount "
					+ 	"FROM Notice WHERE No = " + no,
						rs -> {
							Notice dto = new Notice();
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
		return jdbcContext.executeUpdate("DELETE FROM Notice WHERE No = " + no);
	}
	
	
	
	
	public int upRead(int no) {
		return jdbcContext.executeUpdate("UPDATE Notice Set ReadCount = ReadCount + 1 WHERE No = " + no);
	}
	
}
