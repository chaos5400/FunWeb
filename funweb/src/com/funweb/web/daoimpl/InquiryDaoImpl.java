package com.funweb.web.daoimpl;

import java.util.List;

import com.funweb.web.dao.ReplyBoardDao;
import com.funweb.web.dto.Inquiry;

import jdbccontext.JdbcContext;
import jdbccontext.creator.JdbcContextCreator;
import jdbccontext.exception.NoQueryDataException;
import jdbccontext.exception.NoUpdateDataException;

public class InquiryDaoImpl implements ReplyBoardDao<Inquiry> {

	private JdbcContext jdbcContext;
	
	private String format;	// 데이터베이스 내 저장된 '글 생성 날짜'의 형식을 지정할 포맷 변수
	
	
	
	
	
	public InquiryDaoImpl() {
		this(null);
	}
	
	
	
	public InquiryDaoImpl(String format) {
		this(JdbcContextCreator.getJdbcContext(), format);
	}
	
	
	
	public InquiryDaoImpl(JdbcContext jdbcContext, String format) {
		this.jdbcContext = jdbcContext;
		this.format = format;
	}
	
	
	
	
	@Override
	public int getIdx(int no) {
		return jdbcContext.queryForObject(
					"SELECT Idx FROM Inquiry WHERE No = " + no, 
					rs -> {
						return rs.getInt(1);
					});
	}

	
	
	
	
	@Override
	public int getCountRows() throws NoQueryDataException {
		return jdbcContext.countRows("Inquiry") - 1;
	}

	
	
	
	
	@Override
	public List<Inquiry> getBoardList(int offset, int limit) throws NoQueryDataException {
		
		Object[] args = {limit, offset};
		
		return jdbcContext.queryForObjects(
				"SELECT No, Title, Writer, CreateDate, ReadCount, Indent "
			  + "FROM Inquiry WHERE No > 1 ORDER BY BGroup DESC, Step ASC "
			  + "LIMIT ? OFFSET ? ",
			    args,
				rs -> {
					Inquiry dto = new Inquiry();
					dto.setNo(rs.getInt(1));
					dto.setTitle(rs.getString(2));
					dto.setWriter(rs.getString(3));
					dto.setCreateDate(rs.getTimestamp(4), format);
					dto.setReadCount(rs.getInt(5));
					dto.setIndent(rs.getInt(6));
					return dto;
				});
		
	}

	
	
	
	
	@Override
	public int writeBoard(Inquiry dto) throws NoUpdateDataException {
		
		int key = jdbcContext.insertAndGetGeneratedKeys(
					"INSERT INTO Inquiry (Idx, Title, Content, Writer, Password) "
				  + "VALUES (?, ?, ?, ?, ?)",
					dto.getIdx(), dto.getTitle(), dto.getContent(), 
					dto.getWriter(), dto.getPassword());
		
		/* BGroup 값을 No값과 똑같이 맞춰준다. */
		jdbcContext.executeUpdate("UPDATE Inquiry SET BGroup = " + key + " WHERE No = " + key);
		
		return key;
		
	}

	
	
	
	@Override
	public int copyImagePath(int no, int seq) {
		return jdbcContext.executeUpdate(
			    "INSERT INTO InquiryImages (No, Path) "
			  + "SELECT ?, Path FROM BoardTempImages WHERE Seq = ?", no, seq);
	}

	
	
	
	
	@Override
	public List<String> getImagePath(int no) {
		return jdbcContext.queryForObjects(
				"SELECT Path FROM InquiryImages WHERE No = " + no, 
				rs -> {
					return rs.getString(1);
				});
	}
	
	
	
	

	@Override
	public void deleteImagePath(int no) {
		jdbcContext.executeUpdate("DELETE FROM InquiryImages WHERE No = " + no);
	}

	
	
	
	
	@Override
	public Inquiry getBoard(int no) throws NoQueryDataException {
		return jdbcContext.queryForObject(
				"SELECT Title, Content, Writer, CreateDate, ReadCount "
			+ 	"FROM Inquiry WHERE No = " + no,
				rs -> {
					Inquiry dto = new Inquiry();
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
		return jdbcContext.executeUpdate("DELETE FROM Inquiry WHERE No = " + no);
	}

	
	
	

	@Override
	public int upRead(int no) {
		return jdbcContext.executeUpdate("UPDATE Inquiry Set ReadCount = ReadCount + 1 WHERE No = " + no);
	}

	

	

	@Override
	public int getIdxWithGroupNo(int no) {
		return jdbcContext.queryForObject(
				  "SELECT Idx FROM Inquiry WHERE No = "
				+ "(SELECT BGroup FROM Inquiry WHERE No = " + no + ")", 
				  rs -> {
					  return rs.getInt(1);
				  });
	}

	
	
	
	
	@Override
	public String getBoardPassword(int no) {
		return jdbcContext.queryForObject(
				"SELECT Password FROM Inquiry WHERE No = " + no, 
				rs -> {
					return rs.getString(1);
				});
	}
	
	
	
	
	
	
	@Override
	public int replyBoard(Inquiry dto, int replyNo) {
		int bGroup = dto.getBGroup();
		int step = dto.getStep();
		int indent = dto.getIndent();
		
		replyShape(bGroup, step);
		
//		/* 들여쓰기는 5번까지 가능하다. */
//		indent = indent < 5 ? indent + 1 : indent;
		
		return jdbcContext.insertAndGetGeneratedKeys(
				"INSERT INTO Inquiry (Idx, Title, Content, Writer, Password, BGroup, Step, Indent, ReplyNo) "
			  + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)",
				dto.getIdx(), dto.getTitle(), dto.getContent(), 
				dto.getWriter(), dto.getPassword(), 
				bGroup, step + 1, indent + 1, replyNo);
	}
	
	
	
	
	
	
	@Override
	public Inquiry getReplyInfo(int no) {
		return jdbcContext.queryForObject(
				"SELECT BGroup, Step, Indent FROM Inquiry WHERE No = " + no, 
				rs -> {
					Inquiry dto = new Inquiry();
					dto.setBGroup(rs.getInt(1));
					dto.setStep(rs.getInt(2));
					dto.setIndent(rs.getInt(3));
					return dto;
				});
	}
	
	
	
	
	
	/**
	 * <p>새로 글을 삽입하기 위해 
	 * 같은 그룹에서 답글단 글의 아랫글의 위치를 아래로 1씩 내려보낸다.
	 * 
	 * @param bGroup 그룹 번호
	 * @param step 글의 위치
	 */
	private void replyShape(int bGroup, int step) {
		jdbcContext.executeUpdate(
				"UPDATE Inquiry SET Step = Step + 1 WHERE BGroup = ? AND Step > ?",
				bGroup, step);
	}
	
}
