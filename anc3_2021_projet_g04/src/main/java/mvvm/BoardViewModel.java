package mvvm;

import javafx.beans.property.*;
import model.*;

public class BoardViewModel {
    private Board board;

    BoardViewModel(Board board){
        this.board = board;
    }

    public StringProperty getBoardTitleProperty() {
        return board.getTitle();
    }

    public SimpleListProperty<Column> columnsProperty() {
        return new SimpleListProperty(board.getColumns());
    }

    public void addColumn(){
        board.addColumn();
    }

}
