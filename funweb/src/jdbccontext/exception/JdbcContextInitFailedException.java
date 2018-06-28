package jdbccontext.exception;

public class JdbcContextInitFailedException extends DataAccessException {

	public JdbcContextInitFailedException() {
	}
	
	public JdbcContextInitFailedException(Throwable t) {
		super(t);
	}
	
}
