package com.example.flixster;

import android.content.Context;
import android.content.res.Configuration;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.flixster.databinding.MovieListitemBinding;
import com.example.flixster.model.Movie;

import java.util.List;

public class MoviesAdapter extends RecyclerView.Adapter<MoviesAdapter.ViewHolder> {

    public interface onItemClick {
        void onItemClicked(int position);
    }

    private List<Movie> movies;
    private onItemClick clickListener;
    private Context context;

    public MoviesAdapter(List<Movie> movies, onItemClick onItemClick, Context context) {
        this.movies = movies;
        this.context = context;
        this.clickListener = onItemClick;
    }

    @NonNull
    @org.jetbrains.annotations.NotNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull @org.jetbrains.annotations.NotNull ViewGroup parent, int viewType) {
        View todoView = LayoutInflater.from(parent.getContext()).inflate(R.layout.movie_listitem, parent, false);
        return new ViewHolder(todoView);
    }

    @Override
    public void onBindViewHolder(@NonNull @org.jetbrains.annotations.NotNull MoviesAdapter.ViewHolder holder, int position) {
        holder.bind(movies.get(position));
    }

    @Override
    public int getItemCount() {
        return movies.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        MovieListitemBinding binding;

        public ViewHolder(@NonNull @org.jetbrains.annotations.NotNull View itemView) {
            super(itemView);
            binding = MovieListitemBinding.bind(itemView);
        }

        public void bind(Movie movie) {
            binding.movieTitle.setText(movie.name);
            binding.movieText.setText(movie.description);

            String imageUrl;
            if (context.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE){
                imageUrl = movie.backdropUrl;
            } else {
                imageUrl = movie.imageUrl;
            }

            Glide.with(context)
                    .load(imageUrl)
                    .placeholder(R.drawable.flicks_movie_placeholder)
                    .error(R.drawable.flicks_movie_placeholder)
                    .into(binding.movieImageview);

            binding.movieCard.setOnClickListener(v -> clickListener.onItemClicked(getAdapterPosition()));
        }
    }
}

