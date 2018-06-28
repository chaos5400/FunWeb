package jdbccontext;

import java.sql.SQLException;

public interface IJdbcContext<T> {
	T execute() throws SQLException;
}
