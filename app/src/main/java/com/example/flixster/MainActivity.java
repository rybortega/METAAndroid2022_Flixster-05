package com.example.flixster;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.codepath.asynchttpclient.AsyncHttpClient;
import com.codepath.asynchttpclient.RequestParams;
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler;
import com.codepath.asynchttpclient.callback.TextHttpResponseHandler;
import com.example.flixster.model.Movie;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Headers;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private MoviesAdapter adapter;
    private List<Movie> movies;

    public static final String NOW_PLAYING_URL = "https://api.themoviedb.org/3/movie/now_playing?api_key=a9e7a1d1bbdb2899ade1a8438fada07b";
    public static final String TAG = "MainActivity.java";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        movies = new ArrayList<>();

        initViews();
        fetchMovies();
        initRecyclerView();
    }

    private void fetchMovies(){
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();
        params.put("page", 1);
        client.get(NOW_PLAYING_URL, params, new JsonHttpResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, Headers headers, JSON json) {
                        JSONObject object = json.jsonObject;
                        try {
                            JSONArray results = object.getJSONArray("results");
                            Log.i(TAG, "Successfully fetched movie data");
                            movies.addAll(Movie.fromJsonArray(results));
                            adapter.notifyDataSetChanged();

                        } catch (JSONException e){
                            Log.e(TAG, "JSON exception when parsing movie results", e);
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onFailure(int statusCode, Headers headers, String errorResponse, Throwable t) {
                        Log.w(TAG, errorResponse);
                    }
                }
        );
    }

    private void initViews(){
        recyclerView = findViewById(R.id.movie_recyclerview);
    }

    private void initRecyclerView(){
        adapter = new MoviesAdapter(movies, position -> {
            Intent intent = new Intent(MainActivity.this, DetailActivity.class);
            intent.putExtra("movie", movies.get(position));
            startActivity(intent);
        }, this);

        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }
}