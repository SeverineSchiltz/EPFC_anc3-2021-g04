package mvvm.commands;

import model.Card;
import model.Column;

public class CardMoveToLeft implements Command {

    private Column columnOrigin;
    private Card card;
    private int posInColumn;

    public CardMoveToLeft(Card card) {
        this.card = card;
        columnOrigin = card.getColumn();
        posInColumn =  card.getPosition();
    }

    @Override
    public void execute() {
        this.card.changePositionInColumn(0, -1);
    }

    @Override
    public void unexecute() {
        //this.card.changePositionInColumn(0, 1);
        card.delete();
        columnOrigin.addCardAtPosition(card,posInColumn);
    }

    @Override
    public String toString() {
        return "Déplacement de la carte \"" + this.card + "\" vers la gauche";
    }

}
