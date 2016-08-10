package justynastaron.popularmovies;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private SharedPreferences mPreferences;
    private MovieAdapter mPostersAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        mPostersAdapter = new MovieAdapter(this, new ArrayList<Movie>());
    }

    @Override
    protected void onStart() {
        super.onStart();
        updatePosters();
    }

    /**
     * Switches between {@link justynastaron.popularmovies.MoviesGridFragment} and
     * {@link justynastaron.popularmovies.TroubleFragment}.
     */
    public void pickFragmentToLoad(Trouble troubleType) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        if (troubleType == Trouble.NONE) {
            transaction.replace(R.id.container, new MoviesGridFragment());
        } else {
            mPostersAdapter.clear();
            TroubleFragment troubleFragment = new TroubleFragment();
            troubleFragment.setTroubleType(troubleType);
            transaction.replace(R.id.container, troubleFragment);
        }
        transaction.addToBackStack(null);
        transaction.commit();
    }

    /**
     * On click "Try again" in trouble fragment.
     */
    public void refresh(View view) {
        updatePosters();
    }

    /*
        Triggers fragment update depending on receiving results by {@link FetchMoviesTask}.
     */
    public void decideDependingOnResults() {
        if (mPostersAdapter.isEmpty()) {
            pickFragmentToLoad(Trouble.NO_RESULTS);
        } else {
            pickFragmentToLoad(Trouble.NONE);
        }
    }

    private boolean isOnline() {
        ConnectivityManager cm =
                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }

    /*
        Tries to get list of posters.
     */
    public void updatePosters() {
        if (isOnline()) {
            String savedSortingOrder = mPreferences.getString(getString(R.string.pref_sorting_key), SortingOrder.POPULAR.getName());
            FetchMoviesTask postersTask = new FetchMoviesTask(this, mPostersAdapter);
            postersTask.execute(savedSortingOrder);
        } else {
            pickFragmentToLoad(Trouble.NO_CONNECTIVITY);
        }
    }

    public void saveSortingOrder(String sortingOrder) {
        mPreferences.edit().putString(getString(R.string.pref_sorting_key), sortingOrder).commit();
    }

    public MovieAdapter getAdapter() {
        return mPostersAdapter;
    }
}
