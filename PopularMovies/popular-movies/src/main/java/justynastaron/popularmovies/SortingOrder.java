package justynastaron.popularmovies;

enum SortingOrder {
    POPULAR("popular"), RATING("top_rated");

    private String mName;

    SortingOrder(String name) {
        mName = name;
    }

    public String getName() {
        return mName;
    }
}