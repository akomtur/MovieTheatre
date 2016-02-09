package de.kaubisch.movietheatre.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by kaubisch on 06.02.16.
 */
public class Trailer implements Parcelable {
    public String type;
    public String site;
    public String id;
    public String name;

    public static final Creator<Trailer> CREATOR = new Creator<Trailer>() {
        @Override
        public Trailer createFromParcel(Parcel in) {
            return new Trailer(in);
        }

        @Override
        public Trailer[] newArray(int size) {
            return new Trailer[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    public Trailer() {
        super();
    }

    public Trailer(final Parcel in) {
        id = in.readString();
        type = in.readString();
        site = in.readString();
        name = in.readString();
    }


    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(type);
        dest.writeString(site);
        dest.writeString(name);
    }
}
