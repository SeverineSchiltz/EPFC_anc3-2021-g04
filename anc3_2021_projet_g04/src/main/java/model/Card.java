package model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Card {
    private Column column;
    private final StringProperty title;
    private int id;
    private int positionInColumn;

    Card(String title, Column column){
        this.title= new SimpleStringProperty(title);
        this.column = column;
    }

    Card(int id, String title, Column column){
        this(title, column);
        this.id = id;
    }

    Card(Card card, Column column) {
        this(card.getId(), card.title.getValue(), column);
        setPositionInColumn(card.getPositionInColumn());
    }

    Card(int id, String title, Column column, int position){
        this(id, title, column);
        this.positionInColumn = position;
    }

    public int getId(){
        return id;
    }

    public void setID(int id){
        this.id = id;
    }

    public int getPositionInColumn(){
        return this.positionInColumn;
    }

    public void setPositionInColumn(int position){
        this.positionInColumn = position;
    }

    void restore(Card c, Column co){
        this.title.setValue(c.getTitle().getValue());
        this.column = co;
    }

    public StringProperty getTitle(){
        return this.title;
    }

    public Column getColumn(){
        return this.column;
    }

    public void setColumn(Column column){
        this.column = column;
    }

    public int getPosition(){
        return this.column.getCardPosition(this);
    }

    public void changePositionInColumn(int posCard, int posColumn){
        this.column.changeCardPosition(this, posCard, posColumn);
    }

    public void delete() {
        this.column.deleteCard(this);
    }

    public void changeTitle(String newTitle){
        title.setValue(newTitle);
        DAOCard.getInstance().update(this);
    }

    @Override
    public String toString() {
        return this.title.getValue();
    }

}
