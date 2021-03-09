package mvvm;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.StringProperty;
import model.Card;
import mvvm.commands.*;

public class CardViewModel implements TitleManagement{
    private final Card card;
    private CommandManager cmdManager;

    public CardViewModel(Card card, CommandManager cmdManager){
        this.card = card;
        this.cmdManager = cmdManager;
    }

    public StringProperty getCardTitleProperty(){
        return card.getTitle();
    }

    public void changePosition(int posCard, int posColumn){
        this.card.changePositionInColumn(posCard, posColumn);
        if(posCard == 0) {
            if(posColumn == 1) {
                cmdManager.addCommand(new CardMoveToRight(this.card));
            } else {
                cmdManager.addCommand(new CardMoveToLeft(this.card));
            }
        } else {
            if(posCard == 1) {
                cmdManager.addCommand(new CardMoveToDown(this.card));
            } else {
                cmdManager.addCommand(new CardMoveToUp(this.card));
            }
        }
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

    @Override
    public void changeTitle(String newTitle) {
        cmdManager.addCommand(new CardChangeTitle(card, newTitle));
        card.changeTitle(newTitle);
    }

    public void delete(){
        cmdManager.addCommand(new CardDelete(card));
        card.delete();
    }
}
