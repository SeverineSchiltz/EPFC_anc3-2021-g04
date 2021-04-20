package model;

import java.sql.*;

public class DAOInitialisation {

    private static String url = "jdbc:sqlite:dbTrello.db";

    private static void configDB(Connection conn) throws SQLException {
        Statement stmt = conn.createStatement();
        String sql;

        // Activation of checks FK
        sql = "PRAGMA foreign_keys = ON;";
        stmt.execute(sql);
    }


    private static void createTables(Connection conn) throws SQLException {

        String sql;
        Statement stmt = conn.createStatement();

        // SQL statement for board table
        sql = "CREATE TABLE IF NOT EXISTS board ("
                + "	id integer PRIMARY KEY,"
                + "	name text NOT NULL);";
        stmt.execute(sql);

        // SQL statement for column table
        sql = "CREATE TABLE IF NOT EXISTS column ("
                + "	id integer PRIMARY KEY,"
                + "	name text NOT NULL,"
                + "	position integer NOT NULL,"
                + "	idBoard integer NOT NULL,"
                + " CONSTRAINT fk_board FOREIGN KEY (idBoard) "
                + " REFERENCES board(id));"; // ça référence l'id dans la table board
        stmt.execute(sql);

        // SQL statement for card table
        sql = "CREATE TABLE IF NOT EXISTS card ("
                + "	id integer PRIMARY KEY,"
                + "	name text NOT NULL,"
                + "	position integer NOT NULL,"
                + "	idColumn integer NOT NULL,"
                + " CONSTRAINT fk_column FOREIGN KEY (idColumn) "
                + " REFERENCES column(id));"; // ça référence l'id dans la table board
        stmt.execute(sql);

    }

    private static void clearDB(Connection conn) throws SQLException {
        Statement statement = conn.createStatement();
        String sql;

        // Delete first cards, then columns and finally board (because we don't have "on cascade")

        sql = "DELETE FROM card;";
        statement.execute(sql);

        sql = "DELETE FROM column;";
        statement.execute(sql);

        sql = "DELETE FROM board;";
        statement.execute(sql);

    }

    private static void seedBoard(Connection conn) throws SQLException {
        String sql = "INSERT INTO board(id, name) VALUES(?,?);";
        PreparedStatement preparedStatement = conn.prepareStatement(sql);
        preparedStatement.setInt(1,1);
        preparedStatement.setString(2, "Mon tableau");
        preparedStatement.execute();

    }

    private static void seedColumn(Connection conn) throws SQLException {
        String sql = "INSERT INTO column(id, name, position, idBoard) VALUES(?,?,?,?);";
        PreparedStatement preparedStatement = conn.prepareStatement(sql);

        for (int i = 1; i < 4; ++i){
            preparedStatement.setInt(1, i);
            preparedStatement.setString(2, "Colonne " + i);
            preparedStatement.setInt(3, i - 1);
            preparedStatement.setInt(4, 1);
            preparedStatement.execute();
        }

    }

    private static void seedCard(Connection conn) throws SQLException {
        int idColumns[] = {1, 2, 2, 3, 3, 3};
        int positionCards[] = {0, 0, 1, 0, 1, 2};
        String sql = "INSERT INTO card(id, name, position, idColumn) VALUES(?,?,?,?);";
        PreparedStatement preparedStatement = conn.prepareStatement(sql);

        for (int i = 1; i < 7; ++i) {
            preparedStatement.setInt(1, i);
            preparedStatement.setString(2, "Card " + i);
            preparedStatement.setInt(3, positionCards[i - 1]); // [position_dans_le_tableau]
            preparedStatement.setInt(4, idColumns[i - 1]);
            preparedStatement.execute();
        }

    }

    private static void seedData(Connection conn) throws SQLException {

        clearDB(conn);      // Clear the DB
        seedBoard(conn);    // Add the board
        seedColumn(conn);   // Add the columns
        seedCard(conn);     // Add the cards

    }

    public static void main(String[] args) {
        try (Connection conn = DriverManager.getConnection(url)) {
            configDB(conn);
            createTables(conn);
            seedData(conn);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

}
