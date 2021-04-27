package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.List;
import java.util.Optional;
import java.sql.*;

public interface DAOModel<E> {
    static String url = "jdbc:sqlite:dbTrello.db";

    E getById(int id);
    List<E> getAll();
    void save(E element); // needs to check if the element already exists: if yes: update ; else: insert
    int add(E element);
    void update(E element);
    void delete(E element);
}
