package de.kaubisch.movietheatre.model;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;

/**
 * Created by kaubisch on 10.12.15.
 */
public class MovieDetailConverter implements UriLoaderTask.JsonConverter<MovieDetail> {

    private final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-mm-dd");

    @Override
    public MovieDetail convertJson(String input) {
        MovieDetail movieDetail = new MovieDetail();
        try {
            JSONObject root = new JSONObject(input);
            movieDetail.title = root.getString("title");
            movieDetail.description = root.getString("overview");
            movieDetail.imagePath = root.getString("poster_path").substring(1);
            movieDetail.releaseDate = sdf.parse(root.getString("release_date"));
            movieDetail.voteAverage = root.getDouble("vote_average");
            movieDetail.durationInMin = root.getInt("runtime");
        } catch (JSONException | ParseException e) {
            e.printStackTrace();
        }

        return movieDetail;
    }
}
