package de.kaubisch.movietheatre.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by kaubisch on 09.12.15.
 */
public class Movie implements Parcelable {
    public int id;
    public String name;
    public double rating;
    public String image;

    public static final Creator<Movie> CREATOR = new Creator<Movie>() {
        @Override
        public Movie createFromParcel(Parcel in) {
            return new Movie(in);
        }

        @Override
        public Movie[] newArray(int size) {
            return new Movie[size];
        }
    };

    public Movie() {
        super();
    }

    private Movie(final Parcel in) {
        id = in.readInt();
        name = in.readString();
        rating = in.readDouble();
        image = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(name);
        dest.writeDouble(rating);
        dest.writeString(image);
    }
}
