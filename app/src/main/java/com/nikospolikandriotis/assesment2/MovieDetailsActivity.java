package com.nikospolikandriotis.assesment2;

import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.NetworkError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.google.android.material.snackbar.Snackbar;
import com.nikospolikandriotis.assesment2.network.Cast;
import com.nikospolikandriotis.assesment2.network.Movie;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class MovieDetailsActivity extends AbstractActivity {

    @Override
    int getLayout() {
        return R.layout.activity_movie_details;
    }

    @Override
    void uiSetup() {
        Movie movie = (Movie) getIntent().getSerializableExtra("movie");

        if (movie == null) {
            return;
        }

        bindViewData(movie);
        setupHandlers(movie);
    }

    @Override
    void startOperations() {

    }

    @Override
    void stopOperations() {
    }

    private void bindViewData(Movie movie) {
        if (movie == null) {
           return;
        }

        ImageView posterView = findViewById(R.id.movie_details_poster);
        Glide.with(posterView.getContext()).load(movie.getPosterUrl()).into(posterView);

        ImageView backdropView = findViewById(R.id.movie_details_backdrop);
        Glide.with(backdropView.getContext()).load(movie.getBackdropUrl()).into(backdropView);

        TextView titleView = findViewById(R.id.movie_details_title);
        TextView descView = findViewById(R.id.movie_details_description);
        TextView releaseDateView = findViewById(R.id.movie_details_release_date);
        TextView ratingView = findViewById(R.id.movie_details_rating);

        titleView.setText(movie.getOriginalTitle());
        descView.setText(movie.getOverview());
        if (movie.getReleaseDate() != null) {
            releaseDateView.setText(movie.getReleaseDateString());
        }
        ratingView.setText(movie.getVoteAverage() + "/10");

        getCast(movie.getId());
    }

    private void setupHandlers(Movie movie) {
        Button button = findViewById(R.id.movie_details_btn);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent shareIntent = new Intent();
                shareIntent.setAction(Intent.ACTION_SEND);
                shareIntent.putExtra(Intent.EXTRA_TEXT, getShareMessage(movie.getId()));
                shareIntent.setType("text/plain");
                startActivity(shareIntent);
            }
        });

    }

    private String getShareMessage(int id) {
        return "Check this movie! http://themoviedb.org/movie/" + id;
    }

    private void getCast(int movieId) {
        Log.d("Firing request", "Firing Request");
        RequestQueue queue = Volley.newRequestQueue(this);
        String apiToken = getResources().getString(R.string.movie_db_secret);
        String url = getResources().getString(R.string.get_cast);
        url = url.replaceAll("(\\{movie_id\\})", Integer.toString(movieId));
        Log.d("url", url);
        url += "?api_key=" + apiToken;

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        // Display the first 500 characters of the response string.
                        try {
                            JSONArray results = response.getJSONArray("cast");
                            List<Cast> castList = new ArrayList<>();
                            int totalResults = Math.min(results.length(), 15);
                            for(int i = 0; i < totalResults; i++) {
                                castList.add(
                                    Cast.build(results.getJSONObject(i))
                                );
                            }
                            displayCast(castList);
                        } catch (Exception error) {
                            Log.d("Response Parse Error", error.getMessage());
                        }
                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                if (error instanceof NetworkError) {
                    View parentLayout = findViewById(android.R.id.content);
                    Snackbar bar = Snackbar.make(parentLayout, "Network Error", Snackbar.LENGTH_SHORT);
                    View barView = bar.getView();
                    barView.setBackgroundColor(getResources().getColor(R.color.red));
                    bar.show();
                } else {
                    Log.d("Response Error", error.getMessage());
                }
            }
        });
        queue.add(request);
    }

    private void displayCast(List<Cast> castList) {
        String formattedCast = castList
                                        .stream()
                                        .map((cast -> cast.toString()))
                                        .collect(Collectors.joining(", "));

        TextView castView = findViewById(R.id.movie_details_cast);
        castView.setText(formattedCast);
    }
}