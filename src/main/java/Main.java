import API.ConnectionPool;
import DAO.MovieDAO;
import DAO.MovieDAOImpl;
import Models.Movie;
import java.sql.SQLException;
import java.util.Date;

public class Main {
    public static void main(String[] args) throws SQLException {
        ConnectionPool connectionPool = new ConnectionPool();
        MovieDAO movieDAO = new MovieDAOImpl(connectionPool);

        // Add a movie to the database
        Movie newMovie = new Movie();
        newMovie.setTitle("The Matrix");
        newMovie.setReleaseDate(new Date(946684800000L)); // January 1, 2000
        movieDAO.add(newMovie);

        // Get a movie from the database by ID
        Movie retrievedMovie = movieDAO.getById(1);
        System.out.println(retrievedMovie.getTitle() + " (" + retrievedMovie.getReleaseDate() + ")");
    }
}


