package utility;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public final class JDBCConnection {
    private static final String DB_URL = "jdbc:mysql://localhost:3306/tutorial";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "root";

    private JDBCConnection() {
    }

    static {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException cnfe) {
            throw new ExceptionInInitializerError("MySQL JDBC Driver not found: " + cnfe.getMessage());
        }
    }

    /**
     * Returns a new Connection to the database.
     *
     * @throws SQLException if a database access error occurs
     */
    public static Connection getConnection() {
        try {
            Connection con = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
            return con;

        } catch (SQLException sqle) {
            System.err.println("Failed to close connection: " + sqle.getMessage());
        }
        return null;
    }


    /**
     * Safely closes the given Connection.
     *
     * @param con the Connection to close; ignored if null
     */
    public static void closeConnection(Connection con) {
        if (con != null) {
            try {
                con.close();
                System.out.println("Connection closed.");
            } catch (SQLException sqle) {
                // Log rather than print stack trace in real apps
                System.err.println("Failed to close connection: " + sqle.getMessage());
            }
        }
    }
}
