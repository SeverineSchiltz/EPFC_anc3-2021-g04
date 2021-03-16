package model;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Card {
    private Column column;
    private StringProperty title;

    Card(String title, Column column){
        this.title= new SimpleStringProperty(title);
        this.column = column;
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

    // Called method by CardDelete for constructor
    public int getPosition(){
        return this.column.getCardPosition(this);
    }

//    //TODO: à supprimer, faut gérer ça dans les view models
//    public BooleanProperty isFirstInColumn(){
//        return new SimpleBooleanProperty(this.column.isCardFirst(this));
//    }
//    public BooleanProperty isLastInColumn(){
//        return new SimpleBooleanProperty(this.column.isCardLast(this));
//    }
//    public BooleanProperty isInFirstColumn(){
//        return this.column.isFirstInBoard();
//    }
//    public BooleanProperty isInLastColumn(){
//        return this.column.isLastInBoard();
//    }

    public void changePositionInColumn(int posCard, int posColumn){
        this.column.changeCardPosition(this, posCard, posColumn);
    }

    // Called method by CardDelete for execute() method
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
