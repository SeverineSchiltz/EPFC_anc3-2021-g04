package model;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class DAOColumn implements DAOModel<Column> {


    public static List<Column> getAllByBoard(Board board) {
        try {
            String sql = "SELECT * FROM column WHERE idBoard = ? ORDER BY position;";
            Connection conn = DriverManager.getConnection(url);
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setInt(1,board.getId());
            ResultSet result = preparedStatement.executeQuery();
            List<Column> lc = new ArrayList<>();
            while (result.next()) {
                int id = result.getInt("id");
                String title = result.getString("name");
                Column c = new Column(id, title, board);
                lc.add(c);
            }
            return lc;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return null;
        }
    }

    @Override
    public Column getById(int id) {
        return null;
    }

    @Override
    public List<Column> getAll() {
        return null;
    }

    @Override
    public void save(Column element) {

    }

    @Override
    public void add(Column element) {
//        try {
//            String sql = "INSERT INTO column(name, position, idBoard) VALUES(?,?,?);";
//            Connection conn = DriverManager.getConnection(url);
//            PreparedStatement preparedStatement = conn.prepareStatement(sql);
//            preparedStatement.setInt(1, column.getPosition());
//            preparedStatement.setInt(2, column.getBoard().getId());
//            preparedStatement.setInt(3, column.getId());
//            preparedStatement.execute();
//        } catch (SQLException throwables) {
//            throwables.printStackTrace();
//        }
    }

    @Override
    public void update(Column column) {
        try {
            String sql = "UPDATE column SET name  = ?, position=?, idBoard= ? WHERE id = ?;";
            Connection conn = DriverManager.getConnection(url);
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1, column.getTitle().getValue());
            preparedStatement.setInt(2, column.getPosition());
            preparedStatement.setInt(3, column.getBoard().getId());
            preparedStatement.setInt(4, column.getId());
            preparedStatement.execute();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @Override
    public void delete(Column element) {

    }
}
