package de.kaubisch.movietheatre.view;

import android.app.ProgressDialog;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.Calendar;
import java.util.Date;

import butterknife.Bind;
import butterknife.ButterKnife;
import de.kaubisch.movietheatre.model.Movie;
import de.kaubisch.movietheatre.model.MovieDetail;
import de.kaubisch.movietheatre.R;
import de.kaubisch.movietheatre.presenter.ApiMovieDetailPresenter;
import de.kaubisch.movietheatre.presenter.MovieDetailPresenter;

public class DetailActivity extends AppCompatActivity {

    private boolean largeView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        MainView mainView = (MainView) getFragmentManager().findFragmentById(R.id.main_fragment);
        final DetailView detailView = (DetailView) getFragmentManager().findFragmentById(R.id.detail_fragment);
        largeView = findViewById(R.id.main_fragment) != null;
        if(largeView) {
            mainView.setOnClickListener(new MovieArrayAdapter.ClickExecutor() {
                @Override
                public void execute(Movie movie) {
                    detailView.triggerLoadInformation(movie.id);
                }
            });
        }
    }

}
