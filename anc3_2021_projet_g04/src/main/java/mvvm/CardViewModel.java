package mvvm;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.StringProperty;
import model.Card;
import mvvm.commands.CommandManager;

public class CardViewModel {
    private final Card card;
    private CommandManager cmdManager;

    public CardViewModel(Card card){
        this.card = card;
    }

    public StringProperty getCardTitleProperty(){
        return card.getTitle();
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
        return card.isInFirstColumn();
    }

    public BooleanProperty isInLastColumnProperty(){
        return card.isInLastColumn();
    }

    public void removeCard() {
        this.card.removeCard();
    }

}
