
package db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import entity.Movie;

public class MovieDAO {
    private static Connection conn = null;
    private static PreparedStatement selectAllStatement = null;
    private static PreparedStatement deleteStatement = null;
    private static PreparedStatement insertStatement = null;
    private static PreparedStatement updateStatement = null;
    
    private MovieDAO() {
    }
    
    public static boolean init() {
        if (conn != null) {
            return true;
        }
        try {
            conn = ConnectionManager.getConnection(ConnectionParameters.URL, ConnectionParameters.USERNAME, ConnectionParameters.PASSWORD);
            selectAllStatement = conn.prepareStatement("select * from MOVIE");
            deleteStatement = conn.prepareStatement("delete from MOVIE where MOVIEID = ?");
            insertStatement = conn.prepareStatement("insert into MOVIE values (?,?,?,?)");
            updateStatement = conn.prepareStatement("update MOVIE set MOVIECATEGORYID = ?, TITLE = ?, PRICE = ? where MOVIEID = ?");
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
    
    public static List<Movie> getAllMovies() {
        List<Movie> result = new ArrayList();
        try {
            if (!init()) {
                return result;
            }
            ResultSet rs = selectAllStatement.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("MOVIEID");
                String genre = rs.getString("MOVIECATEGORYID");
                String title = rs.getString("TITLE");
                int price = rs.getInt("PRICE");
                Movie m = new Movie(id, genre, title, price);
                result.add(m);
            }
            System.out.println("************************");
            System.out.println("Found " + result.size() + " items");
        } catch (SQLException ex) {
            System.err.println("************************");
            System.err.println("** Error from getAll ");
            System.err.println("** " + ex.getMessage());
            System.err.println("************************");
        }
        return result;
    }
    
    public static boolean deleteItem(Movie m) {
        return deleteItem(m.getMovieID());
    }
    
    public static boolean deleteItem(int movieID) {
        try {
            if (!init()) 
                return false;
            
            // parameter #1 is the first 'question mark' in the SQL statment
            deleteStatement.setInt(1, movieID); 
            
            int rowCount = deleteStatement.executeUpdate();
            return rowCount == 1;
        } catch (SQLException ex) {
            System.err.println("************************");
            System.err.println("** Error deleting Movie");
            System.err.println(ex.getMessage());
            System.err.println("************************");
            return false;
        }
    }

    public static boolean insertItem(Movie m) {
        try {
            if (!init()) 
                return false;
            insertStatement.setInt(1, m.getMovieID());
            insertStatement.setString(2, m.getMovieCategoryID());
            insertStatement.setString(3, m.getTitle());
            insertStatement.setInt(4, m.getPrice());
            int rowCount = insertStatement.executeUpdate();
            return (rowCount == 1);
        } catch (SQLException ex) {
            System.err.println("************************");
            System.err.println("** Error inserting Movie");
            System.err.println(ex.getMessage());
            System.err.println("************************");
            return false;
        }
    }

    public static boolean updateItem(Movie m) {
        try {
            if (!init()) 
                return false;
            updateStatement.setString(1, m.getMovieCategoryID());
            updateStatement.setString(2, m.getTitle());
            updateStatement.setInt(3, m.getPrice());
            updateStatement.setInt(4, m.getMovieID());
            int rowCount = updateStatement.executeUpdate();
            return (rowCount == 1);
        } catch (SQLException ex) {
            System.err.println("************************");
            System.err.println("** Error updating Movie");
            System.err.println(ex.getMessage());
            System.err.println("************************");
            return false;
        }
    }
}
