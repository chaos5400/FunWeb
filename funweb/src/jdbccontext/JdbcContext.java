
package jdbccontext;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import com.mysql.jdbc.Statement;

import jdbccontext.exception.DataAccessException;
import jdbccontext.exception.JdbcContextInitFailedException;
import jdbccontext.exception.NoQueryDataException;
import jdbccontext.exception.NoUpdateDataException;


/**
 * <p>사용자가 SQL쿼리문을 입력하면 그 쿼리문을 실행하여 결과값을 리턴해주는 메소드들을
 * 멤버 메소드로 가지고 있다.
 * 
 * @author 정진원
 */
public class JdbcContext {
	
	private Connection c = null;
	private PreparedStatement ps = null;
	private ResultSet rs = null;
	private DataSource dataSource;

	private boolean transactional = false;
	
	private boolean trace = false;
	
	
	
	
	public JdbcContext() {
	}
	
	
	
	
	
	/**
	 * lookupPath로 DataSource를 생성하여
	 * 내부 멤버 변수에 저장한다.
	 * 
	 * @param lookupPath JNDI name
	 * @throws JdbcContextInitFailedException 초기화 중 예외 발생
	 */
	public JdbcContext(String lookupPath) throws JdbcContextInitFailedException {
		try {
			DataSource ds = (DataSource) new InitialContext().lookup(lookupPath);
			this.setDataSource(ds);
		} catch (NamingException e) {
			throw new JdbcContextInitFailedException(e);
		}
	}
	
	
	
	
	
	/**
	 * DataSource를 얻어와서 클래스 내부 멤버 변수에 저장한다.
	 * 
	 * @param dataSource 데이터베이스 Connection 객체를 얻기위한 객체
	 */
	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}
	
	
	
	
	
	/**
	 * 트랜잭션을 사용하기 위해서는 true값을 넘겨 주어야 한다.
	 * 
	 * @param transactional true(트랜잭션 사용) or false(트랜잭션 사용 안함, 디폴트)
	 */
	public void setTransactinal(boolean transactional) {
		this.transactional = transactional;
	}
	
	
	
	
	
	/**
	 * DB 오류를 추적하여 로그를 콘솔창에 출력하려면 true로 설정하여야 한다.
	 * 
	 * @param trace true(추적 사용) or false(추적 사용 안함, 디폴트)
	 */
	public void setTrace(boolean trace) {
		this.trace = trace;
	}
	
	
	
	
	
	/**
	 * SQL문을 실행하여 업데이트된 Row수를 반환한다.
	 * 
	 * @param sql	'?'가 없는 SQL 문
	 * @return		업데이트한 Row수
	 * @throws DataAccessException
	 */
	public int executeUpdate(String sql) throws DataAccessException {
		return executeUpdate(getStatementStrategy(sql));
	}
	
	
	
	
	
	/**
	 * SQL문을 실행하여 업데이트된 Row수를 반환한다.
	 * 
	 * @param sql	'?'가 있는 SQL문
	 * @param args	'?'에 매치시킬 매개변수
	 * @return 업데이트한 Row수
	 * @throws DataAccessException
	 */
	public int executeUpdate(String sql, Object... args) throws DataAccessException {
		return executeUpdate(getStatementStrategy(sql, args));
	}
	

	
	
	/**
	 * <p>SQL문을 실행하는 메소드를 구현한 IJdbcContext 객체를 stmt와 함께
	 * 클래스 내부의 private 메소드인 executeJdbcContext() 메소드로 넘긴다.
	 * 
	 * <p>executeJdbcContext() 메소드에서는 넘겨받은 매개변수를 사용하여
	 * 로직을 수행한다.
	 * 
	 * <p>executeJdbcContext() 메소드는 모든 로직을 수행한 후 사용한 
	 * Connection 객체, PreparedStatement객체 그리고 ResultSet객체를 close()한다.
	 * 
	 * @param stmt	PreparedStatement를 구하는 메소드가 구현된 인터페이스 객체
	 * @return	업데이트한 Row수
	 * @throws DataAccessException
	 */
	private int executeUpdate(StatementStrategy stmt) throws DataAccessException {
		return executeJdbcContext(stmt, () -> ps.executeUpdate());
	}
	
	
	
	
	/**
	 * <p>SQL 쿼리문을 입력받아 실행하여 그 결과값을 T타입 객체에 담아 반환한다.
	 * 
	 * <p>만약 ResultSet으로 저장된 데이터가 없다면 NoQ이 발생한다.
	 * 
	 * @param sql		매개변수를 대체하는 1개이상의 '?'를 가지고 있는 SQL 쿼리문
	 * @param args		sql 을 인자로 하여 생성된 PreparedStatement 객체에 입력할 인자값
	 * @param rowMapper	쿼리문의 결과값을 저장할 로직이 담겨있는 메소드가 구현된 IRowMapper 인터페이스의 객체
	 * @return	쿼리문의 결과값을 저장한 T타입 객체
	 * @throws DataAccessException	
	 */
	public <T> T queryForObject(String sql, Object[] args, IRowMapper<T> rowMapper) throws DataAccessException {
		return queryForObject(getStatementStrategy(sql, args), rowMapper);
	}
	
	
	
	
	
	/**
	 * <p>인자 <b>없는</b> SQL 쿼리문을 입력받아 실행하여 그 결과값을 T타입 객체에 담아 반환한다.
	 * 
	 * <p>만약 ResultSet으로 저장된 데이터가 없다면 SQLException이 발생한다.
	 * 
	 * @param sql	매개변수가 없는 SQL 실행문		
	 * @param rowMapper	쿼리문의 결과값을 저장할 로직이 담겨있는 메소드가 구현된 IRowMapper 인터페이스의 객체
	 * @return 	쿼리문의 결과값을 저장한 T타입 객체
	 * @throws DataAccessException
	 */
	public <T> T queryForObject(String sql, IRowMapper<T> rowMapper) throws DataAccessException {
		return queryForObject(getStatementStrategy(sql), rowMapper);
	}
	
	
	
	
	
	/**
	 * <p>이 메소드에서는 넘겨받은 rm 객체 를 사용하여 
	 * SQL문을 실행하여 데이터를 저장하는 메소드를 구현한다. 
	 * 그리고 메소드를 구현한 IJdbcContext 객체를 stmt와 함께
	 * 클래스 내부의 private 메소드인 executeJdbcContext() 메소드로 넘겨서
	 * 반환된 리턴값을 반환한다.
	 * 
	 * <p>executeJdbcContext() 메소드에서는 넘겨받은 매개변수를 사용하여
	 * 로직을 수행한다.
	 * 
	 * <p>executeJdbcContext() 메소드는 모든 로직을 수행한 후 사용한 
	 * Connection 객체, PreparedStatement객체 그리고 ResultSet객체를 close()한다.
	 * 
	 * @param stmt	PreparedStatement를 세팅하는 메소드가 구현된 인터페이스 객체
	 * @param rm	쿼리문을 실행하여 얻은 데이터를 저장하는 메소드가 구현된 객체
	 * @return	데이터를 저장한 T 객체
	 * @throws DataAccessException
	 */
	private <T> T queryForObject(StatementStrategy stmt, IRowMapper<T> rm) throws DataAccessException {
		return executeJdbcContext(stmt, () -> { doExecuteQuery(); return rm.mapRow(rs); });
	}
	
	
	
	
	
	/**
	 * <p>인자 없는 SQL 쿼리문을 입력받아 실행하여 그 결과값들을 List 형태의 객체에 담아 반환한다.
	 * SQL 쿼리문에 의하여 ResultSet으로 저장된 데이터들을 rowMapper의 mapRow()메소드를 실행하여 DTO에 저장하여 각각의 객체를
	 * List 형태의 객체에 담아 반환한다.
	 * 
	 * <p><b>1개 이상의 T타입 객체가 반환된다.</b>
	 * 
	 * <p>만약 ResultSet으로 저장된 데이터가 없다면 NoQueryDataException이 발생한다. 
	 * 
	 * @param sql		'?'를 가지고 있는 SQL 쿼리문
	 * @param rowMapper	쿼리문의 결과값을 저장할 로직이 담겨있는 메소드가 구현된 IRowMapper 인터페이스의 객체
	 * @return	쿼리문의 결과값을 저장한 T타입 객체 List
	 * @throws DataAccessException
	 */
	public <T> List<T> queryForObjects(String sql, IRowMapper<T> rowMapper) throws DataAccessException {
		return queryForObjects(getStatementStrategy(sql), rowMapper);
	}
	
	
	
	
	
	/**
	 * <p>인자 있는 SQL 쿼리문을 입력받아 실행하여 그 결과값들을 List 형태의 객체에 담아 반환한다.
	 * SQL 쿼리문에 의하여 ResultSet으로 저장된 데이터들을 rowMapper의 mapRow()메소드를 실행하여 저장하여 각각의 객체를
	 * List 형태의 객체에 담아 반환한다.
	 * 
	 * <p><b>1개 이상의 T타입 객체가 반환된다.</b>
	 * 
	 * <p>만약 ResultSet으로 저장된 데이터가 없다면 SQLException이 발생한다.
	 * 
	 * @param sql		SQL 쿼리문
	 * @param args		쿼리문의 ?을 대체하는 매개변수의 배열
	 * @param rowMapper	쿼리문의 결과값을 저장할 로직이 담겨있는 메소드가 구현된 IRowMapper 인터페이스 객체
	 * @return 쿼리문의 결과값을 저장한 T타입 객체 List
	 * @throws DataAccessException
	 */
	public <T> List<T> queryForObjects(String sql, Object[] args, IRowMapper<T> rowMapper) throws DataAccessException {
		return queryForObjects(getStatementStrategy(sql, args), rowMapper);
	}
	
	
	
	
	
	
	/**
	 * <p>이 메소드에서는 넘겨받은 rm 객체 를 사용하여 
	 * SQL문을 실행하여 데이터를 DTO객체에 저장하는 메소드를 구현한다. 
	 * 그리고 메소드를 구현한 IJdbcContext 객체를 stmt와 함께
	 * 클래스 내부의 private 메소드인 executeJdbcContext() 메소드로 넘겨서
	 * 반환된 리턴값을 반환한다.
	 * 
	 * 
	 * <p>executeJdbcContext() 메소드에서는 넘겨받은 매개변수를 사용하여
	 * DB 로직을 수행한다.
	 * 
	 * <p>executeJdbcContext() 메소드는 모든 로직을 수행한 후 사용한 
	 * Connection 객체, PreparedStatement객체 그리고 ResultSet객체를 close()한다. 
	 * 
	 * @param stmt	PreparedStatement를 세팅하는 메소드가 구현된 인터페이스 객체
	 * @param rm	쿼리문을 실행하여 얻은 데이터를 저장하는 메소드가 구현된 객체
	 * @return 쿼리문의 결과값을 저장한 T타입 객체 List
	 * @throws DataAccessException
	 */
	private <T> List<T> queryForObjects(StatementStrategy stmt, IRowMapper<T> rm) throws DataAccessException {
		return executeJdbcContext(stmt, 
			() -> {
				doExecuteQuery();
				
				List<T> list = new ArrayList<T>();
				do {
					T tmp = null;
					tmp = rm.mapRow(rs);
					list.add(tmp);
				} while(rs.next());
				
				return list;
			});
	}

	
	
	
	
	/**
	 * 데이터베이스 테이블명을 입력받아 해당 테이블의 Row수를 반환한다.
	 * 
	 * @param tableName	Row수를 구할 데이터베이스의 테이블 이름
	 * @return	tableName 테이블의 Row수
	 * @throws DataAccessException
	 */
	public int countRows(String tableName) throws DataAccessException {
		return executeJdbcContext(
				getStatementStrategy("select count(*) from " + tableName), 
				() -> { doExecuteQuery(); return rs.getInt(1); });
	}
	
	
	
	
	
	/**
	 * 데이터베이스 테이블명을 입력받아 해당 테이블의 조건에 맞는 Row수를 반환한다.
	 * 
	 * @param tableName	Row수를 구할 데이터베이스의 테이블 이름
	 * @param where	Row수를 구할 때 사용할 조건
	 * @return	tableName의 where 조건에 맞는 Row수
	 * @throws DataAccessException
	 */
	public int countRows(String tableName, String where) throws DataAccessException {
		return countRows(tableName, where, (Object[])null);
	}
	
	
	
	
	
	/**
	 * <p>데이터베이스 테이블명을 입력받아 해당 테이블의 조건에 맞는 Row수를 반환한다.
	 * 이 메서드는 where에서 ?에 매치시킬 인자값을 args매개변수로 입력받는다.
	 * 
	 * @param tableName	Row수를 구할 데이터베이스의 테이블 이름
	 * @param where	Row수를 구할 때 사용할 조건
	 * @param args	where에서 '?'에 매치시킬 매개변수 배열
	 * @return tableName의 where 조건에 맞는 Row수
	 * @throws DataAccessException
	 */
	public int countRows(String tableName, String where, Object... args) throws DataAccessException {
		return executeJdbcContext(getStatementStrategy("select count(*) from " + tableName + " where " + where, args), 
				() -> { doExecuteQuery(); return rs.getInt(1); });
	}
	


	
	/**
	 * <p>데이터를 insert하고 그 행의 autu_increment 값을 반환한다.
	 * 
	 * @param sql	insert 구문
	 * @return auto_increment 값
	 * @throws NoUpdateDataException INSERT된 행이 없어서 AUTO_INCREMENT값을 가져오지 못할 경우 발생한다.
	 */
	public int insertAndGetGeneratedKeys(String sql) throws DataAccessException {
		return insertAndGetGeneratedKeys(getStatementStrategyWithKeys(sql));
	}
	
	
	
	
	
	/**
	 * <p>데이터를 insert하고 그 행의 auto_increment 값을 반환한다.
	 * 
	 * <p>args는 쿼리문의 ?을 대체하는 매개변수의 배열이다.
	 * 
	 * @param sql	insert 구문
	 * @param args	쿼리문의 ?을 대체하는 매개변수의 배열
	 * @return auto_increment 값
	 * @throws NoUpdateDataException INSERT된 행이 없어서 AUTO_INCREMENT값을 가져오지 못할 경우 발생한다.
	 */
	public int insertAndGetGeneratedKeys(String sql, Object... args) throws DataAccessException {
		return insertAndGetGeneratedKeys(getStatementStrategyWithKeys(sql, args));
	}
	
	
	
	
	
	
	/**
	 * <p>데이터를 INSERT 하고 그 행의 AUTO_INCREMENT 값을 반환한다.
	 * 
	 * @param stmt PreparedStatement를 구하는 메소드가 구현된 인터페이스 객체
	 * @return AUTO_INCREMENT 값
	 * @throws NoUpdateDataException INSERT된 행이 없어서 AUTO_INCREMENT값을 가져오지 못할 경우 발생한다.
	 */
	private int insertAndGetGeneratedKeys(StatementStrategy stmt) throws NoUpdateDataException {
		return executeJdbcContext(stmt, 
			() -> { 
				ps.executeUpdate();
				rs = ps.getGeneratedKeys();
				if(!rs.next()) throw new NoUpdateDataException();
				return rs.getInt(1);
			});
	}
	
	
	
	
	
	/**
	 * <p>이 메소드에서는 넘겨받은 매개변수를 사용하여 데이터베이스 관련 로직을 수행한다.
	 * 로직 수행이 끝난 후에는 사용한 Connection, PreparedStatement, ResultSet 객체를 close()하는 역할을 한다.
	 * 
	 * <p><b>각각의 SQL실행 메소드에서 사용될 공통적인 부분만을 추려서 메소드 형태로 만들었다.</b>
	 * 
	 * <p>이 메서드에서는 로직 수행중 발생한 SQLException 예외를 DataAccessException 예외로 바꾸어준다.
	 * 
	 * @param stmt	PreparedStatement를 구하는 메소드가 구현된 인터페이스 객체
	 * @param ijc	PreparedStatement를 이용하여 데이터베이스 관련 로직을 수행하는 메소드가 구현된 IJdbcContext 객체
	 * @return	데이터를 저장한 T타입 객체
	 * @throws DataAccessException
	 */
	private <T> T executeJdbcContext(StatementStrategy stmt, IJdbcContext<T> ijc) throws DataAccessException {
		T o = null;
		
		try {
			if(c == null || c.isClosed()) {
				c = dataSource.getConnection();
			}
			
			if(transactional) { c.setAutoCommit(false); } // 트랜잭션 처리시 false
			else { c.setAutoCommit(true); }
			
			stmt.makePreparedStatement(c);
			
			o = ijc.execute();
		} catch (SQLException e) {
			if(this.trace == true) { e.printStackTrace(); }
			throw new DataAccessException(e);
		} finally {
			if(!transactional) { freeResource(); } // 트랜잭션을 사용하지 않으면 리소스를 반환한다.
		}
		
		return o; 
	}
	
	
	
	
	
	/**
	 * PreparedStatement 객체를 생성하는 메서드를 구현한
	 * StatementStrategy 객체를 반환한다.
	 * 
	 * @param sql SQL문
	 * @return StatementStrategy 객체
	 */
	private StatementStrategy getStatementStrategy(String sql) {
		return c -> setPreparedStatement(sql);
	}
	
	
	
	
	
	/**
	 * <p>PreparedStatement 객체를 생성하는 메서드를 구현한
	 * StatementStrategy 객체를 반환한다.
	 * 
	 * @param sql	SQL문
	 * @param args	sql에서 '?'에 매치시킬 매개변수의 배열
	 * @return StatementStrategy 객체
	 */
	private StatementStrategy getStatementStrategy(String sql, Object[] args) {
		return c -> setObjects(sql, args);
	}
	
	
	
	
	
	/**
	 * <p>PreparedStatement 객체를 생성하는 메서드를 구현한
	 * StatementStrategy 객체를 반환한다.
	 * 
	 * @param sql 매치시킬 '?'가 없는 SQL문
	 * @return StatementStrategy 객체
	 */
	private StatementStrategy getStatementStrategyWithKeys(String sql) {
		return c -> setPreparedStatementWithKeys(sql);
	}
	
	
	
	
	
	/**
	 * sql을 매개변수로 하여 생성된 PreparedStaement객체에
	 * sql의 1개이상의 '?'를 args로 매치시켜 반환하는 메소드를 구현한
	 * StatementStrategy 객체를 반환한다.
	 * 
	 * @param sql	SQL문
	 * @param args	sql에서 '?'에 매치시킬 매개변수의 배열
	 * @return	생성된 StatementStrategy 객체
	 */
	private StatementStrategy getStatementStrategyWithKeys(String sql, Object[] args) {
		return c -> setObjectsWithKeys(sql, args);
	}
	
	
	
	
	
	/**
	 * PreparedStatement객체를 생성한 후 멤버변수에 저장하고
	 * SQL에서 '?'에 해당하는 부분을 args값들로 순차적으로 매치시켜준다.
	 * 
	 * @param sql	SQL문
	 * @param args	sql에서 '?'에 매치시킬 매개변수의 배열
	 */
	private void setObjects(String sql, Object[] args) 
			throws SQLException {
		ps = c.prepareStatement(sql);
		setObjects(args);
	}
	
	
	
	
	
	/**
	 * <p>PreparedStatement객체를 생성한 후 멤버변수에 저장하고
	 * SQL에서 '?'에 해당하는 부분을 args값들로 순차적으로 매치시켜준다.
	 * 
	 * <p>이 메서드는 PreparedStatement객체를 생성할 때 
	 * prepareStatement()메서드의 2번째 인자값으로 Statement.RETURN_GENERATED_KEYS 를 주어
	 * INSERT 문을 실행 후 그 테이블의 AUTO_INCREMENT 값을 반환하도록 한다.
	 * 
	 * @param sql	SQL문
	 * @param args	sql에서 '?'에 매치시킬 매개변수의 배열
	 */
	private void setObjectsWithKeys(String sql, Object[] args) 
			throws SQLException {
		this.ps = this.c.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
		setObjects(args);
	}
	
	
	
	
	
	/**
	 * SQL에서 '?'에 해당하는 부분을 args값들로 순차적으로 매치시켜주는 작업을 한다.
	 * 이 메서드는 다른 멤버 메서드 setObjects 또는 setObjectsWithKeys 에서 사용되어 진다.
	 * 
	 * @param args 매치시킬 인자의 배열
	 */
	private void setObjects(Object[] args) throws SQLException {
		if(args == null || args.length == 0) return;
		int i = 1;
		for(Object o : args) {
			this.ps.setObject(i++, o);
		}
	}
	
	
	
	
	
	/**
	 * PrepareStatement를 클래스 멤버변수에 저장하면서 그 과정에서 발생하는
	 * SQLException 예외를 DataAccessException 예외로 바꾸어준다.
	 * 
	 * @param sql 
	 * @throws DataAccessException
	 */
	private void setPreparedStatement(String sql) throws SQLException {
		ps = c.prepareStatement(sql);
	}
	
	
	
	
	
	
	/**
	 * PrepareStatement를 클래스 멤버변수에 저장하면서 그 과정에서 발생하는
	 * SQLException 예외를 DataAccessException 예외로 바꾸어준다.
	 * 
	 * @param sql 쿼리문
	 */
	private void setPreparedStatementWithKeys(String sql) throws SQLException {
		ps = c.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
	}
	
	
	
	
	
	/**
	 * <p>중복되는 쿼리 실행메소드 executeQuery() 호출과
	 * 쿼리 실행문의 반환값이 하나도 없을 때 던지는 throw new NoQueryDataException(); 을 메소드로 구현하였다.
	 * 
	 * @throws NoQueryDataException 쿼리된 데이터가 없을 경우
	 */
	private void doExecuteQuery() throws SQLException {
		rs = ps.executeQuery();
		if(!rs.next()) throw new NoQueryDataException();
	}
	
	
	
	
	
	
	/**
	 * <p>JdbcContext를 이용하여 DB 작업시 트랜잭션 rollback 처리를 한다.
	 * 
	 * <p>이 기능을 사용하기 위해서는 setTransactional(true)로 설정해주어야 한다.
	 * 
	 * @throws DataAccessException
	 */
	public void rollback() throws DataAccessException {
		try {
			if(c != null) c.rollback();
		} catch (SQLException e) {
			throw new DataAccessException(e);
		} finally {
			freeResource();
		}
	}
	
	
	
	
	
	/**
	 * <p>JdbcContext를 이용하여 DB 작업시 트랜잭션 commit 처리를 한다.
	 * 
	 * <p>이 기능을 사용하기 위해서는 setTransactional(true)로 설정해주어야 한다.
	 * 
	 * @throws DataAccessException
	 */
	public void commit() throws DataAccessException {
		try {
			if(c != null) c.commit();
		} catch (SQLException e) {
			throw new DataAccessException(e);
		} finally {
			freeResource();
		}
	}
	
	


	
	/**
	 * ResultSet, PreparedStatement, Connection 객체를 메모리에서 반환한다.
	 */
	public void freeResource() {
		if (rs != null) { try { rs.close(); } catch (SQLException e) { e.printStackTrace(); } }
		if (ps != null) { try { ps.close(); } catch (SQLException e) { e.printStackTrace(); } }
		if (c != null) { try { c.close(); } catch (SQLException e) { e.printStackTrace(); } }
	}
	
}
