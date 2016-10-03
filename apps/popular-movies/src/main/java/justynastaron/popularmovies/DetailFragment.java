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

import butterknife.BindView;
import butterknife.ButterKnife;

public class DetailFragment extends Fragment {

    private static final String LOG_TAG = DetailActivity.class.getSimpleName();

    @BindView(R.id.movie_title) TextView title;
    @BindView(R.id.movie_release_date) TextView releaseDate;
    @BindView(R.id.movie_summary) TextView summary;
    @BindView(R.id.movie_rating) TextView rating;
    @BindView(R.id.movie_poster) ImageView imageView;

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
        ButterKnife.bind(this, rootView);
        Activity activity = getActivity();
        if (activity != null) {
            Intent intent = activity.getIntent();
            if (intent != null && intent.hasExtra(Movie.EXTRA)) {
                mCurrentMovie = intent.getParcelableExtra(Movie.EXTRA);

                title.setText(mCurrentMovie.getTitle());
                releaseDate.setText(mCurrentMovie.getReleaseDate());
                summary.setText(mCurrentMovie.getSummary());
                rating.setText(String.valueOf(mCurrentMovie.getRating()));

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
