package de.kaubisch.movietheatre.presenter;

import android.os.Bundle;

import java.util.Collection;

import de.kaubisch.movietheatre.model.MovieDetail;
import de.kaubisch.movietheatre.model.Review;
import de.kaubisch.movietheatre.model.Trailer;

/**
 * Created by kaubisch on 10.12.15.
 */
public interface MovieDetailPresenter {
    void loadMovieDetail(final int id);
    void onDetailLoaded(final MovieDetail detail);
    void onVideoLoaded(Collection<Trailer> videoCollection);
    void onReviewsLoaded(Collection<Review> result);
    void saveState(Bundle savedInstance);
    void restoreState(Bundle savedInstance);
    void onChangeFavorite();
}
