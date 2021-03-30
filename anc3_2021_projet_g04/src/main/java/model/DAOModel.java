package model;

import java.util.List;
import java.util.Optional;

public interface DAOModel<E> {
    Optional<E> get(int id);
    List<E> getAll();
    void save(E element); // needs to check if the element already exists: if yes: update ; else: insert
//    void add(E element);
//    void update(E element);
    void delete(E element);
}
