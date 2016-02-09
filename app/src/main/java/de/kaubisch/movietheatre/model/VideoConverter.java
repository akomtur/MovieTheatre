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
public class VideoConverter implements UriLoaderTask.JsonConverter<Collection<Trailer>> {
    @Override
    public Collection<Trailer> convertJson(String input) {
        List<Trailer> trailerList = new ArrayList<>();
        try {
            JSONObject root = new JSONObject(input);
            JSONArray results = root.getJSONArray("results");
            for (int i = 0; i < results.length(); i++) {
                JSONObject jsonTrailer = results.getJSONObject(i);
                Trailer trailer = new Trailer();
                trailer.id = jsonTrailer.getString("key");
                trailer.name = jsonTrailer.getString("name");
                trailer.site = jsonTrailer.getString("site");
                trailer.type = jsonTrailer.getString("type");
                trailerList.add(trailer);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return trailerList;
    }
}
