package com.funweb.web.dto;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;

public class NewsNotice {

	private int boardNo = -1;
	private int no;
	private String title;
	private Timestamp createDate;

	/* createDate의 날짜 포맷 형식을 지정하기 위한 변수 */
	private String format;

	public String getReadPage() {
		String board = "";
		switch (boardNo) {
		
		case 0:
			board = "notice";
			break;
			
		case 1:
			board = "publicNews";
			break;
		
		default :
			return null;
		}
		board += "Read.do";
		return board;
	}
	
	public void setBoardNo(int boardNo) {
		this.boardNo = boardNo;
	}

	public int getNo() {
		return no;
	}

	public void setNo(int no) {
		this.no = no;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDate() {
		if (format == null)
			return null;
		return new SimpleDateFormat(format).format(this.createDate);
	}

	public void setCreateDate(Timestamp createDate, String format) {
		this.createDate = createDate;
		this.format = format;
	}

	public String getFormat() {
		return format;
	}

	public void setFormat(String format) {
		this.format = format;
	}

}
