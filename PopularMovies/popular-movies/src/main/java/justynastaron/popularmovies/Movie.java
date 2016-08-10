package justynastaron.popularmovies;

import android.os.Parcel;
import android.os.Parcelable;

public class Movie implements Parcelable {

    /*
    Key to Movie in {@link android.os.Bundle}.
     */
    public static final String EXTRA = "SINGLE_MOVIE";

    /*
    Base URL link to poster.
     */
    public static final String IMAGE_BASE_URL = "http://image.tmdb.org/t/p/w185";

    public static final Parcelable.Creator<Movie> CREATOR = new Parcelable.Creator<Movie>() {
        @Override
        public Movie createFromParcel(Parcel parcel) {
            return new Movie(parcel);
        }

        @Override
        public Movie[] newArray(int i) {
            return new Movie[i];
        }

    };

    private String mTitle;
    private String mOverview;
    private double mAverageVote;
    private String mReleaseDate;
    private String mThumbnailPath;

    public Movie(String title, String overview, double averageVote, String releaseDate, String thumbnailPath) {
        mTitle = title;
        mOverview = overview;
        mAverageVote = averageVote;
        mReleaseDate = releaseDate;
        mThumbnailPath = thumbnailPath;
    }

    public Movie(Parcel in) {
        mTitle = in.readString();
        mOverview = in.readString();
        mAverageVote = in.readDouble();
        mReleaseDate = in.readString();
        mThumbnailPath = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(mTitle);
        parcel.writeString(mOverview);
        parcel.writeDouble(mAverageVote);
        parcel.writeString(mReleaseDate);
        parcel.writeString(mThumbnailPath);
    }

    public String getTitle() {
        return mTitle;
    }

    public String getSummary() {
        return mOverview;
    }

    public double getRating() {
        return mAverageVote;
    }

    public String getReleaseDate() {
        return mReleaseDate;
    }

    public String getPosterPath() {
        return mThumbnailPath;
    }
}
