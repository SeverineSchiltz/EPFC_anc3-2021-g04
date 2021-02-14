package mvvm;

import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.*;

public class ColumnViewModel {
    private final Column column;
    private final StringProperty title = new SimpleStringProperty("");
    private final ObservableList<Card> listCards = FXCollections.observableArrayList();

    public ColumnViewModel(Column column){
        this.column = column;
        configData();
    }

    private void configData() {
        title.setValue(this.column.getTitle());
        listCards.setAll(column.getCards());
    }

    public StringProperty getColumnTitleProperty(){
        return title;
    }

    public SimpleListProperty<Card> cardsProperty() {
        return new SimpleListProperty<>(listCards);
        //return column.getCards();
    }

    public void changePosition(int pos){
        this.column.changePositioninBoard(pos);
    }

    public BooleanProperty isFirstInBoardProperty(){
        return column.isFirstInBoard();
    }

    public BooleanProperty isLastInBoardProperty(){
        return column.isLastInBoard();
    }

}
