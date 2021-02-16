package mvvm;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import model.Card;

public class CardViewModel {
    private final Card card;
    private final StringProperty title = new SimpleStringProperty("");

    public CardViewModel(Card card){
        this.card = card;
        title.setValue(this.card.getTitle());
    }

    public StringProperty getCardTitleProperty(){
        return title;
    }

    public void changePosition(int posCard, int posColumn){
        this.card.changePositionInColumn(posCard, posColumn);
    }

    public BooleanProperty isFirstInColumnProperty(){
        return card.isFirstInColumn();
    }

    public BooleanProperty isLastInColumnProperty(){
        return card.isLastInColumn();
    }

    public BooleanProperty isInFirstColumnProperty(){
        return card.getColumn().isFirstInBoard();
    }

    public BooleanProperty isInLastColumnProperty(){
        return card.getColumn().isLastInBoard();
    }

    public void removeCard() {
        //this.card.getColumn().removeCard(this.card); //TODO: check not ideal
        this.card.removeCard();
    }

}
