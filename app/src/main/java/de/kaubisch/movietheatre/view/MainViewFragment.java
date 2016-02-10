package de.kaubisch.movietheatre.view;

import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.GridView;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import de.kaubisch.movietheatre.R;
import de.kaubisch.movietheatre.model.Movie;
import de.kaubisch.movietheatre.model.favorites.FavoritesDbHelper;
import de.kaubisch.movietheatre.presenter.ApiBasedMainPresenter;
import de.kaubisch.movietheatre.presenter.FavoriteDbFactory;
import de.kaubisch.movietheatre.presenter.MainPresenter;

/**
 * Created by kaubisch on 03.02.16.
 */
public class MainViewFragment extends Fragment implements MainView {

    private MovieArrayAdapter movieAdapter;
    private MainPresenter presenter;
    private ProgressDialog progress;

    private boolean selectFirstItem;

    @Bind(R.id.movieOverview) GridView gridView;
    private SharedPreferences.OnSharedPreferenceChangeListener changeListener;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.main_fragment, container, false);
        ButterKnife.bind(this, view);
        movieAdapter = new MovieArrayAdapter(getActivity());
        gridView.setAdapter(movieAdapter);

        presenter = new ApiBasedMainPresenter(new FavoriteDbFactory() {
            @Override
            public FavoritesDbHelper create() {
                return new FavoritesDbHelper(getActivity());
            }
        }, this);


        changeListener = new SharedPreferences.OnSharedPreferenceChangeListener() {
            @Override
            public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
                if ("sortOrder".equals(key)) {
                    update();
                }
            }
        };

        if(savedInstanceState != null) {
            presenter.restoreState(savedInstanceState);
        } else {
            update();
        }

        PreferenceManager.getDefaultSharedPreferences(getActivity()).registerOnSharedPreferenceChangeListener(changeListener);
        return view;
    }

    @Override
    public void selectFirstItemWhenLoaded(final boolean select) {
        selectFirstItem = select;
    }

    @Override
    public void setOnClickListener(MovieArrayAdapter.ClickExecutor executor) {
        movieAdapter.setExecutor(executor);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        presenter.saveState(outState);
        outState.putString("sortSetting", getSortSetting());
        super.onSaveInstanceState(outState);
    }

    private void update() {
        presenter.updateOverviewData(getSorting(getSortSetting()));
    }

    @NonNull
    private String getSortSetting() {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
        return preferences.getString("sortOrder", "POPULARITY");
    }

    private MainPresenter.SORT_CRITERIA getSorting(final String sortOrder) {
        switch(sortOrder) {
            default:
            case "POPULARITY":
                return MainPresenter.SORT_CRITERIA.POPULARITY;
            case "FAVORITE":
                return MainPresenter.SORT_CRITERIA.FAVORITE;
            case "RATING":
                return MainPresenter.SORT_CRITERIA.RATING;
        }
    }
    @Override
    public void updateData(final List<Movie> movies) {
        movieAdapter.clear();
        movieAdapter.addAll(movies);
        if(selectFirstItem && !movies.isEmpty()) {
            movieAdapter.getExecutor().execute(movies.get(0));
        }
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
}
