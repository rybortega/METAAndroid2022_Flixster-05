package com.example.flixster;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.flixster.databinding.ActivityDetailBinding;
import com.example.flixster.model.Movie;

public class DetailActivity extends AppCompatActivity {

    private ActivityDetailBinding binding;
    private Movie movie;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDetailBinding.inflate(getLayoutInflater());
        View rootView = binding.getRoot();
        setContentView(rootView);

        movie = getIntent().getParcelableExtra("movie");

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(movie.name);
        initViews();
    }

    private void initViews(){
        Glide.with(this)
                .load(movie.backdropUrl)
                .placeholder(R.drawable.flicks_movie_placeholder)
                .error(R.drawable.flicks_movie_placeholder)
                .into(binding.detailMovieImage);

        binding.detailMovieTitle.setText(movie.name);
        binding.detailMovieOverview.setText(movie.description);

        binding.detailsRatingText.setText(movie.voteAverage + " (" + movie.voteCount + " votes)");
        binding.detailMovieRatingbar.setRating((float) movie.voteAverage / 2);
        binding.detailReleaseDateText.setText(movie.releaseDate);

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}

