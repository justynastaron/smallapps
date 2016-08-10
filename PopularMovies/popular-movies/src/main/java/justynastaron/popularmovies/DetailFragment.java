package justynastaron.popularmovies;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class DetailFragment extends Fragment {

    private static final String LOG_TAG = DetailActivity.class.getSimpleName();

    private Movie mCurrentMovie;

    public DetailFragment() {
        //Does nothing on purpose.
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState != null && savedInstanceState.containsKey(Movie.EXTRA)) {
            mCurrentMovie = savedInstanceState.getParcelable(Movie.EXTRA);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_detail, container, false);
        Activity activity = getActivity();
        if (activity != null) {
            Intent intent = activity.getIntent();
            if (intent != null && intent.hasExtra(Movie.EXTRA)) {
                mCurrentMovie = intent.getParcelableExtra(Movie.EXTRA);

                TextView title = (TextView) rootView.findViewById(R.id.movie_title);
                title.setText(mCurrentMovie.getTitle());

                TextView releaseDate = (TextView) rootView.findViewById(R.id.movie_release_date);
                releaseDate.setText(mCurrentMovie.getReleaseDate());

                TextView summary = (TextView) rootView.findViewById(R.id.movie_summary);
                summary.setText(mCurrentMovie.getSummary());

                TextView rating = (TextView) rootView.findViewById(R.id.movie_rating);
                rating.setText(String.valueOf(mCurrentMovie.getRating()));

                ImageView imageView = (ImageView) rootView.findViewById(R.id.movie_poster);
                Uri builtUri = Uri.parse(Movie.IMAGE_BASE_URL).buildUpon().appendEncodedPath(mCurrentMovie.getPosterPath())
                        .build();
                Picasso.with(activity).load(builtUri).into(imageView);
            }
        } else {
            Log.e(LOG_TAG, "For some reason activity is not existing anymore.");
        }
        return rootView;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putParcelable(Movie.EXTRA, mCurrentMovie);
        super.onSaveInstanceState(outState);
    }
}
