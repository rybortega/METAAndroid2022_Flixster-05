package com.example.flixster;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.example.flixster.databinding.ActivityFavoritesBinding;
import com.example.flixster.model.AppDatabaseProvider;
import com.example.flixster.model.Movie;
import com.example.flixster.model.MovieDao;

import java.util.ArrayList;
import java.util.List;

public class FavoritesActivity extends AppCompatActivity {

    ActivityFavoritesBinding binding;
    private MoviesAdapter adapter;
    private List<Movie> movies;
    private MovieDao movieDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityFavoritesBinding.inflate(getLayoutInflater());
        View rootView = binding.getRoot();
        setContentView(rootView);
        setSupportActionBar(binding.favoritesActivityToolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        movies = new ArrayList<>();
        movieDao = AppDatabaseProvider.getInstance(this).movieDao();
        initRecyclerView();
    }

    @Override
    protected void onStart() {
        fetchMovies();
        super.onStart();
    }

    private void fetchMovies(){
        movies.clear();
        movies.addAll(movieDao.getAllFavorites());
        adapter.notifyDataSetChanged();
    }

    private void initRecyclerView(){
        adapter = new MoviesAdapter(movies, new MoviesAdapter.onItemClick() {
            @Override
            public void onItemClicked(int position, View transitionView) {
                Intent intent = new Intent(FavoritesActivity.this, DetailActivity.class);
                intent.putExtra("movie", movies.get(position));

                startActivity(intent);
            }
        }, this);
        binding.favoritesRecyclerview.setAdapter(adapter);
        binding.favoritesRecyclerview.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                this.finish();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }
}