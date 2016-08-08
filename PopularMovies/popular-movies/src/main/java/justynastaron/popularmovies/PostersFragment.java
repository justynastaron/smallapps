package justynastaron.popularmovies;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;

import org.json.JSONException;

import java.io.BufferedReader;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.util.ArrayList;

public class PostersFragment extends Fragment {

    private ArrayAdapter<String> mPostersAdapter;

    public PostersFragment() {
    }

    private void updatePosters() {
        FetchPostersTask postersTask = new FetchPostersTask();
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
        //TODO add preferences to request and executing task
    }

    @Override
    public void onStart() {
        super.onStart();
        updatePosters();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        ArrayList<String> tempList = new ArrayList<>();
        tempList.add("Movie 1");
        tempList.add("Movie 2");
        tempList.add("Movie 3");

        mPostersAdapter =
                new ArrayAdapter<>(
                        getActivity(),
                        R.layout.grid_item_poster,
                        R.id.grid_item_poster_textview,
                        tempList);

        View rootView = inflater.inflate(R.layout.posters_main, container, false);

        GridView gridView = (GridView) rootView.findViewById(R.id.gridview_posters);
        gridView.setAdapter(mPostersAdapter);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                String poster = mPostersAdapter.getItem(position);
                Intent detailActivityStart = new Intent(getActivity(), DetailActivity.class).putExtra(Intent.EXTRA_TEXT, poster);
                startActivity(detailActivityStart);
            }
        });

        return rootView;
    }

    public class FetchPostersTask extends AsyncTask<String, Void, String[]> {

        private final String LOG_TAG = FetchPostersTask.class.getSimpleName();

        private String[] getPosterDataFromJson(String movieJsonStr, int numDays)
                throws JSONException {
            //TODO get movie data from JSON
            return null;
        }

        @Override
        protected String[] doInBackground(String... params) {

            HttpURLConnection urlConnection = null;
            BufferedReader reader = null;

            String movieJsonStr = null;

            String format = "json";

            try {
                //TODO download data
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

            //TODO return data from JSON
            return null;
        }

        @Override
        protected void onPostExecute(String[] result) {
            if (result != null) {
                mPostersAdapter.clear();
                for (String movieStr : result) {
                    mPostersAdapter.add(movieStr);
                }
            }
        }
    }
}
