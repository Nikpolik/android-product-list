package com.nikospolikandriotis.assesment2.recycler;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.nikospolikandriotis.assesment2.R;
import com.nikospolikandriotis.assesment2.network.Movie;

import java.util.List;


public class RecyclerViewAdapter extends RecyclerView.Adapter<BaseRecyclerView> {
    public interface Listener {
        public void onItemClick(View view, int data);
    }

    @NonNull
    private List<Movie> movies;
    private Listener callback;

    public RecyclerViewAdapter(List<Movie> movies, Listener callback) {
        this.movies = movies;
        this.callback = callback;
    }

    @NonNull
    @Override
    public BaseRecyclerView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(viewType, parent, false);
        return new RecyclerViewHolder(view, callback);
    }

    @Override
    public void onBindViewHolder(@NonNull BaseRecyclerView holder, int position) {
        Movie movie = this.movies.get(position);
        if(movie == null) {
            return;
        }

        holder.bindData(movie);
    }

    @Override
    public int getItemCount() {
        return movies.size();
    }

    @Override
    public int getItemViewType(int position) {
        return R.layout.movie_row;
    }
}
