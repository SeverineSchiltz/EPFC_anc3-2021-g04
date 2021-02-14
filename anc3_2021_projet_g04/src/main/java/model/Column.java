package model;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Column {
    private Board board;
    private String title;
    //private final List<Card> listCards = new ArrayList<>();
    private final ObservableList<Card> listCards = FXCollections.observableArrayList();

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

    //TODO: to remove if we don't use it
    public void removeCard(Card c){
        this.listCards.remove(c);
    }

    public void removeCardIndex(int index){
        this.listCards.remove(index);
    }

//    public List<Card> getCards() {
//        return Collections.unmodifiableList(listCards);
//    }

    public SimpleListProperty<Card> getCards() {
        return new SimpleListProperty<>(listCards);
    }

    public void changePositioninBoard(int pos){
        this.board.changeColumnPosition(this, pos);
    }

    public BooleanProperty isLastInBoard(){
        return new SimpleBooleanProperty(this.board.isColumnLast(this));
    }

    public BooleanProperty isFirstInBoard(){
        return new SimpleBooleanProperty(this.board.isColumnFirst(this));
    }

    public boolean isCardFirst(Card card){
        return getCards().indexOf(card) == 0;
    }

    public boolean isCardLast(Card card){
        return getCards().indexOf(card) == listCards.size() - 1;
    }

    /*
    Method to change the card position: 4 options:
    (1) up      posCard: -1     posColumn:  0
    (2) right   posCard:  0     posColumn: +1
    (3) down    posCard: +1     posColumn:  0
    (4) left    posCard:  0     posColumn: -1
     */
    public void changeCardPosition(Card card, int posCard, int posColumn) {
        // current card position on the column
        int curPosCard = this.listCards.indexOf(card);
        // if card moves up or down, same column => posColumn == 0
        if (posColumn == 0) {
            // card position to reach (current one -1 if up or +1 if down)
            int posCardToReach = curPosCard + posCard;
            if (posCardToReach >= 0 && posCardToReach < listCards.size()) {
                Card cardToReplace = listCards.set(posCardToReach, card);
                listCards.set(curPosCard, cardToReplace);
            }
        } else {
            // column where the card is
            Column column = card.getColumn();
            // current column position on the board
            int curPosColumn = this.board.getColumns().indexOf(column); //this.board.getColumns().indexOf(card.getColumn());
            // column position to reach (current one -1 if left or +1 if right)
            int posColumnToReach = curPosColumn + posColumn;
            // if to check column position to reach (is in the range)
            if(posColumnToReach >= 0 && posColumnToReach < this.board.getColumns().size()) {
                // column to reach
                Column columnToReach = this.board.getColumns().get(posColumnToReach);
                // set columnToReach as attribute of the card (if not, the card keeps column)
                card.setColumn(columnToReach);
                // add card on the column to reach
                columnToReach.addCard(card);
                // remove card on the column before the move
                column.removeCard(card);
                //column.removeCardIndex(curPosCard); //column.removeCard(card); => remove the 1st occurence so not appropriate
            }
        }
    }

}
