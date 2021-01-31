package model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Column {
    private Board board;
    private String title;
    private final List<Card> listCards = new ArrayList<>();

    public Column(String title, Board board){
        this.title = title;
        this.board = board;
    }
    public String getTitle(){
        return this.title;
    }

    public void addCard(Card c){
        this.listCards.add(c);
    }

    public List<Card> getCards() {
        return Collections.unmodifiableList(listCards);
    }

    public void changePositioninBoard(int pos){
        this.board.changeColumnPosition(this, pos);
    }

    public boolean isLastInBoard(){
        return this.board.isColumnLast(this);
    }

    public boolean isFirstInBoard(){
        return this.board.isColumnFirst(this);
    }

}
