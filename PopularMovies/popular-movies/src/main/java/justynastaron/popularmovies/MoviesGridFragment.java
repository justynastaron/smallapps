package justynastaron.popularmovies;

import android.content.Intent;
import android.os.Bundle;
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

public class MoviesGridFragment extends Fragment {

    private static final String LOG_TAG = MoviesGridFragment.class.getSimpleName();

    public MoviesGridFragment() {
        //Does nothing on purpose.
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        final MainActivity activity = (MainActivity) getActivity();
        View rootView = inflater.inflate(R.layout.posters_main, container, false);

        if (activity != null) {

            GridView gridView = (GridView) rootView.findViewById(R.id.gridview_posters);

            gridView.setAdapter(activity.getAdapter());
            gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                    Movie poster = activity.getAdapter().getItem(position);
                    Intent detailActivityStart = new Intent(activity, DetailActivity.class).putExtra(Movie.EXTRA, poster);
                    startActivity(detailActivityStart);
                }
            });
        } else {
            Log.e(LOG_TAG, "Activity is not existing anymore?");
        }

        return rootView;
    }

    private void saveSortingAndUpdate(String sortingOrder) {
        MainActivity activity = (MainActivity) getActivity();
        if (activity != null) {
            activity.saveSortingOrder(sortingOrder);
            activity.updatePosters();
        } else {
            Log.e(LOG_TAG, "No activity.");
        }
    }
}
