package mvvm;

import javafx.beans.property.*;
import model.*;

public class BoardViewModel {
    private Board board;
    private StringProperty boardTitle = new SimpleStringProperty();

    public BoardViewModel(Board board){
        this.board = board;
    }

    public StringProperty newBoardTitleProperty() {
        return boardTitle;
    }

    public SimpleListProperty<Column> columnsProperty() {
        return board.getColumns();
    }

    public void addColumn(){
        board.addColumn();
    }

}
