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

import butterknife.BindView;
import butterknife.ButterKnife;

public class MoviesGridFragment extends Fragment {

    private static final String LOG_TAG = MoviesGridFragment.class.getSimpleName();

    @BindView(R.id.gridview_posters) GridView gridView;

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
        View rootView = inflater.inflate(R.layout.posters_main, container, false);
        ButterKnife.bind(this, rootView);

        try {
            final MainActivity activity = (MainActivity) getActivity();

            gridView.setAdapter(activity.getAdapter());
            gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                    Movie poster = activity.getAdapter().getItem(position);
                    Intent detailActivityStart = new Intent(activity, DetailActivity.class).putExtra(Movie.EXTRA, poster);
                    startActivity(detailActivityStart);
                }
            });
        } catch (NullPointerException | ClassCastException e) {
            Log.e(LOG_TAG, "Activity doesn't exists or is not MainActivity.");
        }

        return rootView;
    }

    private void saveSortingAndUpdate(String sortingOrder) {
        try {
            MainActivity activity = (MainActivity) getActivity();
            activity.saveSortingOrder(sortingOrder);
            activity.updatePosters();
        } catch (NullPointerException | ClassCastException e) {
            Log.e(LOG_TAG, "Activity doesn't exists or is not MainActivity.", e);
        }
    }
}
