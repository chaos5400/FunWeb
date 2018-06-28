package com.funweb.web.dto;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;

public class PublicNews {
	
	private int idx;
	private int no;
	private String title;
	private String content;
	private String writer;
	private Timestamp createDate;
	private int readCount;

	/* createDate의 날짜 포맷 형식을 지정하기 위한 변수 */
	private String format;

	
	
	public int getIdx() {
		return idx;
	}

	public void setIdx(int idx) {
		this.idx = idx;
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

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getWriter() {
		return writer;
	}

	public void setWriter(String writer) {
		this.writer = writer;
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

	public int getReadCount() {
		return readCount;
	}

	public void setReadCount(int readCount) {
		this.readCount = readCount;
	}

	public String getFormat() {
		return format;
	}

	public void setFormat(String format) {
		this.format = format;
	}
	
}
