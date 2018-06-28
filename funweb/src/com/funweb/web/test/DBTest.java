package com.funweb.web.test;

import java.sql.SQLException;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import jdbccontext.JdbcContext;
import jdbccontext.exception.DataAccessException;

public class DBTest {

	public void testGeneratedKeys() {
		
		JdbcContext jdbcContext1 = new JdbcContext("java:comp/env/jdbc/jspbeginner");
		JdbcContext jdbcContext2 = new JdbcContext("java:comp/env/jdbc/jspbeginner");
		
		int index1 = jdbcContext1.insertAndGetGeneratedKeys("INSERT INTO BoardTempSequence Values()");
		int index2 = jdbcContext2.insertAndGetGeneratedKeys("INSERT INTO BoardTempSequence Values()", null);
		
		
		System.out.println("index1 : " + index1);
		System.out.println("index2 : " + index2);
		
	}
	
	public void testConnectionAndDBSQLExecution() throws SQLException {
		
		/* DB 초기화 */
		JdbcContext jdbcContext = new JdbcContext();
		try {
			Context init = new InitialContext();
			System.out.println("Before getting DataSource");
			DataSource ds = (DataSource) init.lookup("java:comp/env/jdbc/jspbeginner");
			System.out.println("DataSource : " + ds);
			jdbcContext.setDataSource(ds);
		} catch (NamingException e) {
			System.out.println("BoardDaoImpl생성자에서 에러 : " + e);
		}
		/* DB 초기화 끝*/
		
		
		
		
		try {
			/* DB test 시작 */
			
			
			
			// DB test를 위하여 데이터를 3개 입력한다. 
			// 나중에 입력한 데이터가 3개인지 query문을 날려 값을 비교하여 DB test의 성공 여부를 결정 짓는다.
			jdbcContext.executeUpdate("insert into DBTest(name) values(?)", "name1");
			jdbcContext.executeUpdate("insert into DBTest(name) values(?)", "name2");
			jdbcContext.executeUpdate("insert into DBTest(name) values(?)", "name3");
			
			
			
			// 입력한 데이터가 3개이므로 countRows()메소드를 이용해서 전체 row수를 얻어온 다음
			// 얻어온 row가 3이 아닐 경우 에러메시지 출력
			int rows = jdbcContext.countRows("DBTest");
			if(rows != 3) {
				System.out.println("DBTest 실패! rows :" + rows);
			}
			
			
			
			/* DB test 종료 */
		} catch (DataAccessException e) {
			System.out.println("DBTest에서 테스트 도중 예외 발생");
			throw e;
		} finally {
			try {
				// 매번 테스트를 자동으로 실행하기 위해 DBTest 테이블의 row를 모두 삭제한다.
				jdbcContext.executeUpdate("delete from DBTest");
			} catch (DataAccessException e) {
				System.out.println("DBTest에서 데이터 삭제 도중 예외 발생!!! 올바른 테스트를 진행하기 위하여 DB에 접속하여 데이터를 삭제해주세요."
						+ " TABLENAME : [DBTest]");
				System.out.println(e);
				throw e;
			}
		}
		
	}

}
