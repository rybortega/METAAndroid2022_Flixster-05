package com.example.flixster.model;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Movie implements Parcelable {

    public String name;
    public String description;
    public String imageUrl;
    public String backdropUrl;
    public String releaseDate;
    public int voteCount;
    public double voteAverage;

    public Movie(String name, String description, String imageUrl, String backdropUrl) {
        this.name = name;
        this.description = description;
        this.imageUrl = imageUrl;
        this.backdropUrl = backdropUrl;
    }

    public Movie(JSONObject json) throws JSONException {
        this.name = json.getString("title");
        this.description = json.getString("overview");
        this.releaseDate = json.getString("release_date");
        this.voteCount = json.getInt("vote_count");
        this.voteAverage = json.getDouble("vote_average");
        this.imageUrl = String.format("https://image.tmdb.org/t/p/w342/%s", json.getString("poster_path"));
        this.backdropUrl = String.format("https://image.tmdb.org/t/p/w342/%s", json.getString("backdrop_path"));
    }

    public static List<Movie> fromJsonArray(JSONArray array) throws JSONException {
        List<Movie> movies = new ArrayList<>();
        for (int i = 0; i < array.length(); i++){
            movies.add(new Movie(array.getJSONObject(i)));
        }

        return movies;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.name);
        dest.writeString(this.description);
        dest.writeString(this.imageUrl);
        dest.writeString(this.backdropUrl);
        dest.writeString(this.releaseDate);
        dest.writeInt(this.voteCount);
        dest.writeDouble(this.voteAverage);
    }

    public static final Parcelable.Creator<Movie> CREATOR = new Creator<Movie>() {
        @Override
        public Movie createFromParcel(Parcel source) {
            return new Movie(source);
        }

        @Override
        public Movie[] newArray(int size) {
            return new Movie[size];
        }
    };

    private Movie(Parcel in){
        this.name = in.readString();
        this.description = in.readString();
        this.imageUrl = in.readString();
        this.backdropUrl = in.readString();
        this.releaseDate = in.readString();
        this.voteCount = in.readInt();
        this.voteAverage = in.readDouble();
    }
}
