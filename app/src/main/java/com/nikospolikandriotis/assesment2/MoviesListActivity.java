package com.nikospolikandriotis.assesment2;

import androidx.annotation.Nullable;
import android.widget.SearchView;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.nikospolikandriotis.assesment2.network.Movie;
import com.nikospolikandriotis.assesment2.recycler.RecyclerViewAdapter;

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
        JsonObjectRequest stringRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        // Display the first 500 characters of the response string.
                        try {
                            JSONArray results = response.getJSONArray("results");
                            List<Movie> movies = new ArrayList();
                            int totalResults = Math.min(results.length(), 15);
                            for(int i = 0; i < totalResults; i++) {
                                movies.add(Movie.build(results.getJSONObject(i)));
                            }
                            displayMovies(movies);
                        } catch (Exception e) {
                            Log.e("Tag", e.toString());
                            return;
                        }
                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("Response Error", "That didn't work!");
            }
        });
        queue.add(stringRequest);
    }

    private void displayMovies(List<Movie> movies) {
        RecyclerView recyclerView = findViewById(R.id.recycler_view_list);
        RecyclerViewAdapter adapter = new RecyclerViewAdapter(movies, new RecyclerViewAdapter.Listener() {
            @Override
            public void onItemClick(View view, int id) {
                if (!isClicked) {
                    isClicked = true;
                    Intent intent = new Intent(MoviesListActivity.this, MovieDetailsActivity.class);
                    intent.putExtra("id", id);
                    startActivity(intent);
                }

            }
        });

        recyclerView.setAdapter(adapter);
    }
}
