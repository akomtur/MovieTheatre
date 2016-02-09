package de.kaubisch.movietheatre.view;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import de.kaubisch.movietheatre.R;
import de.kaubisch.movietheatre.model.Movie;

public class MainActivity extends AppCompatActivity {

    private ProgressDialog progress;
    private boolean largeView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        MainView mainView = (MainView) getFragmentManager().findFragmentById(R.id.main_fragment);
        final DetailView detailView = (DetailView) getFragmentManager().findFragmentById(R.id.detail_fragment);
        largeView = findViewById(R.id.detail_fragment) != null;
        if(largeView) {
            mainView.setOnClickListener(new MovieArrayAdapter.ClickExecutor() {
                @Override
                public void execute(Movie movie) {
                    detailView.triggerLoadInformation(movie.id);
                }
            });
            mainView.selectFirstItemWhenLoaded(true);
        } else {
            mainView.selectFirstItemWhenLoaded(false);
        }
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
}
