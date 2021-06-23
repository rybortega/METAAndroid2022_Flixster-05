package com.example.flixster.model;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Movie {

    public String name;
    public String description;
    public String imageUrl;

    public Movie(String name, String description, String imageUrl) {
        this.name = name;
        this.description = description;
        this.imageUrl = imageUrl;
    }

    public Movie(JSONObject json) throws JSONException {
        this.name = json.getString("title");
        this.description = json.getString("overview");
        this.imageUrl = String.format("https://image.tmdb.org/t/p/w342/%s", json.getString("poster_path"));
    }

    public static List<Movie> fromJsonArray(JSONArray array) throws JSONException {
        List<Movie> movies = new ArrayList<>();
        for (int i = 0; i < array.length(); i++){
            movies.add(new Movie(array.getJSONObject(i)));
        }

        return movies;
    }
}
