package de.kaubisch.movietheatre.model;

import java.util.Collection;

import de.kaubisch.movietheatre.model.favorites.Favorite;

/**
 * Created by kaubisch on 09.12.15.
 */
public interface MoviesModel {
    void loadMovieOverview(final boolean sortByRating);
    void getFavorites(DetailModel.AfterQueryCallback<Collection<Favorite>> callback);
}
