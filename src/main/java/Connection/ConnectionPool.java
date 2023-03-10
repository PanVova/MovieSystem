package Connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.Queue;

public class ConnectionPool {
    private static final String DRIVER_CLASS_NAME = "com.mysql.cj.jdbc.Driver";
    private static final String URL = "jdbc:mysql://localhost:3306/cinema";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "1234";

    private static final int MAX_CONNECTIONS = 10;
    private final Queue<Connection> connectionPool = new LinkedList<>();

    public ConnectionPool() throws SQLException {
        try {
            Class.forName(DRIVER_CLASS_NAME);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public synchronized Connection getConnection() throws SQLException {
        if (connectionPool.isEmpty()) {
            return createConnection();
        } else {
            Connection connection = connectionPool.poll();
            if (!isValid(connection)) {
                return createConnection();
            } else {
                return connection;
            }
        }
    }

    private boolean isValid(Connection connection) throws SQLException {
        return connection != null && !connection.isClosed();
    }

    private Connection createConnection() throws SQLException {
        return DriverManager.getConnection(URL, USERNAME, PASSWORD);
    }

    public synchronized void releaseConnection(Connection connection) {
        if (connection != null && connectionPool.size() < MAX_CONNECTIONS) {
            connectionPool.offer(connection);
        }
    }

    public synchronized void closeAllConnections() throws SQLException {
        for (Connection connection : connectionPool) {
            connection.close();
        }
    }
}

