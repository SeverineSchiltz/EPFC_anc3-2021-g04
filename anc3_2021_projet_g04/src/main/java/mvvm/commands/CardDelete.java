package mvvm.commands;

import model.Card;

//TODO : à supprimer car ne fonctionne pas sans memento!
public class CardDelete implements Command {

    private Card card;
    private int positionInList;

    public CardDelete(Card card) {
        this.card = card;
        positionInList = card.getPosition();
    }

    @Override
    public void execute() {
        this.card.delete();
    }

    @Override
    public void unexecute() {
        this.card.getColumn().addCardAtPosition(card, positionInList);
    }

    @Override
    public String toString() {
        return "Supprimer la carte \"" + this.card + "\"";
    }

}
