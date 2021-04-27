package model;

import javafx.collections.ObservableList;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static java.sql.Statement.RETURN_GENERATED_KEYS;

public class DAOColumn implements DAOModel<Column> {

    private static DAOColumn daoCo = new DAOColumn();

    private DAOColumn(){ }

    //TODO : question, vaut-il mieux les avoir static ou lié à l'instance?
    public List<Column> getAllByBoard(Board board) {
        try (Connection conn = DriverManager.getConnection(url)) {
            String sql = "SELECT * FROM column WHERE idBoard = ? ORDER BY position;";
            //Connection conn = DriverManager.getConnection(url);
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
        Column column = null;
        try (Connection conn = DriverManager.getConnection(url)) {
            String sql = "SELECT * FROM column WHERE id = ? ;";
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setInt(1, id);
            ResultSet result = preparedStatement.executeQuery();
            if (result.next()) {
                String title = result.getString("name");
                int idBoard = result.getInt("idBoard");
                Board board = DAOBoard.getInstance().getById(idBoard);
                column = new Column(id, title, board);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return column;
    }

    @Override
    public List<Column> getAll() {
        return null;
    }

    public void save(Column column) {
        Column c = getById(column.getId());
        if(c == null){
            add(column);
        }else{
            update(column);
        }
        saveAllCardsInColumn(column);
    }

    @Override
    public int add(Column column) {
        int newID = 0;
        try (Connection conn = DriverManager.getConnection(url)) {
            String sql = "INSERT INTO column(name, position, idBoard) VALUES(?,?,?);";
            PreparedStatement preparedStatement = conn.prepareStatement(sql, RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, column.getTitle().getValue());
            preparedStatement.setInt(2, column.getPosition());
            preparedStatement.setInt(3, column.getBoard().getId());
            //preparedStatement.execute();
            preparedStatement.executeUpdate();
            try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    newID = generatedKeys.getInt(1);
                }
                else {
                    throw new SQLException("Creating column failed, no ID obtained.");
                }
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        column.setID(newID);
        //addAllCardsInColumn(column);
        return newID;
    }

    @Override
    public void update(Column column) {
        try (Connection conn = DriverManager.getConnection(url)) {
            String sql = "UPDATE column SET name  = ?, position=?, idBoard= ? WHERE id = ?;";
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1, column.getTitle().getValue());
            preparedStatement.setInt(2, column.getPosition());
            preparedStatement.setInt(3, column.getBoard().getId());
            preparedStatement.setInt(4, column.getId());
            preparedStatement.execute();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        //updateAllCard(column.getCards());
    }

    @Override
    public void delete(Column column) {
        deleteAllCardsInColumn(column);
        try (Connection conn = DriverManager.getConnection(url)) {
            String sql = "DELETE FROM column WHERE id = ?;";
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setInt(1, column.getId());
            preparedStatement.execute();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

    }

    public static DAOColumn getInstance(){
        return daoCo;
    }

    private void deleteAllCardsInColumn(Column c){
        for (Card card : c.getCards()) {
            DAOCard.getInstance().delete(card);
        }
    }

    private void saveAllCardsInColumn(Column c){
        for (Card card : c.getCards()) {
            DAOCard.getInstance().save(card);
        }
    }

}
