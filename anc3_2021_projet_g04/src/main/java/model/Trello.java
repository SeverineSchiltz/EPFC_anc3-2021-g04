package model;

import javafx.beans.property.StringProperty;

public class Trello {
    private String title;
    private Board board;

    public Trello(){
        initData();
    }

    private void initData() {
        this.title = "Trello";
        this.board = new Board("Mon Tableau");
        int nCard = 0;
        for (int i = 1; i<4; ++i){
            Column c = new Column("Colonne " + i, board);
            for (int j = 1; j<=i; ++j){
                ++nCard;
                c.addCard(new Card("Carte " + nCard, c));
            }
            this.board.addColumn(c);
        }
    }

    public String getTitle(){
        return this.title;
    }

    public StringProperty getBoardTitle(){
        return this.board.getTitle();
    }

    public Board getBoard(){
        return this.board;
    }

}
