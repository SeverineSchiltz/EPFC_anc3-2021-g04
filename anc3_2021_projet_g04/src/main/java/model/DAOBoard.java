package model;

import javafx.collections.ObservableList;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static java.sql.Statement.RETURN_GENERATED_KEYS;

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
        List<Board> boards = new ArrayList<>();
        try (Connection conn = DriverManager.getConnection(url)) {
            String sql = "SELECT * FROM board ORDER BY id;";
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            ResultSet result = preparedStatement.executeQuery();
            while (result.next()) {
                int id = result.getInt("id");
                String title = result.getString("name");
                Board board = new Board(id, title);
                boards.add(board);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return boards;
    }


    public void save(Board board) {
        Board b = getById(board.getId());
        if (b == null) {
            add(board);
        } else {
            update(board);
        }
        saveAllColumnsInBoard(board);
    }

    @Override
    public int add(Board board) {
        int newID = 0;
        try (Connection conn = DriverManager.getConnection(url)) {
            String sql = "INSERT INTO board(name) VALUES(?) ;";
            PreparedStatement preparedStatement = conn.prepareStatement(sql, RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, board.getTitle().getValue());
            preparedStatement.execute();
            try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    newID = generatedKeys.getInt(1);
                }
                else {
                    throw new SQLException("Creating board failed, no ID obtained.");
                }
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        // TODO: ask Séverine
        board.setID(newID);
        return newID;
    }

    @Override
    public void update(Board board) {
        try (Connection conn = DriverManager.getConnection(url)) {
            String sql = "UPDATE board SET name  = ? WHERE id = ?;";
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1, board.getTitle().getValue());
            preparedStatement.setInt(2, board.getId());
            preparedStatement.execute();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        //updateAllColumns(board.getColumns());
    }

    @Override
    public void delete(Board board) {
        deleteAllColumnsInBoard(board);
        try (Connection conn = DriverManager.getConnection(url)) {
            String sql = "DELETE FROM board WHERE id = ?;";
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setInt(1, board.getId());
            preparedStatement.execute();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    private void deleteAllColumnsInBoard(Board board){
        for (Column column : board.getColumns()) {
            DAOColumn.getInstance().delete(column);
        }
    }

    private void saveAllColumnsInBoard(Board board){
        for (Column column : board.getColumns()) {
            DAOColumn.getInstance().save(column);
        }
    }

}
