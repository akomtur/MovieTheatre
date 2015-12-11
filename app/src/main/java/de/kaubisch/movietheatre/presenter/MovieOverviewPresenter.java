package de.kaubisch.movietheatre.presenter;

import java.util.List;

import de.kaubisch.movietheatre.Movie;

/**
 * Created by kaubisch on 09.12.15.
 */
public interface MovieOverviewPresenter {
    void updateOverviewData(final boolean sortByRating);
    void onModelUpdate(List<Movie> movies);
}
