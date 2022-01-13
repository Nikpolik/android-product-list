package com.nikospolikandriotis.assesment2.network;

import android.util.Log;

import androidx.annotation.Nullable;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Movie {
    private int id;
    private String posterUrl;
    private String backdropUrl;
    @Nullable
    private Date releaseDate;
    private String originalTitle;
    private String overview;

    public double getVoteAverage() {
        return voteAverage;
    }

    public void setVoteAverage(double voteAverage) {
        this.voteAverage = voteAverage;
    }

    private double voteAverage;

    public int getId() {
        return id;
    }

    public String getPosterUrl() {
        if (posterUrl.equals("null")) {
            return null;
        }

        return "https://image.tmdb.org/t/p/w500" + posterUrl;
    }

    public Date getReleaseDate() {
        return releaseDate;
    }

    public String getOriginalTitle() {
        return originalTitle;
    }

    public String getOverview() {
        return overview;
    }

    public String getReleaseDateString() {
        return new SimpleDateFormat("dd/MM/yyyy").format(releaseDate);
    }

    public static Movie build(JSONObject object) throws JSONException, ParseException {
        Log.d("Movie ---- > ", object.toString());
        Movie movie = new Movie();
        movie.id = object.getInt("id");
        movie.originalTitle = object.getString("original_title");
        movie.posterUrl = object.getString("poster_path");
        movie.overview = object.getString("overview");
        movie.voteAverage = object.getDouble("vote_average");
        movie.backdropUrl = object.getString("backdrop_url");

        String dateString = object.getString("release_date");
        if(dateString != null && !dateString.equals("")) {
            movie.releaseDate = new SimpleDateFormat("yyyy-dd-MM").parse(dateString);
        }

        return movie;
    }
}
