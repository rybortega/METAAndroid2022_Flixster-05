package com.example.flixster;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.codepath.asynchttpclient.AsyncHttpClient;
import com.codepath.asynchttpclient.RequestParams;
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler;
import com.codepath.asynchttpclient.callback.TextHttpResponseHandler;
import com.example.flixster.databinding.ActivityMainBinding;
import com.example.flixster.model.Movie;
import com.google.android.youtube.player.YouTubeIntents;
import com.google.android.youtube.player.YouTubeStandalonePlayer;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Headers;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;

//    private RecyclerView recyclerView;
    private MoviesAdapter adapter;
    private List<Movie> movies;

    public static final String NOW_PLAYING_URL = "https://api.themoviedb.org/3/movie/now_playing";
    public static final String TAG = "MainActivity.java";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        View rootView = binding.getRoot();
        setContentView(rootView);

        movies = new ArrayList<>();

        fetchMovies();
        initRecyclerView();
    }

    private void fetchMovies(){
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();
        params.put("api_key", getString(R.string.movie_db_key));
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

    private void initRecyclerView(){
        adapter = new MoviesAdapter(movies, position -> {
            Intent intent = new Intent(MainActivity.this, DetailActivity.class);
            intent.putExtra("movie", movies.get(position));
            startActivity(intent);
        }, this);

        binding.movieRecyclerview.setAdapter(adapter);
        binding.movieRecyclerview.setLayoutManager(new LinearLayoutManager(this));
    }
}