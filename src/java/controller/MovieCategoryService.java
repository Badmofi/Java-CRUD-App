
package controller;

import com.google.gson.Gson;
import db.MovieCategoryDAO;
import entity.MovieCategory;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "MovieCategoryService", urlPatterns = {"/MovieService/genres"})
public class MovieCategoryService extends HttpServlet{
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try (PrintWriter out = response.getWriter()) {
            List<MovieCategory> g = MovieCategoryDAO.getAllGenres();
            Gson gs = new Gson();
            out.println(gs.toJson(g));
        }
    }
    
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>
}
