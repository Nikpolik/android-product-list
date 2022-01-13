package com.nikospolikandriotis.assesment2.recycler;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.bumptech.glide.Glide;
import com.nikospolikandriotis.assesment2.R;
import com.nikospolikandriotis.assesment2.network.Movie;

public class MoviesListViewHolder extends BaseRecyclerView {
    private MoviesListViewAdapter.Listener callback;

    public MoviesListViewHolder(@NonNull View itemView, MoviesListViewAdapter.Listener callback) {
        super(itemView);
        this.callback = callback;
    }

    @Override
    public void bindData(Movie movie) {
        TextView titleView = itemView.findViewById(R.id.movie_row_title);
        titleView.setText(movie.getOriginalTitle());

        ImageView imageView = itemView.findViewById(R.id.movie_row_image);
        Glide.with(itemView.getContext()).load(movie.getPosterUrl()).into(imageView);

        TextView dateView = itemView.findViewById((R.id.movie_row_year));
        if (movie.getReleaseDate() != null) {
            dateView.setText(movie.getReleaseDateString());
        } else {
            dateView.setText("Year not available");
        }

        TextView ratingView = itemView.findViewById(R.id.movie_row_rating);
        ratingView.setText(Double.toString(movie.getVoteAverage()) + "/10");

        TextView descriptionView = itemView.findViewById(R.id.movie_row_description);
        descriptionView.setText(movie.getOverview());

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callback.onItemClick(v, movie.getId());
            }
        });
    }
}
