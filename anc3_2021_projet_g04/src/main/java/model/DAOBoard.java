package model;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class DAOBoard implements DAOModel<Board> { // enter the element type !

    @Override
    public Board getById(int idBoard) {
        try {
            String sql = "SELECT * FROM board WHERE id = ? ;";
            Connection conn = DriverManager.getConnection(url);
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setInt(1, idBoard);
            ResultSet result = preparedStatement.executeQuery();
            if (result.next()) {
                int id = result.getInt("id");
                String title = result.getString("name");
                Board board = new Board(id, title);
                return board;
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
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
