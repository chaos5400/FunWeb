package com.funweb.web.dao;

import java.util.List;

import com.funweb.web.dto.NewsNotice;

public interface MainPageDao {

	/**
	 * <p>Pulbic News & Notice 에서 가장 최근에 작성된
	 * 글 num개를 가져와 리스트 형태로 반환한다.
	 * 
	 * @param num 가져올 글의 개수
	 * @return NewsNotice 타입의 객체
	 */
	List<NewsNotice> getNewsNotice(int num);
	
}
