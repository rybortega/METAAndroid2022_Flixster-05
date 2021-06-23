package com.example.flixster;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.flixster.model.Movie;

import java.util.List;

public class MoviesAdapter extends RecyclerView.Adapter<MoviesAdapter.ViewHolder> {

    public interface onItemClick {
        void onItemLongClicked(int position);
        void onItemClicked(int position);
    }

    List<Movie> movies;
    onItemClick clickListener;
    Context context;

    public MoviesAdapter(List<Movie> movies, Context context) {
        this.movies = movies;
        this.context = context;
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

        ImageView image;
        TextView title;
        TextView text;

        public ViewHolder(@NonNull @org.jetbrains.annotations.NotNull View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.movie_imageview);
            title = itemView.findViewById(R.id.movie_title);
            text = itemView.findViewById(R.id.movie_text);
        }

        public void bind(Movie movie) {
            title.setText(movie.name);
            text.setText(movie.description);
            Glide.with(context)
                    .load(movie.imageUrl)
                    .placeholder(R.drawable.flicks_movie_placeholder)
                    .error(R.drawable.flicks_movie_placeholder)
                    .into(image);
        }
    }
}

