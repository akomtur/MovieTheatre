package de.kaubisch.movietheatre.model;

import android.net.Uri;

import de.kaubisch.movietheatre.BuildConfig;
import de.kaubisch.movietheatre.presenter.MovieDetailPresenter;

/**
 * Created by kaubisch on 10.12.15.
 */
public class ApiMovieDetailModel implements DetailModel {
    private MovieDetailPresenter presenter;

    private final UriLoaderTask.JsonConverter<MovieDetail> detailConverter = new MovieDetailConverter();

    public ApiMovieDetailModel(MovieDetailPresenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void loadMovieDetail(int id) {
        Uri uri = Uri.parse("http://api.themoviedb.org/3/movie").buildUpon()
                .appendPath(String.valueOf(id))
                .appendQueryParameter("api_key", BuildConfig.MOVIE_API_KEY)
                .build();

        new UriLoaderTask<>(detailConverter, new UriLoaderTask.UriLoaderListener<MovieDetail>() {
            @Override
            public void onDataLoaded(MovieDetail result) {
                presenter.onDetailLoaded(result);
            }
        }).execute(uri);
    }
}
