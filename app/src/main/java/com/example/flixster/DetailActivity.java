package com.example.flixster;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.flixster.model.Movie;

public class DetailActivity extends AppCompatActivity {

    private ImageView image;
    private TextView title;
    private TextView ratingText;
    private RatingBar ratingBar;
    private TextView overviewText;
    private TextView releaseText;

    private Movie movie;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        movie = getIntent().getParcelableExtra("movie");

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(movie.name);
        initViews();

    }

    private void initViews(){
        image = findViewById(R.id.detail_movie_image);
        title = findViewById(R.id.detail_movie_title);
        ratingText = findViewById(R.id.details_rating_text);
        ratingBar = findViewById(R.id.detail_movie_ratingbar);
        overviewText = findViewById(R.id.detail_movie_overview);
        releaseText = findViewById(R.id.detail_release_date_text);

        Glide.with(this)
                .load(movie.backdropUrl)
                .placeholder(R.drawable.flicks_movie_placeholder)
                .error(R.drawable.flicks_movie_placeholder)
                .into(image);

        title.setText(movie.name);
        overviewText.setText(movie.description);

        ratingText.setText(movie.voteAverage + " (" + movie.voteCount + " votes)");
        ratingBar.setRating((float) movie.voteAverage / 2);
        releaseText.setText(movie.releaseDate);

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

