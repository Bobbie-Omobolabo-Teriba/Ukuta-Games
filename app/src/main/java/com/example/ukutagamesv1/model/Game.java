package com.example.ukutagamesv1.model;

public class Game {

    private Integer id;
    private String name;
    private String platforms;
    private String release;
    private String image_url;
    private String rating;
    private String genres;

    public Game(){

    }

    public Game(Integer id, String name, String platforms, String release, String image_url, String rating, String genres) {
        this.id = id;
        this.name = name;
        this.platforms = platforms;
        this.release = release;
        this.image_url = image_url;
        this.rating = rating;
        this.genres = genres;
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getPlatforms() {
        return platforms;
    }

    public String getRelease() {
        return release;
    }

    public String getImage_url() {
        return image_url;
    }

    public String getRating() {
        return rating;
    }

    public String getGenres() {
        return genres;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPlatforms(String platforms) {
        this.platforms = platforms;
    }

    public void setRelease(String release) {
        this.release = release;
    }

    public void setImage_url(String image_url) {
        this.image_url = image_url;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public void setGenres(String genres) {
        this.genres = genres;
    }
}
