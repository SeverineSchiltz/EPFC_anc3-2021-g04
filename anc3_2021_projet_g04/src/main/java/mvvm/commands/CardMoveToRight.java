package mvvm.commands;

import model.Card;

//TODO : à refaire avec position!
public class CardMoveToRight implements Command {

    private Card card;

    public CardMoveToRight(Card card) {
        this.card = card;
    }

    @Override
    public void execute() {
        this.card.changePositionInColumn(0, 1);
    }

    @Override
    public void unexecute() {
        this.card.changePositionInColumn(0, -1);
    }

    @Override
    public String toString() {
        return "Déplacement de la carte \"" + this.card + "\" vers la droite";
    }

}
