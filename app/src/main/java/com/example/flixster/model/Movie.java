package com.example.flixster.model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

@Entity(tableName = "favorites")
public class Movie implements Parcelable {

    private final String name;
    private final String description;
    private final String imageUrl;
    private final String backdropUrl;
    private final String releaseDate;
    private final int voteCount;
    private final double voteAverage;
    @PrimaryKey
    private final int id;
    private String ytVideoId = null;

    public Movie(String name, String description, String imageUrl, String backdropUrl, String releaseDate, int voteCount, double voteAverage, int id, String ytVideoId) {
        this.name = name;
        this.description = description;
        this.imageUrl = imageUrl;
        this.backdropUrl = backdropUrl;
        this.releaseDate = releaseDate;
        this.voteCount = voteCount;
        this.voteAverage = voteAverage;
        this.id = id;
        this.ytVideoId = ytVideoId;
    }

    public Movie(JSONObject json) throws JSONException {
        this.name = json.getString("title");
        this.description = json.getString("overview");
        this.releaseDate = json.getString("release_date");
        this.voteCount = json.getInt("vote_count");
        this.voteAverage = json.getDouble("vote_average");
        this.imageUrl = String.format("https://image.tmdb.org/t/p/w342/%s", json.getString("poster_path"));
        this.backdropUrl = String.format("https://image.tmdb.org/t/p/w342/%s", json.getString("backdrop_path"));
        this.id = json.getInt("id");
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
        dest.writeString(this.getName());
        dest.writeString(this.getDescription());
        dest.writeString(this.getImageUrl());
        dest.writeString(this.getBackdropUrl());
        dest.writeString(this.getReleaseDate());
        dest.writeInt(this.getVoteCount());
        dest.writeDouble(this.getVoteAverage());
        dest.writeInt(this.getId());
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
        this.id = in.readInt();
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public String getBackdropUrl() {
        return backdropUrl;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public int getVoteCount() {
        return voteCount;
    }

    public double getVoteAverage() {
        return voteAverage;
    }

    public int getId() {
        return id;
    }

    public String getYtVideoId() {
        return ytVideoId;
    }

    public void setYtVideoId(String id) {
        this.ytVideoId = id;
    }

    public boolean hasYtVideoId(){
        return this.ytVideoId != null;
    }
}
