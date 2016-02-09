package de.kaubisch.movietheatre.view;

import de.kaubisch.movietheatre.model.MovieDetail;

/**
 * Created by kaubisch on 10.12.15.
 */
public interface DetailView {
    String DETAIL_ID_KEY = "DetailView.id";

    void updateTitle(final String title);
    void updateImage(final String path);
    void updateReleaseYear(final int releaseYear);
    void updateDuration(final int durationInMinutes);
    void updateDescription(final String description);
    void updateVoting(final double voting);
    void triggerLoadInformation(final int id);
    void showProgress();
    void hideProgress();
    void clearVideoList();
    void addYoutubeVideo(String id, String name);
    void clearReviewList();
    void addReviewEntries(String author, String reviewText);
    void updateFavoriteStatus(boolean checked);
}
