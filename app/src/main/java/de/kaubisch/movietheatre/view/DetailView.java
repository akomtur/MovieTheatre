package de.kaubisch.movietheatre.view;

import de.kaubisch.movietheatre.MovieDetail;

/**
 * Created by kaubisch on 10.12.15.
 */
public interface DetailView {
    String DETAIL_ID_KEY = "DetailView.id";

    void showData(MovieDetail detail);
    void showProgress();
    void hideProgress();
}
