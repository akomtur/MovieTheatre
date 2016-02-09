package de.kaubisch.movietheatre.presenter;

import de.kaubisch.movietheatre.model.favorites.FavoritesDbHelper;

/**
 * Created by kaubisch on 08.02.16.
 */
public interface FavoriteDbFactory {
    FavoritesDbHelper create();
}
