package de.kaubisch.movietheatre.presenter;

import de.kaubisch.movietheatre.model.MovieDetail;

/**
 * Created by kaubisch on 10.12.15.
 */
public interface MovieDetailPresenter {
    void loadMovieDetail(final int id);
    void onDetailLoaded(final MovieDetail detail);

}
