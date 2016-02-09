package de.kaubisch.movietheatre.view;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import de.kaubisch.movietheatre.R;
import de.kaubisch.movietheatre.model.Movie;

/**
 * Created by kaubisch on 03.02.16.
 */
public class MovieArrayAdapter extends ArrayAdapter<Movie> {
    public static interface ClickExecutor {
        void execute(final Movie movie);
    }

    private Activity activity;
    private ClickExecutor executor;

    public MovieArrayAdapter(final Activity activity) {
        this(activity, new ClickExecutor() {
            @Override
            public void execute(Movie movie) {
                Intent intent = new Intent(activity, DetailActivity.class);
                intent.putExtra(DetailView.DETAIL_ID_KEY, movie.id);
                activity.startActivity(intent);
            }
        });
    }

    public MovieArrayAdapter(final Activity activity, final ClickExecutor executor) {
        super(activity, R.layout.item_movie, 0);
        this.activity = activity;
        this.executor = executor;
    }

    public void setExecutor(ClickExecutor executor) {
        this.executor = executor;
    }

    public ClickExecutor getExecutor() {
        return executor;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;
        if (v == null) {
            LayoutInflater inflater = LayoutInflater.from(getContext());
            v = inflater.inflate(R.layout.item_movie, null);
        }
        final Movie movie = getItem(position);
        ImageView movieImage = (ImageView) v.findViewById(R.id.movieImage);
        Uri imageUri = Uri.parse("http://image.tmdb.org/t/p/").buildUpon()
                .appendPath(activity.getString(R.string.image_width)).appendPath(movie.image).build();
        Picasso.with(getContext()).load(imageUri).into(movieImage);
        movieImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(executor != null) {
                    executor.execute(movie);
                }
            }
        });
        return v;
    }
}
