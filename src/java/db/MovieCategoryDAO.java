
package db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import entity.MovieCategory;


public class MovieCategoryDAO {
    
    private static Connection conn = null;
    private static PreparedStatement selectAllStatement = null;
    
    // constructor is private - no instantiation allowed
    private MovieCategoryDAO() {
    }
    
    public static boolean init() {
        if (conn != null) {
            return true;
        }
        try {
            conn = ConnectionManager.getConnection(ConnectionParameters.URL, ConnectionParameters.USERNAME, ConnectionParameters.PASSWORD);
            selectAllStatement = conn.prepareStatement("select * from MOVIECATEGORY");
            return true;
        } catch (SQLException ex) {
            System.err.println("************************");
            System.err.println("** Error preparing SQL");
            System.err.println("** " + ex.getMessage());
            System.err.println("************************");
            conn = null;
        }
        return false;
    }
    
    public static List<MovieCategory> getAllGenres() {
        List<MovieCategory> result = new ArrayList();
        try {
            if (!init())
                return result;
            ResultSet rs = selectAllStatement.executeQuery();
            while (rs.next()) {
                String cat = rs.getString("MOVIECATEGORYID");
                String desc = rs.getString("MOVIECATEGORYDESCRIPTION");
                MovieCategory tempcat = new MovieCategory(cat, desc);
                result.add(tempcat);
            }
            System.out.println("************************");
            System.out.println("Found " + result.size() + " categories");
        } catch (SQLException ex) {
            System.err.println("************************");
            System.err.println("** Error getting Categories");
            System.err.println("** " + ex.getMessage());
            System.err.println("************************");
        }
        return result;
    }
}
