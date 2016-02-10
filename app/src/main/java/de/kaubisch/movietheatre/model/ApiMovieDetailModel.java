package de.kaubisch.movietheatre.model;

import android.content.ContentValues;
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
import de.kaubisch.movietheatre.presenter.MovieDetailPresenter;

/**
 * Created by kaubisch on 10.12.15.
 */
public class ApiMovieDetailModel implements DetailModel {
    private MovieDetailPresenter presenter;

    private final UriLoaderTask.JsonConverter<MovieDetail> detailConverter = new MovieDetailConverter();
    private final UriLoaderTask.JsonConverter<Collection<Trailer>> videoConverter = new VideoConverter();
    private final UriLoaderTask.JsonConverter<Collection<Review>> reviewConverter = new ReviewConverter();
    private Uri baseUri = Uri.parse("http://api.themoviedb.org/3/movie");

    private FavoritesDbHelper db;

    public ApiMovieDetailModel(final FavoritesDbHelper dbHelper, final MovieDetailPresenter presenter) {
        this.presenter = presenter;
        this.db = dbHelper;
    }

    @Override
    public void loadMovieDetail(int movieId) {
        Uri uri = baseUri.buildUpon()
                .appendPath(String.valueOf(movieId))
                .appendQueryParameter("api_key", BuildConfig.MOVIE_API_KEY)
                .build();

        new UriLoaderTask<>(detailConverter, new UriLoaderTask.UriLoaderListener<MovieDetail>() {
            @Override
            public void onDataLoaded(MovieDetail result) {
                presenter.onDetailLoaded(result);
            }
        }).execute(uri);
    }

    @Override
    public void loadMovieReviews(int movieId) {
        Uri uri = baseUri.buildUpon()
                .appendPath(String.valueOf(movieId))
                .appendPath("reviews")
                .appendQueryParameter("api_key", BuildConfig.MOVIE_API_KEY)
                .build();

        new UriLoaderTask<>(reviewConverter, new UriLoaderTask.UriLoaderListener<Collection<Review>>() {
            @Override
            public void onDataLoaded(Collection<Review> result) {
                presenter.onReviewsLoaded(result);
            }
        }).execute(uri);
    }

    @Override
    public void loadMovieTrailer(int movieId) {
        Uri uri = baseUri.buildUpon()
                .appendPath(String.valueOf(movieId))
                .appendPath("videos")
                .appendQueryParameter("api_key", BuildConfig.MOVIE_API_KEY)
                .build();
        new UriLoaderTask<>(videoConverter, new UriLoaderTask.UriLoaderListener<Collection<Trailer>>() {
            @Override
            public void onDataLoaded(Collection<Trailer> result) {
                presenter.onVideoLoaded(result);
            }
        }).execute(uri);
    }

    @Override
    public void setFavorite(final int movieId, final String imagePath, final AfterQueryCallback<Void> callback) {
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... params) {
                SQLiteDatabase database = db.getWritableDatabase();
                ContentValues values = new ContentValues();
                values.put(FavoriteContract.FavoriteEntry.COLUMN_FAVORITE_ID, movieId);
                values.put(FavoriteContract.FavoriteEntry.COLUMN_IMAGE_PATH, imagePath);
                database.insert(FavoriteContract.FavoriteEntry.TABLE_NAME, null, values);
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                callback.onResult(null);
            }
        }.execute();
    }

    @Override
    public void removeFavorite(final int movieId, final AfterQueryCallback<Void> callback) {
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... params) {
                SQLiteDatabase database = db.getWritableDatabase();
                database.delete(FavoriteContract.FavoriteEntry.TABLE_NAME
                    , FavoriteContract.FavoriteEntry.COLUMN_FAVORITE_ID +" = ? "
                    , new String[] {String.valueOf(movieId)});
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                callback.onResult(null);
            }
        }.execute();
    }

    @Override
    public void isFavorite(int movieId, final AfterQueryCallback<Boolean> callback) {
        new AsyncTask<Integer, Void, Boolean>() {
            @Override
            protected Boolean doInBackground(Integer... params) {
                SQLiteDatabase database = db.getWritableDatabase();
                Cursor query = database.query(FavoriteContract.FavoriteEntry.TABLE_NAME
                        , new String[]{FavoriteContract.FavoriteEntry.COLUMN_FAVORITE_ID}
                        , FavoriteContract.FavoriteEntry.COLUMN_FAVORITE_ID + "= ?"
                        , new String[]{String.valueOf(params[0])}
                        , null, null, null);
                return query.getCount() > 0;
            }

            @Override
            protected void onPostExecute(Boolean result) {
                callback.onResult(result);
            }
        }.execute(movieId);
    }
}
