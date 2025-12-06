package exceptions;

import java.sql.SQLException;

public class DbException extends RuntimeException {
    public DbException(String message) {
        super(message);
    }
}
