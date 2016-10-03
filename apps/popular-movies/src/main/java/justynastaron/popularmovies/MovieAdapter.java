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

import butterknife.BindView;
import butterknife.ButterKnife;

/*
    Custom adapter for movie posters.
 */
public class MovieAdapter extends ArrayAdapter<Movie> {

    public MovieAdapter(Activity context, List<Movie> movies) {
        super(context, 0, movies);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Movie movie = getItem(position);

        ViewHolder holder;
        if (convertView != null) {
            holder = (ViewHolder) convertView.getTag();
        } else {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.grid_item_poster, parent, false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        }

        Uri builtUri = Uri.parse(Movie.IMAGE_BASE_URL).buildUpon().appendEncodedPath(movie.getPosterPath())
                .build();
        Picasso.with(getContext()).load(builtUri).into(holder.imageView);

        return convertView;
    }

    static class ViewHolder {
        @BindView(R.id.grid_item_poster_image_view) ImageView imageView;

        public ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
