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
import de.kaubisch.movietheatre.model.MovieDetail;
import de.kaubisch.movietheatre.R;
import de.kaubisch.movietheatre.presenter.ApiMovieDetailPresenter;
import de.kaubisch.movietheatre.presenter.MovieDetailPresenter;

public class DetailActivity extends AppCompatActivity implements DetailView {

    private MovieDetailPresenter presenter;
    private ProgressDialog progress;

    @Bind(R.id.detail_title) TextView title;
    @Bind(R.id.detail_image) ImageView poster;
    @Bind(R.id.detail_release_year) TextView releaseYear;
    @Bind(R.id.detail_duration) TextView duration;
    @Bind(R.id.detail_description) TextView description;
    @Bind(R.id.detail_vote) TextView voting;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        ButterKnife.bind(this);

        presenter = new ApiMovieDetailPresenter(this);
        presenter.loadMovieDetail(getIntent().getIntExtra(DETAIL_ID_KEY, -1));
    }

    @Override
    public void showData(MovieDetail detail) {
        title.setText(detail.title);
        Uri imageUri = Uri.parse("http://image.tmdb.org/t/p/").buildUpon()
                .appendPath(getString(R.string.image_width)).appendPath(detail.imagePath).build();
        Picasso.with(this).load(imageUri).into(poster);

        showReleaseYear(detail.releaseDate);
        duration.setText(String.format("%d%s", detail.durationInMin, getString(R.string.duration_in_min)));
        description.setText(detail.description);
        voting.setText(String.format("%.1f/10", detail.voteAverage));
    }

    private void showReleaseYear(final Date releaseDate) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(releaseDate);
        releaseYear.setText(String.valueOf(cal.get(Calendar.YEAR)));
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
