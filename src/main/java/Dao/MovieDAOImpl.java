package Dao;

import Connection.ConnectionPool;
import Model.Movie;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MovieDAOImpl implements MovieDAO {
    private ConnectionPool connectionPool;

    public MovieDAOImpl(ConnectionPool connectionPool) {
        this.connectionPool = connectionPool;
    }

    @Override
    public Movie getById(int id) throws SQLException {
        try (Connection connection = connectionPool.getConnection();
             PreparedStatement statement = connection.prepareStatement("SELECT * FROM movies WHERE id = ?")) {
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                Movie movie = new Movie();
                movie.setId(resultSet.getInt("id"));
                movie.setTitle(resultSet.getString("title"));
                movie.setReleaseDate(resultSet.getDate("release_date"));
                return movie;
            } else {
                return null;
            }
        }
    }

    @Override
    public List<Movie> getAll() throws SQLException {
        try (Connection connection = connectionPool.getConnection();
             PreparedStatement statement = connection.prepareStatement("SELECT * FROM movies")) {
            ResultSet resultSet = statement.executeQuery();
            List<Movie> movies = new ArrayList<>();

            while (resultSet.next()) {
                Movie movie = new Movie();
                movie.setId(resultSet.getInt("id"));
                movie.setTitle(resultSet.getString("title"));
                movie.setReleaseDate(resultSet.getDate("release_date"));
                movies.add(movie);
            }

            return movies;
        }
    }

    @Override
    public void add(Movie movie) throws SQLException {
        try (Connection connection = connectionPool.getConnection();
             PreparedStatement statement = connection.prepareStatement("INSERT INTO movies (title, release_date) VALUES (?, ?)")) {
            statement.setString(1, movie.getTitle());
            statement.setDate(2, new java.sql.Date(movie.getReleaseDate().getTime()));
            statement.executeUpdate();
        }
    }

    @Override
    public void update(Movie movie) throws SQLException {
        try (Connection connection = connectionPool.getConnection();
             PreparedStatement statement = connection.prepareStatement("UPDATE movies SET title = ?, release_date = ? WHERE id = ?")) {
            statement.setString(1, movie.getTitle());
            statement.setDate(2, new java.sql.Date(movie.getReleaseDate().getTime()));
            statement.setInt(3, movie.getId());
            statement.executeUpdate();
        }
    }

    @Override
    public void delete(Movie movie) throws SQLException {
        try (Connection connection = connectionPool.getConnection();
             PreparedStatement statement = connection.prepareStatement("DELETE FROM movies WHERE id = ?")) {
            statement.setInt(1, movie.getId());
            statement.executeUpdate();
        }
    }
}
