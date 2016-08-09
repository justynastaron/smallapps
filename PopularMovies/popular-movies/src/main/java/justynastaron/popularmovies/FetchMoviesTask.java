package justynastaron.popularmovies;

import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class FetchMoviesTask extends AsyncTask<String, Void, Movie[]> {

    private final String LOG_TAG = FetchMoviesTask.class.getSimpleName();

    private MainActivity activity;
    private MovieAdapter mPostersAdapter;

    public FetchMoviesTask(MainActivity activity, MovieAdapter postersAdapter){
        this.activity = activity;
        mPostersAdapter = postersAdapter;
    }

    private Movie[] getPosterDataFromJson(String movieJsonStr)
            throws JSONException {

        final String DB_RESULTS = "results";
        final String DB_TITLE = "original_title";
        final String DB_OVERVIEW = "overview";
        final String DB_VOTE_AVERAGE = "vote_average";
        final String DB_RELEASE_DATE = "release_date";
        final String DB_THUMBNAIL = "poster_path";

        JSONObject moviesJson = new JSONObject(movieJsonStr);
        JSONArray moviesArray = moviesJson.getJSONArray(DB_RESULTS);

        Movie[] moviesResults = new Movie[moviesArray.length()];

        String title;
        String overview;
        double averageVote;
        String releaseDate;
        String thumbnailPath;

        for (int i = 0; i < moviesArray.length(); i++) {

            JSONObject singleMovie = moviesArray.getJSONObject(i);

            title = singleMovie.getString(DB_TITLE);
            overview = singleMovie.getString(DB_OVERVIEW);
            averageVote = singleMovie.getDouble(DB_VOTE_AVERAGE);
            releaseDate = singleMovie.getString(DB_RELEASE_DATE);
            thumbnailPath = singleMovie.getString(DB_THUMBNAIL);

            moviesResults[i] = new Movie(title, overview, averageVote, releaseDate, thumbnailPath);
        }
        return moviesResults;
    }

    @Override
    protected Movie[] doInBackground(String... params) {

        if (params == null || params.length <= 0) {
            return null;
        }

        HttpURLConnection urlConnection = null;
        BufferedReader reader = null;

        String movieJsonStr = null;

        try {
            final String MOVIE_DB_BASE_URL =
                    "http://api.themoviedb.org/3/movie";
            final String FORMAT_PARAM = "api_key";

            Uri builtUri = Uri.parse(MOVIE_DB_BASE_URL).buildUpon().appendEncodedPath(params[0])
                    .appendQueryParameter(FORMAT_PARAM, BuildConfig.MOVIE_DATABASE_API_KEY)
                    .build();

            URL url = new URL(builtUri.toString());

            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            InputStream inputStream = urlConnection.getInputStream();
            StringBuffer buffer = new StringBuffer();
            if (inputStream == null) {
                return null;
            }
            reader = new BufferedReader(new InputStreamReader(inputStream));

            String line;
            while ((line = reader.readLine()) != null) {
                buffer.append(line + "\n");
            }

            if (buffer.length() == 0) {
                return null;
            }
            movieJsonStr = buffer.toString();
        } catch (IOException e) {
            Log.e(LOG_TAG, "Error ", e);
            return null;
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (reader != null) {
                try {
                    reader.close();
                } catch (final IOException e) {
                    Log.e(LOG_TAG, "Error closing stream", e);
                }
            }
        }

        try {
            return getPosterDataFromJson(movieJsonStr);
        } catch (JSONException e) {
            Log.e(LOG_TAG, e.getMessage(), e);
            e.printStackTrace();
        }

        return null;
    }

    @Override
    protected void onPostExecute(Movie[] result) {
        try {
            if (result != null) {
                mPostersAdapter.clear();
                for (Movie movie : result) {
                    mPostersAdapter.add(movie);
                }
            } else {
                activity.pickFragmentToLoad(false);
            }
        }
        catch (NullPointerException e){
            Log.e(LOG_TAG, "Adapter or activity is not existing anymore.", e);
        }
    }
}