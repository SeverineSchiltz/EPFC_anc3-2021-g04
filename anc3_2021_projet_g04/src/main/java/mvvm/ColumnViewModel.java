package mvvm;

import javafx.beans.property.*;
import model.*;
import mvvm.commands.*;

public class ColumnViewModel implements TitleManagement{
    private final Column column;
    private final CommandManager cmdManager;

    public ColumnViewModel(Column column, CommandManager cmdManager){
        this.column = column;
        this.cmdManager = cmdManager;
    }

    public CommandManager getCmdManager(){
        return cmdManager;
    }

    public StringProperty getColumnTitleProperty(){
        return column.getTitle();
    }

    public SimpleListProperty<Card> cardsProperty() {
        return new SimpleListProperty(column.getCards());
    }

    public void changePosition(int pos){
        if(pos>0){
            cmdManager.addCommand(new ColumnMoveToRight(column));
        }else{
            cmdManager.addCommand(new ColumnMoveToLeft(column));
        }
        cmdManager.execute();
    }

    public BooleanProperty isLeftDisabledProperty(){
        return new SimpleBooleanProperty(column.getBoard().getColumnPosition(column) ==0);
    }

    public BooleanProperty isRightDisabledProperty(){
        return new SimpleBooleanProperty(column.getBoard().getColumnPosition(column) == column.getBoard().getNumberOfColumn()-1);
    }

    public void delete(){
        cmdManager.addCommand(new ColumnDelete(column));
        cmdManager.execute();
    }

    public void addCard() {
        cmdManager.addCommand(new CardAdd(this.column));
        cmdManager.execute();
    }

    @Override
    public void changeTitle(String newTitle) {
        cmdManager.addCommand(new ColumnChangeTitle(column, newTitle));
        cmdManager.execute();
    }
}
