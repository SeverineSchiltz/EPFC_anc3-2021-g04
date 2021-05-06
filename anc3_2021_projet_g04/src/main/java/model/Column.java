package model;

import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;


public class Column {
    private int id;
    private Board board;
    private final StringProperty title;
    private final ObservableList<Card> cards = FXCollections.observableArrayList();

    Column(int id, String title, Board board){
        this(title, board);
        this.id = id;
    }

    Column(String title, Board board){
        this.title= new SimpleStringProperty(title);
        this.board = board;
    }

    Column(Column column, Board board) {
        this(column.getId(), column.title.getValue(), board);
        for (Card card : column.getCards()) {
            cards.add(new Card(card, this));
        }
    }

    public int getId(){
        return id;
    }

    public void setID(int id){
        this.id = id;
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

    public Card addCard() {
        Card card = createNewCard();
        this.addCard(card);
        return card;
    }

    public Card createNewCard() {
        return new Card("Card", this);
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

    public void delete(){
        board.deleteColumn(this);
    }

    public void deleteCard(int index){
        this.cards.remove(index);
    }

    public void deleteCard(Card card) {
        int pos = card.getPosition();
        for(int i = cards.size() - 1; i > pos; --i) {
            Card tmp = cards.get(i);
            tmp.setPositionInColumn(i - 1);
            DAOCard.getInstance().update(tmp);
        }
        this.cards.remove(card);
        DAOCard.getInstance().delete(card);
    }

    public int getNumberOfCards(){
        return this.cards.size();
    }

    public int getCardPosition(Card card){
        return this.cards.indexOf(card);
    }

    public Card getCardAtPosition(int index){
        return cards.get(index);
    }

    public void addCardAtPosition(Card card, int pos){
        for(int i = cards.size() - 1; i >= pos; --i) {
            Card tmp = cards.get(i);
            tmp.setPositionInColumn(i + 1);
            DAOCard.getInstance().update(tmp);
        }
        cards.add(pos, card);
        card.setColumn(this);
        card.setPositionInColumn(pos);
        int newID = DAOCard.getInstance().add(card);
        card.setID(newID);
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
        int curPosCard = this.cards.indexOf(card); //int curPosCard = card.getPositionInColumn(); ?
        // if card moves up or down, same column => posColumn == 0
        if (posColumn == 0) {
            // card position to reach (current one -1 if up or +1 if down)
            int posCardToReach = curPosCard + posCard;
            if (posCardToReach >= 0 && posCardToReach < cards.size()) {
                Card cardToReplace = cards.set(posCardToReach, card);
                cards.set(curPosCard, cardToReplace);
                // set card positions for current card and card to replace
                card.setPositionInColumn(posCardToReach);
                cardToReplace.setPositionInColumn(curPosCard);
                // update
                DAOCard.getInstance().update(cardToReplace);
                DAOCard.getInstance().update(card);
            }
        } else {
            // TODO: review column to reach
            Column columnToReach = getBoard().getColumns().get(this.getPosition() + posColumn);
            // delete card from this column
            deleteCard(card);
            // add card to column to reach
            columnToReach.addCardAtPosition(card, columnToReach.getNumberOfCards());
        }
    }

    // TODO: method to delete after review
    public void changeCardPositionOldVersion(Card card, int posCard, int posColumn) {
        // current card position on the column
        int curPosCard = this.cards.indexOf(card);
        // if card moves up or down, same column => posColumn == 0
        if (posColumn == 0) {
            // card position to reach (current one -1 if up or +1 if down)
            int posCardToReach = curPosCard + posCard;
            if (posCardToReach >= 0 && posCardToReach < cards.size()) {
                Card cardToReplace = cards.set(posCardToReach, card);
                cards.set(curPosCard, cardToReplace);
                DAOCard.getInstance().update(cardToReplace);
                DAOCard.getInstance().update(card);
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
                DAOCard.getInstance().update(card);
            }
        }
    }

    @Override
    public String toString() {
        return this.title.getValue();
    }

    public void changeTitle(String newTitle){
        title.setValue(newTitle);
        DAOColumn.getInstance().update(this);
    }

    public void setBoard(Board b){
        this.board = b;
    }

    static Column createNewColumn(String title, Board board){
        //TODO : Vaut-il mieux récupérer l'id dans la DB ou bien peut-on gérer ça avec un compteur (qui irait rechercher le dernière id)?
        // Si il ft récupérer l'id en DB, l'id n'est pas final, cela pose-t-il problème?
        Column c = new Column(title, board);

        return c;
    }

    static Column createNewDefaultColumn(Board board){
        Column c = createNewColumn("Column", board);

        return c;
    }



}
