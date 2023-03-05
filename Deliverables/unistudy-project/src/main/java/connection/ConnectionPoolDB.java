package connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

public class ConnectionPoolDB {
    private static List<Connection> freeDbConnections;

    static {
        freeDbConnections = new LinkedList<Connection>();
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private static Connection createDBConnection() throws SQLException {
        Connection newConnection;

        String ip = "localhost";
        String port = "3306";

        String db = "unistudydb";
        String username = "root";
        String password = "Universitario07!";

        newConnection = DriverManager.getConnection("jdbc:mysql://" + ip + ":" + port + "/" + db + "",username, password);
        newConnection.setAutoCommit(true); //serve?

        return newConnection;
    }

    public static synchronized Connection getConnection() throws SQLException { //serve synchronized?
        Connection connection;

        if (!freeDbConnections.isEmpty()) {
            connection = (Connection) freeDbConnections.get(0);
            ConnectionPoolDB.freeDbConnections.remove(0);

            try {
                if (connection.isClosed()) {
                    connection = ConnectionPoolDB.getConnection();
                }
            } catch (SQLException e) {
                connection = ConnectionPoolDB.getConnection();
            }
        } else {
            connection = ConnectionPoolDB.createDBConnection();

        }

        return connection;
    }

    public static synchronized void releaseConnection(Connection connection) {
        ConnectionPoolDB.freeDbConnections.add(connection);
    }

}
