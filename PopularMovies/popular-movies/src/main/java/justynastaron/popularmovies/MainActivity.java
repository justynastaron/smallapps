package justynastaron.popularmovies;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    private final String LOG_TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (savedInstanceState == null) {
            pickFragmentToLoad();
        }
    }

    public void pickFragmentToLoad() {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        if(isOnline()) {
            transaction.replace(R.id.container, new PostersFragment());
        } else {
            transaction.replace(R.id.container, new OfflineFragment());
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

    public void refresh(View view) {
        pickFragmentToLoad();
    }
}
