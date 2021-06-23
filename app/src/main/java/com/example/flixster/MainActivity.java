package com.example.flixster;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    MoviesAdapter adapter;
    List<Movie> movies;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initViews();
        initRecylerView();
    }

    private void initViews(){
        recyclerView = findViewById(R.id.movie_recyclerview);
    }

    private void initRecylerView(){
        movies = new ArrayList<>();
        movies.add(new Movie("Avatar", "Movie about avatar", "url"));
        movies.add(new Movie("Star Wars", "Movie about wars in the stars", "url"));
        adapter = new MoviesAdapter(movies);

        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }
}