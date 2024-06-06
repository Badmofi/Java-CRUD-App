package db;

import java.sql.Connection;
import java.sql.DriverManager;

public class ConnectionManager {

    private static Connection conn = null;


    static Connection getConnection(String url, String user, String password) {
        if (conn == null) {
            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
                conn = DriverManager.getConnection(url, user, password);
            } catch (Exception ex) {
                System.err.println("************************");
                System.err.println("** Error opening DB");
                System.err.println("** " + ex.getMessage());
                System.err.println("************************");
                return null;
            }
        }
        return conn;
    }

}