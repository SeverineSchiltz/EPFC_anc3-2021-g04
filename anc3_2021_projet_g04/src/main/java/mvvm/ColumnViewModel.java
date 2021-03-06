package mvvm;

import javafx.beans.property.*;
import model.*;
import mvvm.commands.*;

public class ColumnViewModel implements TitleManagement{
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

    public BooleanProperty isLeftPossible(){
        return new SimpleBooleanProperty(column.getBoard().getColumnPosition(column) ==0);
    }

    public BooleanProperty isRightPossible(){
        return new SimpleBooleanProperty(column.getBoard().getColumnPosition(column) == column.getBoard().getNumberOfColumn()-1);
    }

//    public BooleanProperty isFirstInBoardProperty(){
//        return column.isLastInBoard();
//    }
//    public BooleanProperty isLastInBoardProperty(){
//        return column.isLastInBoard();
//    }

    public void delete(){
        cmdManager.addCommand(new ColumnDelete(column));
        column.delete();
    }

    public void addCard() {
        this.column.addCard();
    }

    @Override
    public void changeTitle(String newTitle) {
        cmdManager.addCommand(new ColumnChangeTitle(column, newTitle));
        column.changeTitle(newTitle);
    }
}
