package mvvm.commands;

import model.Column;

public class CardAdd implements Command {

    private Column column;

    public CardAdd(Column column) {
        this.column = column;
    }

    @Override
    public void execute() {
        this.column.addCard();
    }

    @Override
    public void unexecute() {
        int indexOfCardToDelete = this.column.getNumberOfCards() - 1;
        this.column.deleteCard(indexOfCardToDelete);
    }

    @Override
    public String toString() {
        return "Ajout d'une carte sur la colonne \"" + this.column + "\"";
    }

}
