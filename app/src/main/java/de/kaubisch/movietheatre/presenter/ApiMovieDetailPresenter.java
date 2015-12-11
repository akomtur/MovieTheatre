package de.kaubisch.movietheatre.presenter;

import de.kaubisch.movietheatre.MovieDetail;
import de.kaubisch.movietheatre.model.ApiMovieDetailModel;
import de.kaubisch.movietheatre.model.DetailModel;
import de.kaubisch.movietheatre.view.DetailView;

/**
 * Created by kaubisch on 10.12.15.
 */
public class ApiMovieDetailPresenter implements MovieDetailPresenter {

    private DetailView view;
    private DetailModel model;

    public ApiMovieDetailPresenter(DetailView view) {
        this.view = view;
        this.model = new ApiMovieDetailModel(this);
    }

    @Override
    public void loadMovieDetail(int id) {
        if(id != -1) {
            view.showProgress();
            model.loadMovieDetail(id);
        }
    }

    @Override
    public void onDetailLoaded(MovieDetail detail) {
        view.hideProgress();
        view.showData(detail);
    }
}
