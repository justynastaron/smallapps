package justynastaron.popularmovies;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class TroubleFragment extends Fragment {

    private Trouble mTroubleType;

    public TroubleFragment() {
        //Does nothing on purpose.
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_trouble, container, false);
        TextView troubleText = (TextView) rootView.findViewById(R.id.trouble_message);
        if (mTroubleType == Trouble.NO_CONNECTIVITY) {
            troubleText.setText(getString(R.string.no_internet));
        } else {
            troubleText.setText(getString(R.string.no_results));
        }
        return rootView;
    }

    public void setTroubleType(Trouble troubleType){
        mTroubleType = troubleType;
    }
}
