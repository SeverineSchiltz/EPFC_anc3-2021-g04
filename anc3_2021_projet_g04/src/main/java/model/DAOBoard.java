package model;

import java.util.List;
import java.util.Optional;

public class DAOBoard implements DAOModel<Board> { // enter the element type !

    @Override
    public Optional<Board> get(int id) {
        return Optional.empty();
    }

    @Override
    public List<Board> getAll() {
        return null;
    }

    @Override
    public void save(Board element) {

    }

    @Override
    public void delete(Board element) {

    }
}
