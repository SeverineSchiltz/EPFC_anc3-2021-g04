package mvvm;

import javafx.beans.property.*;
import model.*;

public class ColumnViewModel {
    private final Column column;
    private final StringProperty title = new SimpleStringProperty("");

    public ColumnViewModel(Column column){
        this.column = column;
        configData();
    }

    private void configData() {
        title.setValue(this.column.getTitle());
    }

    public StringProperty getColumnTitleProperty(){
        return title;
    }

    public SimpleListProperty<Card> cardsProperty() {
        return new SimpleListProperty<>(column.getCards());
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

    public void delete(){
        column.delete();
    }

    public void addCard() {
        this.column.addCard();
    }

}
