package Dao;

import Model.Movie;
import java.sql.SQLException;
import java.util.List;

public interface MovieDAO {
    Movie getById(int id) throws SQLException;
    List<Movie> getAll() throws SQLException;
    void add(Movie movie) throws SQLException;
    void update(Movie movie) throws SQLException;
    void delete(Movie movie) throws SQLException;
}