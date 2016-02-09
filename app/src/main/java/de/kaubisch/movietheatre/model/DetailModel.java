package de.kaubisch.movietheatre.model;

import java.util.Collection;

import de.kaubisch.movietheatre.model.favorites.Favorite;

/**
 * Created by kaubisch on 10.12.15.
 */
public interface DetailModel {
    interface AfterQueryCallback<T> {
        void onResult(T result);

    }

    void loadMovieDetail(final int movieId);
    void loadMovieReviews(final int movieId);
    void loadMovieTrailer(final int movieId);
    void setFavorite(final int movieId, final String imagePath, AfterQueryCallback<Void> callback);
    void removeFavorite(final int movieId, AfterQueryCallback<Void> callback);
    void isFavorite(final int movieId, final AfterQueryCallback<Boolean> callback);

}
