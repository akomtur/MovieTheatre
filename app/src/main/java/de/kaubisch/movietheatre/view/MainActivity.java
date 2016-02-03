package de.kaubisch.movietheatre.view;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.List;

import de.kaubisch.movietheatre.R;
import de.kaubisch.movietheatre.model.Movie;
import de.kaubisch.movietheatre.presenter.ApiBasedMovieOverviewPresenter;
import de.kaubisch.movietheatre.presenter.MovieOverviewPresenter;

public class MainActivity extends AppCompatActivity implements OverviewView {

    private ArrayAdapter<Movie> movieAdapter;
    private MovieOverviewPresenter presenter;

    private ProgressDialog progress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        movieAdapter = new ArrayAdapter<Movie>(this, R.layout.item_movie, 0) {
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                View v = convertView;
                if(v == null) {
                    LayoutInflater inflater = LayoutInflater.from(getContext());
                    v = inflater.inflate(R.layout.item_movie, null);
                }
                final Movie movie = getItem(position);
                ImageView movieImage = (ImageView) v.findViewById(R.id.movieImage);
                Uri imageUri = Uri.parse("http://image.tmdb.org/t/p/").buildUpon()
                        .appendPath(getString(R.string.image_width)).appendPath(movie.image).build();
                Picasso.with(getContext()).load(imageUri).into(movieImage);
                movieImage.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(MainActivity.this, DetailActivity.class);
                        intent.putExtra(DetailView.DETAIL_ID_KEY, movie.id);
                        MainActivity.this.startActivity(intent);
                    }
                });
                return v;
            }

        };

        GridView gridView = (GridView)findViewById(R.id.movieOverview);
        gridView.setAdapter(movieAdapter);

        presenter = new ApiBasedMovieOverviewPresenter(this);

    }

    @Override
    protected void onResume() {
        super.onResume();
        update();
    }

    private void update() {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        String sortOrder = preferences.getString("sortOrder", "popular");
        presenter.updateOverviewData(sortOrder.equals("rating"));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.add("Settings");
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case 0:
                Intent intent = new Intent(this, AppPreference.class);
                startActivity(intent);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void updateData(List<Movie> movies) {
        movieAdapter.clear();
        movieAdapter.addAll(movies);
    }
    @Override
    public void showProgress() {
        if(progress == null) {
            progress = new ProgressDialog(this);
            progress.show();
        }
    }

    @Override
    public void hideProgress() {
        if(progress != null) {
            progress.dismiss();
            progress = null;
        }
    }
}
