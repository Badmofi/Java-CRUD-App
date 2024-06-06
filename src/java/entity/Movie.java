/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entity;

/**
 *
 * @author Carter Barton
 */
public class Movie {
    private int movieID;
    private String movieCategoryID;
    private String title;
    private int price;
    
    public Movie(int movieID, String movieCategoryID, String title, int price) {
        this.movieID = movieID;
        this.movieCategoryID = movieCategoryID;
        this.title = title;
        this.price = price;
    }
    
    public int getMovieID() {
        return movieID;
    }

    public String getMovieCategoryID() {
        return movieCategoryID;
    }

    public String getTitle() {
        return title;
    }

    public int getPrice() {
        return price;
    }

    @Override
    public String toString() {
        return "ID:" + movieID + ",Cat:" + movieCategoryID +",Title:" + title;
    }
}

