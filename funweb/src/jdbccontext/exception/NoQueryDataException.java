package jdbccontext.exception;

public class NoQueryDataException extends DataAccessException {

	public NoQueryDataException() {
	}
	
	public NoQueryDataException(Throwable t) {
		super(t);
	}
}
