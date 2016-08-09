package justynastaron.popularmovies;

public class Movie {

    String title;
    String overview;
    double averageVote;
    String releaseDate;
    String thumbnailPath;

    public Movie(String title, String overview, double averageVote, String releaseDate, String thumbnailPath) {
        this.title = title;
        this.overview = overview;
        this.averageVote = averageVote;
        this.releaseDate = releaseDate;
        this.thumbnailPath = thumbnailPath;
    }

    @Override
    public String toString() {
        return title+" "+releaseDate;
    }
}
