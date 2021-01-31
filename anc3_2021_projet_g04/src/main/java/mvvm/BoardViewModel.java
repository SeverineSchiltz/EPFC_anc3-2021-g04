package mvvm;

import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.*;

import java.util.ArrayList;

public class BoardViewModel {
    private Board board;
    private final ObservableList<Column> listColumns = FXCollections.observableList(new ArrayList<>());
    private StringProperty boardTitle = new SimpleStringProperty();

    public BoardViewModel(Board board){
        this.board = board;
        configData();

    }

    public StringProperty newBoardTitleProperty() {
        return boardTitle;
    }

    private void configData() {
        listColumns.setAll(board.getColumns());
    }

    public SimpleListProperty<Column> columnsProperty() {
        return new SimpleListProperty<>(listColumns);
    }

}
