
package controller;

import com.google.gson.Gson;
import db.MovieDAO;
import entity.Movie;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Scanner;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "MovieService", urlPatterns = {"/MovieService/movies", "/MovieService/movies/*"})
public class MovieService extends HttpServlet{
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try (PrintWriter out = response.getWriter()) {
            List<Movie> allItems = MovieDAO.getAllMovies();
            Gson g = new Gson();
            out.println(g.toJson(allItems));
        }
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
       try (PrintWriter out = response.getWriter()) {
            Scanner sc = new Scanner(request.getReader());
            String jsonData = sc.nextLine(); // payload is a single string
            Gson g = new Gson();
            Movie m = g.fromJson(jsonData, Movie.class);
            boolean success = MovieDAO.insertItem(m);
            out.println(success);
        }
    }
    
    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
       try (PrintWriter out = response.getWriter()) {
            Scanner sc = new Scanner(request.getReader());
            String jsonData = sc.nextLine();
            Gson g = new Gson();
            Movie m = g.fromJson(jsonData, Movie.class);
            boolean success = MovieDAO.updateItem(m);
            out.println(success);
        }
    }
    
    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try (PrintWriter out = response.getWriter()) {
            
            int movieID = Integer.parseInt(request.getPathInfo().substring(1));
            boolean success = MovieDAO.deleteItem(movieID);
            out.println(success);
        }
    }
    
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>
}
