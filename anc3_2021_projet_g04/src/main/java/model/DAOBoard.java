package model;

import javafx.collections.ObservableList;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class DAOBoard implements DAOModel<Board> { // enter the element type !

    private static DAOBoard daoB = new DAOBoard();

    private DAOBoard(){ }

    public static DAOBoard getInstance(){
        return daoB;
    }

    @Override
    public Board getById(int idBoard) {
        try (Connection conn = DriverManager.getConnection(url)) {
            String sql = "SELECT * FROM board WHERE id = ? ;";
            //Connection conn = DriverManager.getConnection(url);
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


    public void save(Board element) {

    }

    @Override
    public int add(Board element) {
        return 0;
    }

    @Override
    public void update(Board element) {

    }

    @Override
    public void delete(Board element) {

    }

    public void updateAllColumns(ObservableList<Column> columns){
        for (Column c : columns) {
            DAOColumn.getInstance().update(c);
        }
    }


}
