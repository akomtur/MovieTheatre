package de.kaubisch.movietheatre.presenter;

import android.os.Bundle;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;

import de.kaubisch.movietheatre.model.MovieDetail;
import de.kaubisch.movietheatre.model.ApiMovieDetailModel;
import de.kaubisch.movietheatre.model.DetailModel;
import de.kaubisch.movietheatre.model.Review;
import de.kaubisch.movietheatre.model.Trailer;
import de.kaubisch.movietheatre.model.favorites.FavoritesDbHelper;
import de.kaubisch.movietheatre.view.DetailView;

/**
 * Created by kaubisch on 10.12.15.
 */
public class ApiMovieDetailPresenter implements MovieDetailPresenter {

    private DetailView view;
    private DetailModel model;

    private MovieDetail movieDetail;
    private Collection<Review> reviewCollection;
    private Collection<Trailer> trailerCollection;
    private boolean isFavorite;
    private int id;
    private DetailModel.AfterQueryCallback<Void> changeFavoriteCallback = new DetailModel.AfterQueryCallback<Void>() {
        @Override
        public void onResult(Void result) {
            isFavorite = !isFavorite;
            view.updateFavoriteStatus(isFavorite);
        }
    };

    public ApiMovieDetailPresenter(final FavoriteDbFactory dbFactory, final DetailView view) {
        this.view = view;
        this.model = new ApiMovieDetailModel(dbFactory.create(), this);
    }

    @Override
    public void loadMovieDetail(int id) {
        if(id != -1) {
            this.id = id;
            view.showProgress();
            model.loadMovieDetail(id);
            model.loadMovieTrailer(id);
            model.loadMovieReviews(id);
            model.isFavorite(id, new DetailModel.AfterQueryCallback<Boolean>() {
                @Override
                public void onResult(Boolean result) {
                    isFavorite = result;
                    view.updateFavoriteStatus(result);
                }
            });
        }
    }

    @Override
    public void onDetailLoaded(MovieDetail detail) {
        if(detail != null) {
            movieDetail = detail;
            view.hideProgress();
            view.updateTitle(detail.title);
            view.updateDescription(detail.description);
            view.updateVoting(detail.voteAverage);
            view.updateDuration(detail.durationInMin);
            view.updateReleaseYear(getReleaseYear(detail.releaseDate));
            view.updateImage(detail.imagePath);
        }
    }

    @Override
    public void onVideoLoaded(Collection<Trailer> videoCollection) {
        if(videoCollection != null) {
            trailerCollection = videoCollection;
            view.clearVideoList();
            for (Trailer trailer : videoCollection) {
                if("YouTube".equals(trailer.site) && "Trailer".equals(trailer.type)) {
                    view.addYoutubeVideo(trailer.id, trailer.name);
                }
            }
        }
    }

    @Override
    public void onReviewsLoaded(Collection<Review> reviewCollection) {
        if (reviewCollection != null) {
            this.reviewCollection = reviewCollection;
            view.clearReviewList();
            for (Review review : reviewCollection) {
                view.addReviewEntries(review.author, review.reviewText);
            }
        }
    }

    private int getReleaseYear(final Date releaseDate) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(releaseDate);
        return cal.get(Calendar.YEAR);
    }

    @Override
    public void saveState(Bundle savedInstance) {
        savedInstance.putParcelable("movieDetail", movieDetail);
        savedInstance.putParcelableArrayList("reviews", new ArrayList<Parcelable>(reviewCollection));
        savedInstance.putParcelableArrayList("trailer", new ArrayList<Parcelable>(trailerCollection));
        savedInstance.putBoolean("isFavorite", isFavorite);
    }

    @Override
    public void restoreState(Bundle savedInstance) {
        onReviewsLoaded(savedInstance.<Review>getParcelableArrayList("reviews"));
        onVideoLoaded(savedInstance.<Trailer>getParcelableArrayList("trailer"));
        onDetailLoaded(savedInstance.<MovieDetail>getParcelable("movieDetail"));
        isFavorite = savedInstance.getBoolean("isFavorite");
    }

    @Override
    public void onChangeFavorite() {
        if(isFavorite) {
            model.removeFavorite(id, changeFavoriteCallback);
        } else {
            model.setFavorite(id, movieDetail.imagePath, changeFavoriteCallback);
        }
    }
}
