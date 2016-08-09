package justynastaron.popularmovies;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class OfflineFragment extends Fragment {

    private static final String LOG_TAG = OfflineFragment.class.getSimpleName();

    public OfflineFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_offline, container, false);
        return rootView;
    }
}
