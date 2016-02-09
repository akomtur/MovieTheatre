package de.kaubisch.movietheatre.presenter;

import android.os.Bundle;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import de.kaubisch.movietheatre.model.ApiMovieOverviewModel;
import de.kaubisch.movietheatre.model.DetailModel;
import de.kaubisch.movietheatre.model.Movie;
import de.kaubisch.movietheatre.model.MoviesModel;
import de.kaubisch.movietheatre.model.favorites.Favorite;
import de.kaubisch.movietheatre.view.MainView;

/**
 * Created by kaubisch on 09.12.15.
 */
public class ApiBasedMainPresenter implements MainPresenter {

    private MainView view;
    private MoviesModel model;

    private List<Movie> movieList;

    public ApiBasedMainPresenter(final FavoriteDbFactory dbFactory, final MainView view) {
        this.view = view;
        this.model = new ApiMovieOverviewModel(dbFactory.create(), this);
    }

    @Override
    public void updateOverviewData(final SORT_CRITERIA sortCriteria) {
        if(sortCriteria != SORT_CRITERIA.FAVORITE) {
            view.showProgress();
            model.loadMovieOverview(sortCriteria == SORT_CRITERIA.RATING);
        } else {
            model.getFavorites(new DetailModel.AfterQueryCallback<Collection<Favorite>>() {
                @Override
                public void onResult(Collection<Favorite> result) {
                    List<Movie> movieList = new ArrayList<Movie>();
                    for (Favorite favorite : result) {
                        Movie movie = new Movie();
                        movie.id = favorite.id;
                        movie.image = favorite.imagePath;
                        movieList.add(movie);
                    }
                    onModelUpdate(movieList);
                }
            });
        }
    }

    @Override
    public void onModelUpdate(List<Movie> movies) {
        if(movies != null) {
            movieList = movies;
            view.hideProgress();
            view.updateData(movies);
        }
    }

    @Override
    public void saveState(Bundle savedInstance) {
        savedInstance.putParcelableArrayList("movies", new ArrayList<Parcelable>(movieList));
    }

    @Override
    public void restoreState(Bundle savedInstance) {
        onModelUpdate(savedInstance.<Movie>getParcelableArrayList("movies"));
    }
}
