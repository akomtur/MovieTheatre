package de.kaubisch.movietheatre.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;

/**
 * Created by kaubisch on 10.12.15.
 */
public class MovieDetail implements Parcelable {
    public String title;
    public Date releaseDate;
    public double voteAverage;
    public String description;
    public String imagePath;
    public int durationInMin;

    public static final Creator<MovieDetail> CREATOR = new Creator<MovieDetail>() {
        @Override
        public MovieDetail createFromParcel(Parcel in) {
            return new MovieDetail(in);
        }

        @Override
        public MovieDetail[] newArray(int size) {
            return new MovieDetail[size];
        }
    };

    public MovieDetail() {
        super();
    }

    private MovieDetail(final Parcel in) {
        title = in.readString();
        releaseDate = new Date(in.readLong());
        voteAverage = in.readDouble();
        description = in.readString();
        imagePath = in.readString();
        durationInMin = in.readInt();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(title);
        dest.writeLong(releaseDate.getTime());
        dest.writeDouble(voteAverage);
        dest.writeString(description);
        dest.writeString(imagePath);
        dest.writeInt(durationInMin);
    }
}
