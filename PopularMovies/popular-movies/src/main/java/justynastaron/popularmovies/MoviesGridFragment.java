package justynastaron.popularmovies;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import java.util.ArrayList;

public class MoviesGridFragment extends Fragment {

    private static final String LOG_TAG = MoviesGridFragment.class.getSimpleName();

    private SharedPreferences mPreferences;
    private MovieAdapter mPostersAdapter;

    public MoviesGridFragment() {
        //Does nothing on purpose.
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Activity activity = getActivity();
        if (activity != null) {
            mPreferences = PreferenceManager.getDefaultSharedPreferences(activity);
        } else {
            Log.e(LOG_TAG, "Activity is not existing anymore?");
        }
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.main, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.menuSortNewest) {
            saveSortingAndUpdate(SortingOrder.POPULAR.getName());
        } else if (id == R.id.menuSortRating) {
            saveSortingAndUpdate(SortingOrder.RATING.getName());
        }

        return super.onOptionsItemSelected(item);
    }

    private void saveSortingAndUpdate(String sortingOrder) {
        if (mPreferences != null) {
            mPreferences.edit().putString(getString(R.string.pref_sorting_key), sortingOrder).commit();
            updatePosters(sortingOrder);
        } else {
            Log.e(LOG_TAG, "No activity means no preferences.");
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        if (mPreferences != null) {
            String savedSortingOrder = mPreferences.getString(getString(R.string.pref_sorting_key), SortingOrder.POPULAR.getName());
            updatePosters(savedSortingOrder);
        } else {
            Log.e(LOG_TAG, "No activity means no preferences.");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        final Activity activity = getActivity();
        View rootView = inflater.inflate(R.layout.posters_main, container, false);

        if (activity != null) {
            mPostersAdapter =
                    new MovieAdapter(activity, new ArrayList<Movie>());

            GridView gridView = (GridView) rootView.findViewById(R.id.gridview_posters);
            gridView.setAdapter(mPostersAdapter);
            gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                    Movie poster = mPostersAdapter.getItem(position);
                    Intent detailActivityStart = new Intent(activity, DetailActivity.class).putExtra(Movie.EXTRA, poster);
                    startActivity(detailActivityStart);
                }
            });
        } else {
            Log.e(LOG_TAG, "Activity is not existing anymore?");
        }

        return rootView;
    }

    private void updatePosters(String sortingOrder) {
        try {
            FetchMoviesTask postersTask = new FetchMoviesTask((MainActivity) getActivity(), mPostersAdapter);
            postersTask.execute(sortingOrder);
        } catch (ClassCastException e) {
            Log.e(LOG_TAG, "Attempt of using not MainActivity.", e);
        } catch (NullPointerException e) {
            Log.e(LOG_TAG, "Are you associating fragment with context, not activity?", e);
        }
    }

    private enum SortingOrder {
        POPULAR("popular"), RATING("top_rated");

        private String mName;

        SortingOrder(String name) {
            mName = name;
        }

        private String getName() {
            return mName;
        }
    }
}
