package mvvm.commands;

import model.Card;
import model.Column;

public class CardMoveToLeft implements Command {

    private final Column columnOrigin;
    private final Card card;
    private final int posInColumn;

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
        card.delete();
        columnOrigin.addCardAtPosition(card,posInColumn);
    }

    @Override
    public String toString() {
        return "Déplacement de la carte \"" + this.card + "\" vers la gauche";
    }

}
