package jdbccontext.creator;

import jdbccontext.JdbcContext;

public class JdbcContextCreator {
	
	/**
	 * JNDI를 사용하여 JdbcContext를 생성하여 반환한다.
	 * 
	 * @return
	 */
	public static JdbcContext getJdbcContext() {
		return new JdbcContext("java:comp/env/jdbc/funweb");
	}
	
}

