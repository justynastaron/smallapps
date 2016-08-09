package justynastaron.popularmovies;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;

import java.util.ArrayList;

public class MoviesGridFragment extends Fragment {

    private final String LOG_TAG = MoviesGridFragment.class.getSimpleName();

    private SharedPreferences preferences;
    private ArrayAdapter<String> mPostersAdapter;

    public MoviesGridFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        preferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
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
        preferences.edit().putString(getString(R.string.pref_sorting_key), sortingOrder).commit();
        updatePosters(sortingOrder);
    }

    @Override
    public void onStart() {
        super.onStart();
        String savedSortingOrder = preferences.getString(getString(R.string.pref_sorting_key), SortingOrder.POPULAR.getName());
        updatePosters(savedSortingOrder);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        mPostersAdapter =
                new ArrayAdapter<>(
                        getActivity(),
                        R.layout.grid_item_poster,
                        R.id.grid_item_poster_textview,
                        new ArrayList<String>());

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

    private void updatePosters(String sortingOrder) {
        try{
            FetchMoviesTask postersTask = new FetchMoviesTask((MainActivity) getActivity(), mPostersAdapter);
            postersTask.execute(sortingOrder);
        }
        catch (ClassCastException e){
            Log.e(LOG_TAG, "Attempt of using not MainActivity.", e);
        }
        catch (NullPointerException e){
            Log.e(LOG_TAG, "Are you associating fragment with context, not activity?", e);
        }
    }

    private enum SortingOrder {
        POPULAR("popular"), RATING("top_rated");

        private String name;

        SortingOrder(String name) {
            this.name = name;
        }

        private String getName() {
            return name;
        }
    }
}
