package mvvm.commands;

import model.Card;
import model.Column;

public class CardAdd implements Command {

    private final Column column;
    private Card card;

    public CardAdd(Column column) {
        this.column = column;
    }

    @Override
    public void execute() {
        column.addCardAtPosition(card, column.getNumberOfCards());
    }

    @Override
    public void unexecute() {
        int indexOfCardToDelete = this.column.getNumberOfCards() - 1;
        card =  column.getCardAtPosition(indexOfCardToDelete);
        this.column.deleteCard(indexOfCardToDelete);
    }

    @Override
    public String toString() {
        return "Ajout d'une carte sur la colonne \"" + this.column + "\"";
    }

}
