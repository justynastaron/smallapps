package justynastaron.popularmovies;

import android.app.Activity;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class MovieAdapter extends ArrayAdapter<Movie> {
    private static final String LOG_TAG = MovieAdapter.class.getSimpleName();
    private final String IMAGE_BASE_URL = "http://image.tmdb.org/t/p/w185";

    public MovieAdapter(Activity context, List<Movie> movies) {
        super(context, 0, movies);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Movie movie = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.grid_item_poster, parent, false);
        }

        ImageView imageView = (ImageView) convertView.findViewById(R.id.grid_item_poster_image_view);
        Uri builtUri = Uri.parse(IMAGE_BASE_URL).buildUpon().appendEncodedPath(movie.thumbnailPath)
                .build();
        Picasso.with(getContext()).load(builtUri).into(imageView);

        return convertView;
    }
}
