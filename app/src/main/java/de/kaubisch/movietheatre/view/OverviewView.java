package de.kaubisch.movietheatre.view;

import java.util.List;

import de.kaubisch.movietheatre.Movie;

/**
 * Created by kaubisch on 09.12.15.
 */
public interface OverviewView {
    void updateData(List<Movie> movies);
    void showProgress();
    void hideProgress();
}
