package com.funweb.web.dao;

import com.funweb.web.dto.Account;

import jdbccontext.exception.DataAccessException;

public interface AccountDao {
	
	/**
	 * <p>로그인시 필요한 정보들을 DB에서 가져와 Account 객체에 담아서 반환한다.
	 * 
	 * <ul>로그인시 필요한 정보
	 * 	<li>Password
	 * 	<li>E_MailActive
	 * </ul>
	 * 
	 * @param userId 계정 정보를 얻을 아이디
	 * @return 로그인 검사시 필요한 정보를 담은 Account 객체
	 */
	Account getLoginValidationInfo(String id);
	
	
	
	
	
	/** 
	 * <p>로그인 후 세션에 저장해 둘 정보(Idx, UserRole)를 반환한다. 
	 * 
	 * @param id 계정 아이디
	 * @return Idx와 UserRole 정보를 담은 Account 객체
	 */
	Account getLoginInfo(String id);
	
	
	
	
	
	/**
	 * <p>DB에서 id와 일치하는 계정 정보를 Account 객체에 담아 반환한다.
	 * 
	 * <p>회원 정보 보기, 회원 정보 수정 페이지에서 사용된다.
	 * 
	 * @Param idx 계정 아이디의 인덱스
	 * @return 일치하는 계정 정보를 담은 Account객체
	 */
	Account getAccountInfo(int idx);
	
	
	
	
	
	/**
	 * <p>idx에 일치하는 비밀번호를 반환한다.
	 * 
	 * @param idx 비밀번호를 얻어올 계정의 아이디의 인덱스
	 * @return 일치하는 계정의 비밀번호
	 */
	String getPassword(int idx);
	
	
	
	
	
	/**
	 * <p>회원가입 폼에서 입력한 계정 정보를 account 객체로 넘겨받아
	 * 그 데이터를 DB에 저장한다.
	 * 
	 * @Param dto 회원가입 폼에서 입력한 계정 정보를 담은 객체
	 * @return 새로 생성한 토큰
	 * @throws DataAccessException 
	 * 				MySQLIntegrityConstraintViolationException 토큰값이 중복될 경우 발생
	 */
	String insertNewAccount(Account account) throws DataAccessException;
	
	
	
	
	
	/**
	 * <p>토큰의 인덱스값을 사용하여 쿼리문을 날려
	 * 해당하는 토큰값을 반환한다.
	 * 
	 * @param idx
	 * @return 토큰
	 */
	String getToken(int idx);
	
	
	
	
	
	/**
	 * <p>아이디의 토큰을 새로 갱신하고 새로 생성한 토큰을 반환한다.
	 * 
	 * @param userId 토큰을 갱신할 아이디
	 * @return 새로 생성한 토큰
	 */
	String updateNewToken(String userId);
	
	
	
	
	
	/**
	 * <p>수정된 계정의 정보를 DB에 새로 업데이트한다.
	 * 
	 * @param dto 계정의 아이디를 포함하고 있는 수정된 회원 정보
	 */
	void updateAccountInfo(Account account);
	
	
	
	
	
	/**
	 * <p>새로운 비밀번호로 DB를 업데이트한다.
	 * 
	 * @param idx	비밀번호를 변경할 계정 아이디의 인덱스
	 * @param newPass	새로운 비밀번호
	 */
	void updateNewPassword(int idx, String newPass);
	
	
	
	
	
	/**
	 * <p>인자로 넘겨진 아이디 값에 해당하는 데이터베이스의 계정을 삭제한다.
	 * 
	 * @param Idx 삭제할 계정 아이디의 인덱스
	 */
	void deleteAccount(int idx);
	
	
	
	
	/**
	 * <p>토큰을 사용하여 계정의 E_mailActive를 true로 변경해 계정을 활성화 시킨다.
	 * 
	 * @param token url에 적힌 token 매개변수
	 * @return 업데이트에 성공하면 true, 실패하면 false
	 */
	boolean activeAccountByToken(String token);
	
	
	
	
	
	/**
	 * <p>userId에 해당하는 아이디가 DB에 있으면 1 아니면 0을 반환한다.
	 * 
	 * <p>이 함수는 검색할 아이디에 대해서 대소문자 구분을 하지 않는다.
	 * 
	 * @param userId 검색할 아이디
	 * @return userId에 해당하는 아이디가 DB에 있으면 1 아니면 0
	 */
	public int countRowsForUserId(String id);
	
}
