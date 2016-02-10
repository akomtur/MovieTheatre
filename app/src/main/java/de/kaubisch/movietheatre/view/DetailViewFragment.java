package de.kaubisch.movietheatre.view;

import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.Collection;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import de.kaubisch.movietheatre.R;
import de.kaubisch.movietheatre.model.Review;
import de.kaubisch.movietheatre.model.favorites.FavoritesDbHelper;
import de.kaubisch.movietheatre.presenter.ApiMovieDetailPresenter;
import de.kaubisch.movietheatre.presenter.FavoriteDbFactory;
import de.kaubisch.movietheatre.presenter.MovieDetailPresenter;

/**
 * Created by kaubisch on 03.02.16.
 */
public class DetailViewFragment extends Fragment implements DetailView {
    @Bind(R.id.detail_title) TextView title;
    @Bind(R.id.detail_image) ImageView poster;
    @Bind(R.id.detail_release_year) TextView releaseYear;
    @Bind(R.id.detail_duration) TextView duration;
    @Bind(R.id.detail_description) TextView description;
    @Bind(R.id.detail_vote) TextView voting;
    @Bind(R.id.detail_video_list) LinearLayout videoList;
    @Bind(R.id.detail_review_list) LinearLayout reviewList;
    @Bind(R.id.favorite_button) ImageView favoriteButton;

    private MovieDetailPresenter presenter;
    private ProgressDialog progress;
    private ArrayAdapter<Review> reviewAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.detail_fragment, container, false);
        ButterKnife.bind(this, view);

        presenter = new ApiMovieDetailPresenter(new FavoriteDbFactory() {
            @Override
            public FavoritesDbHelper create() {
                return new FavoritesDbHelper(getActivity());
            }
        }, this);

        favoriteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.onChangeFavorite();
            }
        });

        if(savedInstanceState != null) {
            presenter.restoreState(savedInstanceState);
        } else {
            triggerLoadInformation(getMovieKey());
        }

        return view;
    }

    private int getMovieKey() {
        int id = -1;
        if(getArguments() != null) {
            id = getArguments().getInt(DETAIL_ID_KEY, -1);
        }
        if(id == -1) {
            id = getActivity().getIntent().getIntExtra(DETAIL_ID_KEY, -1);
        }
        return id;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        presenter.saveState(outState);
        super.onSaveInstanceState(outState);
    }

    private void playVideo(final String id) {
        Intent videoIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("vnd.youtube:" + id));
        List<ResolveInfo> resolveInfos = getActivity().getPackageManager().queryIntentActivities(videoIntent, PackageManager.MATCH_DEFAULT_ONLY);
        if(resolveInfos.isEmpty()) {
            videoIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://youtube.com/watch?v=" + id));
        }
        startActivity(videoIntent);

    }

    @Override
    public void triggerLoadInformation(int id) {
        if(id != -1) {
            presenter.loadMovieDetail(id);
        }
    }

    @Override
    public void updateDuration(int durationInMinutes) {
        duration.setText(String.format("%d%s", durationInMinutes, getString(R.string.duration_in_min)));
    }

    @Override
    public void updateDescription(String description) {
        this.description.setText(description);
    }

    @Override
    public void updateVoting(double voting) {
        this.voting.setText(String.format("%.1f/10", voting));
    }

    @Override
    public void updateReleaseYear(int releaseYear) {
        this.releaseYear.setText(String.valueOf(releaseYear));
    }

    @Override
    public void updateImage(String path) {
        Uri imageUri = Uri
                .parse(getString(R.string.image_url))
                .buildUpon()
                .appendPath(getString(R.string.image_width))
                .appendPath(path)
                .build();
        Picasso.with(getActivity()).load(imageUri).into(poster);
    }

    @Override
    public void updateTitle(String title) {
        this.title.setText(title);
    }

    @Override
    public void showProgress() {
        if(progress == null) {
            progress = new ProgressDialog(getActivity());
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

    @Override
    public void clearVideoList() {
        videoList.removeAllViewsInLayout();
    }

    @Override
    public void addYoutubeVideo(final String id, String name) {
        videoList.addView(createVideoItem(id, name));
    }

    @NonNull
    private View createVideoItem(final String id, String name) {
        View item = LayoutInflater.from(getActivity()).inflate(R.layout.item_detail_trailer, null);
        TextView tv = (TextView) item.findViewById(R.id.video_title);
        tv.setText(name);
        item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                playVideo(id);
            }
        });
        return item;
    }

    @Override
    public void clearReviewList() {
        reviewList.removeAllViewsInLayout();
    }

    @Override
    public void addReviewEntries(String author, String reviewText) {
        View inflate = LayoutInflater.from(getActivity()).inflate(R.layout.item_detail_review, null);
        ReviewViewHolder viewHolder = new ReviewViewHolder(inflate);
        viewHolder.author.setText(author);
        viewHolder.content.setText(reviewText);
        reviewList.addView(inflate);

    }

    @Override
    public void updateFavoriteStatus(boolean checked) {
        final int drawableId = checked ? R.drawable.btn_star_big_on : R.drawable.btn_star_big_off;
        favoriteButton.setImageResource(drawableId);
    }
}
