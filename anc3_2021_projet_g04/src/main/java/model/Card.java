package model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Card {
    private Column column;
    private final StringProperty title;
    private int id;

    Card(String title, Column column){
        this.title= new SimpleStringProperty(title);
        this.column = column;
    }

    Card(int id, String title, Column column){
        this.title= new SimpleStringProperty(title);
        this.column = column;
        this.id = id;
    }

    Card(Card card, Column column) {
        this(card.title.getValue(), column);
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
    }

    @Override
    public String toString() {
        return this.title.getValue();
    }

}
