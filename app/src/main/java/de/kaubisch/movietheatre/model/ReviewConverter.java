package de.kaubisch.movietheatre.model;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by kaubisch on 06.02.16.
 */
public class ReviewConverter implements UriLoaderTask.JsonConverter<Collection<Review>> {
    @Override
    public Collection<Review> convertJson(String input) {
        List<Review> reviewList = new ArrayList<>();
        try {
            JSONObject root = new JSONObject(input);
            JSONArray results = root.getJSONArray("results");
            for (int i = 0; i < results.length(); i++) {
                JSONObject jsonReview = results.getJSONObject(i);
                reviewList.add(new Review(jsonReview.getString("author"), jsonReview.getString("content")));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return reviewList;
    }
}
