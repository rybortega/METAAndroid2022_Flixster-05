package com.example.flixster;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import com.codepath.asynchttpclient.AsyncHttpClient;
import com.codepath.asynchttpclient.RequestParams;
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler;
import com.example.flixster.databinding.ActivityMainBinding;
import com.example.flixster.model.Movie;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Headers;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;

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
        setSupportActionBar(binding.mainActivityToolbar);

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
        adapter = new MoviesAdapter(movies, (position, transitionView) -> {
            Intent intent = new Intent(MainActivity.this, DetailActivity.class);
            intent.putExtra("movie", movies.get(position));

            startActivity(intent);
        }, this);

        binding.movieRecyclerview.setAdapter(adapter);
        binding.movieRecyclerview.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_favorites:
                showFavorites();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void showFavorites(){
        Intent intent = new Intent(MainActivity.this, FavoritesActivity.class);
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_activity_menu, menu);
        return true;
    }
}