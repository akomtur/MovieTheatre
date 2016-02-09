package de.kaubisch.movietheatre.model.favorites;

import android.provider.BaseColumns;

/**
 * Created by kaubisch on 07.02.16.
 */
public final class FavoriteContract {

    public FavoriteContract() {}

    public static abstract class FavoriteEntry implements BaseColumns {
        public static final String TABLE_NAME = "favorites";
        public static final String COLUMN_FAVORITE_ID = "id";
        public static final String COLUMN_IMAGE_PATH = "image";
    }

    public static final String SQL_CREATE_ENTRIES = "CREATE TABLE " + FavoriteEntry.TABLE_NAME + "(" +
            FavoriteEntry.COLUMN_FAVORITE_ID + " INTEGER PRIMARY KEY," +
            FavoriteEntry.COLUMN_IMAGE_PATH + " TEXT )";
    public static final String SQL_DELETE_ENTRIES = "DROP TABLE IF EXISTS " + FavoriteEntry.TABLE_NAME;
}
