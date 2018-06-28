package jdbccontext;

import java.sql.Connection;
import java.sql.SQLException;

public interface StatementStrategy {
	void makePreparedStatement(Connection c) throws SQLException;
}
