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


    public void save(Board board) {
        Board b = getById(board.getId());
        if (b == null) {
            add(board); // useful? we don't add boards
        } else {
            update(board);
        }
        saveAllColumnsInBoard(board);
    }

    @Override
    public int add(Board board) {
        return 0;
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

    public void updateColumnsAsFromPosition(Board board, int pos, int op) {
        String sql;
        try (Connection conn = DriverManager.getConnection(url)) {
            if(op>=0){
                sql = "UPDATE column SET position = position +" + op + " WHERE position >= ? AND idBoard = ?;";
            }else{
                sql = "UPDATE column SET position = position " + op + " WHERE position >= ? AND idBoard = ?;";
            }
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setInt(1, pos);
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
