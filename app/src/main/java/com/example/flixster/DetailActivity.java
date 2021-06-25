package com.example.flixster;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.codepath.asynchttpclient.AsyncHttpClient;
import com.codepath.asynchttpclient.RequestParams;
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler;
import com.example.flixster.databinding.ActivityDetailBinding;
import com.example.flixster.model.Movie;
import com.google.android.youtube.player.YouTubeIntents;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import okhttp3.Headers;

public class DetailActivity extends AppCompatActivity {

    private ActivityDetailBinding binding;
    private Movie movie;
    private String ytVideoId;

    public static final String TAG = "DetailActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDetailBinding.inflate(getLayoutInflater());
        View rootView = binding.getRoot();
        setContentView(rootView);
        setSupportActionBar(binding.mainActivityToolbar);

        movie = getIntent().getParcelableExtra("movie");

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(movie.getName());
        initViews();
        fetchYtVideoId();
    }


    private void fetchYtVideoId(){
        SharedPreferences sharedPref = getSharedPreferences(getString(R.string.preference_file_key), Context.MODE_PRIVATE);
        String key = movie.getId() + "*yt_video_id";
        if (sharedPref.contains(key)){
            ytVideoId = sharedPref.getString(key, null);
            return;
        }

        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();
        params.put("api_key", getString(R.string.movie_db_key));
        String url = "https://api.themoviedb.org/3/movie/" + movie.getId() + "/videos";
        client.get(url, params, new JsonHttpResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, Headers headers, JSON json) {
                        JSONObject object = json.jsonObject;
                        try {
                            ytVideoId = object.getJSONArray("results").getJSONObject(0).getString("key");
                            Log.i(TAG, "Successfully fetched movie video data");
                            sharedPref.edit().putString(key, ytVideoId).apply();
                        } catch (JSONException e){
                            Log.e(TAG, "JSON exception when parsing movie video data", e);
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

        Glide.with(this)
                .load(movie.getBackdropUrl())
                .placeholder(R.drawable.flicks_movie_placeholder)
                .error(R.drawable.flicks_movie_placeholder)
                .into(binding.detailMovieImage);

        binding.detailMovieTitle.setText(movie.getName());
        binding.detailMovieOverview.setText(movie.getDescription());

        binding.detailsRatingText.setText(movie.getVoteAverage() + " (" + movie.getVoteCount() + " votes)");

        float ratingBarValue = (float) movie.getVoteAverage() / 2;
        ObjectAnimator anim = ObjectAnimator.ofFloat(binding.detailMovieRatingbar, "rating", 0, ratingBarValue);
        anim.setDuration(500);
        anim.start();

        binding.detailReleaseDateText.setText(movie.getReleaseDate());

        binding.detailMovieImage.setOnClickListener(v -> {
            if (ytVideoId == null){
                Toast.makeText(this, "There was an error. Please try again", Toast.LENGTH_LONG).show();
                return;
            }

            startActivity(YouTubeIntents.createPlayVideoIntent(this, ytVideoId));
        });
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                supportFinishAfterTransition();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}

