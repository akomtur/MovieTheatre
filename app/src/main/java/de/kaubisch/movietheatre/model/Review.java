package de.kaubisch.movietheatre.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by kaubisch on 06.02.16.
 */
public class Review implements Parcelable {
    public String author;
    public String reviewText;

    public static final Creator<Review> CREATOR = new Creator<Review>() {
        @Override
        public Review createFromParcel(Parcel in) {
            return new Review(in);
        }

        @Override
        public Review[] newArray(int size) {
            return new Review[size];
        }
    };

    public Review(String author, String reviewText) {
        this.reviewText = reviewText;
        this.author = author;
    }

    protected Review(Parcel in) {
        author = in.readString();
        reviewText = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(author);
        dest.writeString(reviewText);
    }
}
