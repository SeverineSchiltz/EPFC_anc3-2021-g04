package mvvm;

import javafx.beans.property.*;
import model.*;
import mvvm.commands.*;

public class ColumnViewModel {
    private final Column column;
    private CommandManager cmdManager;

    public ColumnViewModel(Column column, CommandManager cmdManager){
        this.column = column;
        this.cmdManager = cmdManager;
    }

    public StringProperty getColumnTitleProperty(){
        return column.getTitle();
    }

    public SimpleListProperty<Card> cardsProperty() {
        return new SimpleListProperty(column.getCards());
    }

    public void changePosition(int pos){
        this.column.changePositioninBoard(pos);
        if(pos>0){
            cmdManager.addCommand(new ColumnMoveToRight(column));
        }else{
            cmdManager.addCommand(new ColumnMoveToLeft(column));
        }
    }

    public BooleanProperty isFirstInBoardProperty(){
        return column.isFirstInBoard();
    }

    public BooleanProperty isLastInBoardProperty(){
        return column.isLastInBoard();
    }

    public void delete(){
        cmdManager.addCommand(new ColumnDelete(column));
        column.delete();
    }

    public void addCard() {
        this.column.addCard();
    }

}
