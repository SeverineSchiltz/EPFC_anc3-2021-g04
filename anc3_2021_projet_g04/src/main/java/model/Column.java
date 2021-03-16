package model;

import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;


public class Column {
    private Board board;
    private StringProperty title;
    private final ObservableList<Card> cards = FXCollections.observableArrayList();

    Column(String title, Board board){
        this.title= new SimpleStringProperty(title);
        this.board = board;
        //cards.add(null);
    }
    public Column(Column column, Board board) {
        this(column.title.getValue(), board);
        // boucle
        for (Card card : column.getCards()) {
            cards.add(new Card(card, this));
        }
    }

    void restore(Column c){
        this.title.setValue(c.getTitle().getValue());
        int numNew = c.getNumberOfCards();
        int numthis = this.getNumberOfCards();
        int minNum = Math.min(numNew, numthis);
        for(int i = 0; i < minNum; ++i){
            cards.get(i).restore(c.getCards().get(i), this);
        }
        if(numNew < numthis){
            for(int i = numthis-1; i >= minNum; --i){
                cards.remove(i);
            }
        }else if(numNew > numthis){
            for(int i = minNum; i < numNew; ++i){
                cards.add(new Card(c.getCards().get(i), this));
            }
        }
    }

    public StringProperty getTitle(){
        return this.title;
    }

    public void addCard(Card c){
        this.cards.add(c);
    }

    public void addCard() {
        Card cardToAdd = new Card("Card", this); //new Card("Card " + (cards.size() + 1), this);
        this.addCard(cardToAdd);
    }

    public Board getBoard(){
        return this.board;
    }

    public int getPosition(){
        return board.getColumnPosition(this);
    }

    public void removeCard(Card c){
        this.cards.remove(c);
    }

    public ObservableList<Card> getCards() {
        return FXCollections.unmodifiableObservableList(cards);
    }

    public void changePositioninBoard(int pos){
        this.board.changeColumnPosition(this, pos);
    }

//    //TODO: à supprimer dès que sera changer dans les cartes
//    public BooleanProperty isLastInBoard(){
//        return new SimpleBooleanProperty(this.board.isColumnLast(this));
//    }
//    public BooleanProperty isFirstInBoard(){
//        return new SimpleBooleanProperty(this.board.isColumnFirst(this));
//    }
//
//    public boolean isCardFirst(Card card){
//        return getCards().indexOf(card) == 0;
//    }
//
//    public boolean isCardLast(Card card){
//        return getCards().indexOf(card) == cards.size() - 1;
//    }

    public void delete(){
        board.deleteColumn(this);
    }

    // Called method by CardAdd for unexecute() method
    public void deleteCard(int index){
        this.cards.remove(index);
    }

    // Called method by CardDelete for execute() method
    public void deleteCard(Card card){
        this.cards.remove(card);
    }

    // Called method by CardAdd for unexecute() method
    // A card is added at the end of the column so should be removed at the end too
    public int getNumberOfCards(){
        return this.cards.size();
    }

    // Called method by Card
    public int getCardPosition(Card card){
        return this.cards.indexOf(card);
    }

    // Called method by CardDelete for unexecute() method
    public void addCardAtPosition(Card card, int pos){
        cards.add(null);
        for(int i = cards.size() - 1; i > pos; --i){
            cards.set(i, cards.get(i - 1));
        }
        cards.set(pos, card);
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
        int curPosCard = this.cards.indexOf(card);
        // if card moves up or down, same column => posColumn == 0
        if (posColumn == 0) {
            // card position to reach (current one -1 if up or +1 if down)
            int posCardToReach = curPosCard + posCard;
            if (posCardToReach >= 0 && posCardToReach < cards.size()) {
                Card cardToReplace = cards.set(posCardToReach, card);
                cards.set(curPosCard, cardToReplace);
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
            }
        }
    }

    @Override
    public String toString() {
        return this.title.getValue();
    }

    public void changeTitle(String newTitle){
        title.setValue(newTitle);
    }

}
