package de.kaubisch.movietheatre.model;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by kaubisch on 10.12.15.
 */
public class MovieOverviewConverter implements UriLoaderTask.JsonConverter<List<Movie>> {
    @Override
    public List<Movie> convertJson(String input) {
        try {
            JSONObject root = new JSONObject(input);
            JSONArray results = root.getJSONArray("results");
            List<Movie> movies = new ArrayList<>();

            for (int i = 0; i < results.length(); i++) {
                JSONObject movie = results.getJSONObject(i);
                Movie movieObj = new Movie();
                movieObj.id = movie.getInt("id");
                movieObj.name = movie.getString("title");
                movieObj.image = movie.getString("poster_path").substring(1);
                movieObj.rating = movie.getDouble("vote_average");
                movies.add(movieObj);
            }
            return movies;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return Collections.EMPTY_LIST;
    }
}
