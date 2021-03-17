package mvvm.commands;

import model.BoardMemento;
import model.Card;

public class CardDeleteWithMemento implements Command {

    private String cardTitle;
    private BoardMemento boardMemento;

    public CardDeleteWithMemento(Card card) {
        this.cardTitle = card.getTitle().getValue();
        this.boardMemento = new BoardMemento(card.getBoard());
    }
    @Override
    public void execute() {
        boardMemento.restore();
    }

    @Override
    public void unexecute() {
        boardMemento.restore();
    }

    @Override
    public String toString() {
        return "Supprimer la colonne \"" + cardTitle + "\"";
    }

}