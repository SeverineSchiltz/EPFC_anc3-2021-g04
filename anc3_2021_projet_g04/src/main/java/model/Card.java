package model;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;

public class Card {
    private Column column;
    private String title;

    public Card(String title){
        this.title = title;
    }

    // TODO: remove if not necessary
    public Card(String title, Column column){
        this.title = title;
        this.column = column;
    }

    public String getTitle(){
        return this.title;
    }

    public Column getColumn(){
        return this.column;
    }

    public BooleanProperty isFirstInColumn(){
        return new SimpleBooleanProperty(this.getColumn().isCardFirst(this));
    }

    public BooleanProperty isLastInColumn(){
        return new SimpleBooleanProperty(this.getColumn().isCardLast(this));
    }

    public void changePositionInColumn(int posCard, int posColumn){
        this.getColumn().changeCardPosition(this, posCard, posColumn);
    }


}
