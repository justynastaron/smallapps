package justynastaron.popularmovies;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (savedInstanceState == null) {
            pickFragmentToLoad(true);
        }
    }

    /**
     * Switches between {@link justynastaron.popularmovies.MoviesGridFragment} and
     * {@link justynastaron.popularmovies.TroubleFragment}.
     *
     * @param resultsAvailable defines appropriate message in {@link justynastaron.popularmovies.TroubleFragment}
     *                         (false interpreted as lack of connectivity). Ignored in
     *                         {@link justynastaron.popularmovies.MoviesGridFragment}.
     */
    public void pickFragmentToLoad(boolean resultsAvailable) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        if (isOnline()) {
            transaction.replace(R.id.container, new MoviesGridFragment());
        } else {
            TroubleFragment troubleFragment = new TroubleFragment();
            troubleFragment.setResultsAvailable(resultsAvailable);
            transaction.replace(R.id.container, troubleFragment);
        }
        transaction.addToBackStack(null);
        transaction.commit();
    }

    private boolean isOnline() {
        ConnectivityManager cm =
                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }

    /**
     * On click "Try again" in trouble fragment.
     */
    public void refresh(View view) {
        pickFragmentToLoad(true);
    }
}
