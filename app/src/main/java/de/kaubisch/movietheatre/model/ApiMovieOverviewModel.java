package de.kaubisch.movietheatre.model;

import android.net.Uri;

import java.util.List;

import de.kaubisch.movietheatre.BuildConfig;
import de.kaubisch.movietheatre.presenter.MovieOverviewPresenter;

/**
 * Created by kaubisch on 09.12.15.
 */
public class ApiMovieOverviewModel implements MoviesModel {
    private final MovieOverviewPresenter presenter;

    private final UriLoaderTask.JsonConverter<List<Movie>> overviewConverter = new MovieOverviewConverter();

    public ApiMovieOverviewModel(MovieOverviewPresenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void loadMovieOverview(final boolean sortByRating) {
        Uri uri = Uri.parse("http://api.themoviedb.org/3/discover/movie").buildUpon()
                .appendQueryParameter("api_key", BuildConfig.MOVIE_API_KEY)
                .appendQueryParameter("sort_by", sortByRating ? "vote_average.desc" : "popularity.desc")
                .build();

        new UriLoaderTask<>(overviewConverter, new UriLoaderTask.UriLoaderListener<List<Movie>>() {
            @Override
            public void onDataLoaded(List<Movie> result) {
                presenter.onModelUpdate(result);
            }
        }).execute(uri);
    }
}
