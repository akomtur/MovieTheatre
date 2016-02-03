package de.kaubisch.movietheatre.presenter;

import java.util.List;

import de.kaubisch.movietheatre.model.ApiMovieOverviewModel;
import de.kaubisch.movietheatre.model.Movie;
import de.kaubisch.movietheatre.model.MoviesModel;
import de.kaubisch.movietheatre.view.OverviewView;

/**
 * Created by kaubisch on 09.12.15.
 */
public class ApiBasedMovieOverviewPresenter implements MovieOverviewPresenter {

    private OverviewView view;
    private MoviesModel model;


    public ApiBasedMovieOverviewPresenter(OverviewView view) {
        this.view = view;
        this.model = new ApiMovieOverviewModel(this);
    }

    @Override
    public void updateOverviewData(final boolean sortByRating) {
        view.showProgress();
        model.loadMovieOverview(sortByRating);
    }

    @Override
    public void onModelUpdate(List<Movie> movies) {
        view.hideProgress();
        view.updateData(movies);
    }
}
