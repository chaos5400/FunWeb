package com.funweb.web.daoimpl;

import com.funweb.web.dao.AccountDao;
import com.funweb.web.dto.Account;

import jdbccontext.JdbcContext;
import jdbccontext.creator.JdbcContextCreator;
import token.TokenGenerator;

public class AccountDaoImpl implements AccountDao {

	private JdbcContext jdbcContext;
	
	
	
	
	
	public AccountDaoImpl() {
		this(JdbcContextCreator.getJdbcContext());
	}
	
	
	
	
	
	/* JdbcContext를 인자로 받는 생성자를 만든 이유는 
	 * 이 클래스 객체를 사용하는 곳에서 JdbcContext 
	 * 클래스 내부에 있는 트랜잭션 기능을 사용하기 위해서다 */
	public AccountDaoImpl(JdbcContext jdbcContext) {
		this.jdbcContext = jdbcContext;
	}
	
	
	
	
	
	public Account getLoginValidationInfo(String userId) {
		return jdbcContext.queryForObject(
				"SELECT Password, EMailActive "
				+ " FROM Account WHERE binary(UserID) = '" + userId +"'",
				rs -> {
					/* 로그인 검사시 필요한 정보들만 DB에서 가져온다. */
					Account dto = new Account();
					dto.setPassword(rs.getString(1));
					dto.setEMailActive(rs.getBoolean(2));
					return dto;
				});
	}
	
	
	
	
	
	
	@Override
	public Account getLoginInfo(String userId) {
		return jdbcContext.queryForObject(
				"SELECT Idx, UserRole "
				+ " FROM Account WHERE binary(UserID) = '" + userId + "'", 
				rs -> {
					/* 로그인 검사시 필요한 정보들만 DB에서 가져온다. */
					Account dto = new Account();
					dto.setIdx(rs.getInt(1));
					dto.setUserRole(rs.getString(2));
					return dto;
				});
	}
	
	
	
	
	
	@Override
	public Account getAccountInfo(int idx) {
		return jdbcContext.queryForObject("SELECT FirstName, LastName, Address, PhoneNumber, MobilePhoneNumber "
				+ "FROM Account WHERE Idx = " + idx,
				rs -> {
					/* 회원 정보를 DB에서 가져온다. */
					Account dto = new Account();
					dto.setFirstName(rs.getString(1));
					dto.setLastName(rs.getString(2));
					dto.setAddress(rs.getString(3));
					dto.setPhoneNumber(rs.getString(4));
					dto.setMobilePhoneNumber(rs.getString(5));
					return dto;
				});
	}
	
	
	
	
	
	@Override
	public String getPassword(int idx) {
		return jdbcContext.queryForObject("SELECT Password FROM Account WHERE Idx = " + idx, 
				rs -> { return rs.getString(1); });
	}
	
	
	
	
	
	
	@Override
	public String insertNewAccount(Account dto) {
		int idx = createNewToken();
		jdbcContext.executeUpdate("INSERT INTO Account (UserID, Password, FirstName, LastName, "
				+ "Address, PhoneNumber, MobilePhoneNumber, UserRole, TokenIdx) "
				+ "VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?)",
				dto.getUserID(), dto.getPassword(), dto.getFirstName(), dto.getLastName(),
				dto.getAddress(), dto.getPhoneNumber(), dto.getMobilePhoneNumber(), 
				dto.getUserRole(), idx);
		return getToken(idx);
	}
	
	

	
	
	@Override
	public String getToken(int idx) {
		return jdbcContext.queryForObject(
				"SELECT Token FROM TokenArchive WHERE Idx = " + idx,
				rs -> { return rs.getString(1); });
	}
	
	
	
	
	
	@Override
	public String updateNewToken(String userId) {
		int idx = createNewToken();
		jdbcContext.executeUpdate("UPDATE Account SET TokenIdx = ? WHERE UserId = ?", idx, userId);
		return getToken(idx);
	}
	
	
	


	@Override
	public void updateAccountInfo(Account dto) {
		jdbcContext.executeUpdate("UPDATE Account SET FirstName = ?, LastName = ?, "
				+ "Address = ?, PhoneNumber = ?, MobilePhoneNumber = ? "
				+ "WHERE binary(UserID) = ?", 
				dto.getFirstName(), dto.getLastName(), 
				dto.getAddress(), dto.getPhoneNumber(), dto.getMobilePhoneNumber(),
				dto.getUserID());
	}
	
	
	
	
	
	@Override
	public void updateNewPassword(int idx, String newPass) {
		jdbcContext.executeUpdate("UPDATE Account SET Password = ? WHERE Idx = ?", newPass, idx);
	}
	
	
	
	
	
	@Override
	public void deleteAccount(int idx) {
		jdbcContext.executeUpdate("DELETE FROM Account WHERE Idx = ?", idx);
	}
	
	
	
	
	
	@Override
	public boolean activeAccountByToken(String token) {
		// 토큰이 일치하는 계정의 이메일 인증을 활성화시킨다.
		if(token == null || 
				jdbcContext.executeUpdate(
						  "UPDATE Account SET EMailActive = true, TokenIdx = 0 WHERE TokenIdx = "
						+ "(SELECT Idx FROM TokenArchive WHERE Token = ?)", token)
				== 0) {
			return false;
		}
		return true;
	}
	
	
	
	
	
	@Override
	public int countRowsForUserId(String userId) {
		return jdbcContext.countRows("Account", "UserID = ?", userId);
	}
	
	

	
	
	/**
	 * 토큰값을 새로 생성하여 그 인덱스 값을 반환한다.
	 * 
	 * @return 새로 생성한 토큰의 인덱스 값
	 */
	private int createNewToken() {
		String token = TokenGenerator.getTokenString(50);
		int index = jdbcContext.insertAndGetGeneratedKeys(
				"INSERT INTO TokenArchive (Token) VALUES (?)", token);
		return index;
	}
	
}
