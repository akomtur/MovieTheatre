package de.kaubisch.movietheatre.presenter;

import android.os.Bundle;

import java.util.List;

import de.kaubisch.movietheatre.model.Movie;

/**
 * Created by kaubisch on 09.12.15.
 */
public interface MainPresenter {
    enum SORT_CRITERIA {
        POPULARITY,
        RATING,
        FAVORITE,
    }

    void updateOverviewData(SORT_CRITERIA sorting);
    void onModelUpdate(List<Movie> movies);
    void saveState(Bundle savedInstance);
    void restoreState(Bundle savedInstance);
}
