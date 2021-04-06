package model;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class DAOCard implements DAOModel<Card> {

    public static List<Card> getAllByColumn(Column column) {
        try {
            String sql = "SELECT * FROM card WHERE idColumn = ? ORDER BY position;";
            Connection conn = DriverManager.getConnection(url);
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
        return null;
    }

    @Override
    public List<Card> getAll() {
        return null;
    }

    @Override
    public void save(Card element) {

    }

    @Override
    public void add(Card element) {

    }

    @Override
    public void update(Card element) {

    }

    @Override
    public void delete(Card element) {

    }
}
