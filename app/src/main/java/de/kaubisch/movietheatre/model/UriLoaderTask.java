package de.kaubisch.movietheatre.model;

import android.net.Uri;
import android.os.AsyncTask;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by kaubisch on 10.12.15.
 */
public class UriLoaderTask<Result> extends AsyncTask<Uri, Void, Result> {

    public interface UriLoaderListener<T> {
        public void onDataLoaded(T result);
    }

    public interface JsonConverter<T> {
        public T convertJson(final String input);
    }

    private final UriLoaderListener<Result> listener;
    private final JsonConverter<Result> converter;

    public UriLoaderTask(JsonConverter<Result> converter) {
        this(converter, null);
    }

    public UriLoaderTask(JsonConverter<Result> converter, UriLoaderListener<Result> listener) {
        this.listener = listener;
        this.converter = converter;
    }

    @Override
    protected Result doInBackground(Uri... params) {
        String jsonFromUri = readJsonFromUri(params[0]);

        return converter.convertJson(jsonFromUri);
    }

    @Override
    protected void onPostExecute(Result result) {
        if(listener != null) {
            listener.onDataLoaded(result);
        }
    }

    private String readJsonFromUri(final Uri uri) {
        try {
            HttpURLConnection connection = (HttpURLConnection) new URL(uri.toString()).openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("Accept", "application/json");
            connection.connect();

            // Read the input stream into a String
            InputStream inputStream = connection.getInputStream();
            StringBuffer buffer = new StringBuffer();
            if (inputStream == null) {
                // Nothing to do.
                return null;
            }
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));

            String line;
            while ((line = reader.readLine()) != null) {
                // Since it's JSON, adding a newline isn't necessary (it won't affect parsing)
                // But it does make debugging a *lot* easier if you print out the completed
                // buffer for debugging.
                buffer.append(line + "\n");
            }

            if (buffer.length() == 0) {
                // Stream was empty.  No point in parsing.
                return null;
            }
            return buffer.toString();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }
}
