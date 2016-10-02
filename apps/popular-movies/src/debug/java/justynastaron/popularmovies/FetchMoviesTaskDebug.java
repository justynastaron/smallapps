package justynastaron.popularmovies;

import com.facebook.stetho.okhttp3.StethoInterceptor;

import okhttp3.OkHttpClient;

public class FetchMoviesTaskDebug extends FetchMoviesTask {

    public FetchMoviesTaskDebug(MainActivityDebug activity, MovieAdapter postersAdapter) {
        super(activity, postersAdapter);
        if(BuildConfig.DEBUG) {
            super.client = new OkHttpClient.Builder().addNetworkInterceptor(new StethoInterceptor()).build();
        }
    }
}