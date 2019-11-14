package com.example.glide;

import java.util.List;

public class Cat {

    /**
     * breeds : []
     * id : 2og
     * url : https://cdn2.thecatapi.com/images/2og.jpg
     * width : 500
     * height : 331
     */

    private String id;
    private String url;
    private int width;
    private int height;
    private List<?> breeds;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public List<?> getBreeds() {
        return breeds;
    }

    public void setBreeds(List<?> breeds) {
        this.breeds = breeds;
    }
}
