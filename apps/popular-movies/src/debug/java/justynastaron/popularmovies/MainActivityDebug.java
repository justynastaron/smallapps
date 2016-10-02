package justynastaron.popularmovies;

public class MainActivityDebug extends MainActivity {

    /*
        Tries to get list of posters.
    */
    public void updatePosters() {
        if (super.isOnline()) {
            String savedSortingOrder = mPreferences.getString(getString(R.string.pref_sorting_key), SortingOrder.POPULAR.getName());
            FetchMoviesTaskDebug postersTask = new FetchMoviesTaskDebug(this, mPostersAdapter);
            postersTask.execute(savedSortingOrder);
        } else {
            pickFragmentToLoad(Trouble.NO_CONNECTIVITY);
        }
    }
}
