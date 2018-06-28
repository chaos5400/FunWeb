package jdbccontext.exception;

public class DataAccessException extends RuntimeException {
	
	public DataAccessException() {
	}
	
	public DataAccessException(Throwable t) {
		super(t);
	}
	
}
