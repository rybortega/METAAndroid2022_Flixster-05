package com.example.flixster;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import com.example.flixster.databinding.ActivityFavoritesBinding;
import com.example.flixster.databinding.ActivityMainBinding;

public class FavoritesActivity extends AppCompatActivity {

    ActivityFavoritesBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityFavoritesBinding.inflate(getLayoutInflater());
        View rootView = binding.getRoot();
        setContentView(rootView);
        setSupportActionBar(binding.favoritesActivityToolbar);
    }
}