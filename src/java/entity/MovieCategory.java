/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entity;

/**
 *
 * @author Carter Barton
 */
public class MovieCategory {
    private String movieCategoryID;
    private String movieCategoryDescription;

    public MovieCategory(String movieCategoryID, String movieCategoryDescription) {
        this.movieCategoryID = movieCategoryID;
        this.movieCategoryDescription = movieCategoryDescription;
    }

    public String getMovieCategoryId() {
        return movieCategoryID;
    }

    public String getMovieCategoryDescription() {
        return movieCategoryDescription;
    }
    
    @Override
    public String toString() {
        return "ID:" + movieCategoryID + ",Cat:" + movieCategoryDescription;
    }
    
}
