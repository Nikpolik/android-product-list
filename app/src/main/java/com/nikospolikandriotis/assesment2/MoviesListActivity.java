package com.nikospolikandriotis.assesment2;

import androidx.annotation.Nullable;

import android.view.View;
import android.widget.SearchView;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.android.volley.NetworkError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.snackbar.Snackbar;
import com.nikospolikandriotis.assesment2.network.Movie;
import com.nikospolikandriotis.assesment2.recycler.MoviesListViewAdapter;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MoviesListActivity extends AbstractActivity {

    private boolean isClicked = false;

    @Override
    int getLayout() {
        return R.layout.activity_movies_list;
    }

    @Override
    void uiSetup() {
        SearchView searchView = findViewById(R.id.movies_list_search);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

            @Override
            public boolean onQueryTextSubmit(String s) {
                searchMovies(s);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                return false;
            }
        });
    }

    @Override
    void startOperations() {

    }

    @Override
    void stopOperations() {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        searchMovies("hangover");
    }

    @Override
    protected void onPostResume() {
        this.isClicked = false;
        super.onPostResume();
    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
    }

    private void searchMovies(String searchTerm) {
        Log.d("Firing request", "Firing Request");
        RequestQueue queue = Volley.newRequestQueue(this);
        String apiToken = getResources().getString(R.string.movie_db_secret);
        String url = getResources().getString(R.string.search_movies);
        url += "?api_key=" + apiToken;
        url += "&query=" + searchTerm;
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        // Display the first 500 characters of the response string.
                        try {
                            JSONArray results = response.getJSONArray("results");
                            List<Movie> movies = new ArrayList<>();
                            int totalResults = Math.min(results.length(), 15);
                            for (int i = 0; i < totalResults; i++) {
                                movies.add(Movie.build(results.getJSONObject(i)));
                            }
                            displayMovies(movies);
                        } catch (Exception e) {
                            Log.e("Tag", e.toString());
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

    private void displayMovies(List<Movie> movies) {
        RecyclerView recyclerView = findViewById(R.id.recycler_view_list);
        MoviesListViewAdapter adapter = new MoviesListViewAdapter(movies, (view, id) -> {
            if (!isClicked) {
                isClicked = true;
                Intent intent = new Intent(MoviesListActivity.this, MovieDetailsActivity.class);
                Movie movie = null;
                for (Movie currentMovie : movies) {
                    if(currentMovie.getId() == id) {
                        movie = currentMovie;
                        break;
                    }
                }

                if (movie == null) {
                    return;
                }

                intent.putExtra("movie", movie);
                startActivity(intent);
            }
        });

        recyclerView.setAdapter(adapter);
    }
}
