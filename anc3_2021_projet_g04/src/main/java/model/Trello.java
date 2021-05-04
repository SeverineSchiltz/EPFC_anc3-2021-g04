package model;

import javafx.beans.property.StringProperty;

public class Trello {
    private String title;
    private Board board;

    public Trello(){
        initData();
    }

    private void initData() {
//        this.title = "Trello";
//        this.board = new Board("Mon Tableau");
//        int nCard = 0;
//        for (int i = 1; i<4; ++i){
//            Column c = new Column("Colonne " + i, board);
//            for (int j = 1; j<=i; ++j){
//                ++nCard;
//                c.addCard(new Card("Carte " + nCard, c));
//            }
//            this.board.addColumn(c);
//        }

        this.board = DAOBoard.getInstance().getById(1);
        for(Column co: DAOColumn.getInstance().getAllByBoard(board)){
            board.addColumn(co);
            for(Card ca : DAOCard.getAllByColumn(co)){
                System.out.println(ca.getPositionInColumn()); // checks
                co.addCard(ca);
            }
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
