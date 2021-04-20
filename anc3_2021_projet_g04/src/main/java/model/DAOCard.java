package model;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static java.sql.Statement.RETURN_GENERATED_KEYS;

public class DAOCard implements DAOModel<Card> {

    private static DAOCard daoCard = new DAOCard();

    private DAOCard(){ }

    public static List<Card> getAllByColumn(Column column) {
        try (Connection conn = DriverManager.getConnection(url)) {
            String sql = "SELECT * FROM card WHERE idColumn = ? ORDER BY position;";
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setInt(1,column.getId());
            ResultSet result = preparedStatement.executeQuery();
            List<Card> lc = new ArrayList<>();
            while (result.next()) {
                int id = result.getInt("id");
                String title = result.getString("name");
                Card c = new Card(id, title, column);
                lc.add(c);
            }
            return lc;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return null;
        }
    }

    @Override
    public Card getById(int id) {
        Card card = null;
        try (Connection conn = DriverManager.getConnection(url)) {
            String sql = "SELECT * FROM card WHERE id = ? ;";
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setInt(1, id);
            ResultSet result = preparedStatement.executeQuery();
            if (result.next()) {
                String title = result.getString("name");
                //int position = result.getInt("position");
                int idColumn = result.getInt("idColumn");
                Column column = DAOColumn.getInstance().getById(idColumn);
                card = new Card(id, title, column);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return card;
    }

    @Override
    public List<Card> getAll() {
        List<Card> cards = new ArrayList<>();
        try (Connection conn = DriverManager.getConnection(url)) {
            String sql = "SELECT * FROM card ORDER BY id;";
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            ResultSet result = preparedStatement.executeQuery();
            while (result.next()) {
                int id = result.getInt("id");
                String title = result.getString("name");
                //int position = result.getInt("position"); // quid position?
                int idColumn = result.getInt("idColumn");
                Column column = DAOColumn.getInstance().getById(idColumn);
                Card card = new Card(id, title, column);
                cards.add(card);
            }
            preparedStatement.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return cards;
    }


    //TODO: attention: this method is not used because not working at this stage
    public void save(Card card) {
        Column column = card.getColumn();
        if(getAllByColumn(column).contains(card))
            update(card);
        add(card);
    }

    @Override
    public int add(Card card) {
        int newID = 0;
        try (Connection conn = DriverManager.getConnection(url)) {
            String sql = "INSERT INTO card(name, position, idColumn) VALUES(?, ?, ?) ;";
            // RETURN_GENERATED_KEYS added in the prepareStatement argument
            PreparedStatement preparedStatement = conn.prepareStatement(sql, RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, card.getTitle().getValue());
            preparedStatement.setInt(2, card.getPosition());
            preparedStatement.setInt(3, card.getColumn().getId());
            preparedStatement.execute();
            try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    newID = generatedKeys.getInt(1);
                }
                else {
                    throw new SQLException("Creating card failed, no ID obtained.");
                }
            }
            preparedStatement.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return newID;
    }

    @Override
    public void update(Card card) {
        try (Connection conn = DriverManager.getConnection(url)) {
            String sql = "UPDATE card SET name = ?, position = ?, idColumn = ? WHERE id = ? ;";
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1, card.getTitle().getValue());
            preparedStatement.setInt(2, card.getPosition());
            preparedStatement.setInt(3, card.getColumn().getId());
            preparedStatement.setInt(4, card.getId());
            preparedStatement.execute();
            preparedStatement.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @Override
    public void delete(Card card) {
        try (Connection conn = DriverManager.getConnection(url)) {
            String sql = "DELETE FROM card WHERE id = ? ;";
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setInt(1, card.getId());
            preparedStatement.execute();
            preparedStatement.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public static DAOCard getInstance(){
        return daoCard;
    }

}
