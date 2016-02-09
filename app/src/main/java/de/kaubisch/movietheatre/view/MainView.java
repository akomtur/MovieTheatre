package de.kaubisch.movietheatre.view;

import android.content.DialogInterface;

import java.util.List;

import de.kaubisch.movietheatre.model.Movie;

/**
 * Created by kaubisch on 09.12.15.
 */
public interface MainView {
    void selectFirstItemWhenLoaded(boolean select);
    void updateData(List<Movie> movies);
    void showProgress();
    void hideProgress();
    void setOnClickListener(MovieArrayAdapter.ClickExecutor executor);
}
