package it.unisa.db;

import java.sql.*;
import java.util.*;
//possibili snellimenti successivi
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

        String params = "?allowPublicKeyRetrieval=true&useSSL=false&serverTimezone=UTC&useLegacyDatetimeCode=false&useUnicode=true&useJDBCCompliantTimezoneShift=true&zeroDateTimeBehavior=CONVERT_TO_NULL&autoReconnect=true";

        newConnection = DriverManager.getConnection("jdbc:mysql://" + ip + ":" + port + "/" + db + params, username, password);
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
