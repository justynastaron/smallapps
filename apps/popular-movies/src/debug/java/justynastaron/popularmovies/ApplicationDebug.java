package justynastaron.popularmovies;

import android.app.Application;

import com.facebook.stetho.Stetho;

public class ApplicationDebug extends Application {
    public void onCreate() {
        super.onCreate();
        Stetho.initializeWithDefaults(this);
    }
}
