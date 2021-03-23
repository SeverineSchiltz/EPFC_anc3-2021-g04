package mvvm.commands;

import model.Card;
import model.Column;

public class CardAdd implements Command {

    private final Column column;
    private final Card card;

    public CardAdd(Column column) {
        this.column = column;
        this.card = this.column.createNewCard();
    }

    @Override
    public void execute() {
        column.addCardAtPosition(card, column.getNumberOfCards());
    }

    @Override
    public void unexecute() {
        this.card.delete();
    }

    @Override
    public String toString() {
        return "Ajout d'une carte sur la colonne \"" + this.column + "\"";
    }

}
