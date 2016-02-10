package de.kaubisch.movietheatre.model;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.AsyncTask;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import de.kaubisch.movietheatre.BuildConfig;
import de.kaubisch.movietheatre.model.favorites.Favorite;
import de.kaubisch.movietheatre.model.favorites.FavoriteContract;
import de.kaubisch.movietheatre.model.favorites.FavoritesDbHelper;
import de.kaubisch.movietheatre.presenter.MainPresenter;

/**
 * Created by kaubisch on 09.12.15.
 */
public class ApiMovieOverviewModel implements MoviesModel {
    private final FavoritesDbHelper db;
    private final MainPresenter presenter;

    private final UriLoaderTask.JsonConverter<List<Movie>> overviewConverter = new MovieOverviewConverter();

    public ApiMovieOverviewModel(final FavoritesDbHelper db, final MainPresenter presenter) {
        this.db = db;
        this.presenter = presenter;
    }

    @Override
    public void loadMovieOverview(final boolean sortByRating) {
        Uri uri = Uri.parse("http://api.themoviedb.org/3/discover/movie").buildUpon()
                .appendQueryParameter("api_key", BuildConfig.MOVIE_API_KEY)
                .appendQueryParameter("sort_by", sortByRating ? "vote_average.desc" : "popularity.desc")
                .build();

        new UriLoaderTask<>(overviewConverter, new UriLoaderTask.UriLoaderListener<List<Movie>>() {
            @Override
            public void onDataLoaded(List<Movie> result) {
                presenter.onModelUpdate(result);
            }
        }).execute(uri);
    }

    @Override
    public void getFavorites(final DetailModel.AfterQueryCallback<Collection<Favorite>> callback) {
        new AsyncTask<Void, Void, List<Favorite>>() {
            @Override
            protected List<Favorite> doInBackground(Void... params) {
                SQLiteDatabase database = db.getReadableDatabase();
                Cursor query = database.query(FavoriteContract.FavoriteEntry.TABLE_NAME
                        , new String[]{FavoriteContract.FavoriteEntry.COLUMN_FAVORITE_ID, FavoriteContract.FavoriteEntry.COLUMN_IMAGE_PATH}
                        , null
                        , null
                        , null, null, null);

                int idIdx = query.getColumnIndex(FavoriteContract.FavoriteEntry.COLUMN_FAVORITE_ID);
                int pathIdx = query.getColumnIndex(FavoriteContract.FavoriteEntry.COLUMN_IMAGE_PATH);
                List<Favorite> favoriteList = new ArrayList<>();
                if(query.moveToFirst()) {
                    do {
                        favoriteList.add(new Favorite(query.getInt(idIdx), query.getString(pathIdx)));
                    } while(query.moveToNext());
                }
                return favoriteList;
            }

            @Override
            protected void onPostExecute(List<Favorite> favorites) {
                callback.onResult(favorites);
            }
        }.execute();
    }
}
