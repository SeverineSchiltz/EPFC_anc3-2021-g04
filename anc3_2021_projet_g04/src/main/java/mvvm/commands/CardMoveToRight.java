package mvvm.commands;

import model.Board;
import model.Card;
import model.Column;

public class CardMoveToRight implements Command {

    private Card card;
    private int posInColumn;

    public CardMoveToRight(Card card) {
        this.card = card;
        posInColumn = card.getPosition();
    }

    @Override
    public void execute() {
        this.card.changePositionInColumn(0, 1);
    }

    @Override
    public void unexecute() {
        //this.card.changePositionInColumn(0, -1);
        Column currentColumn = card.getColumn();
        Board board = currentColumn.getBoard();
        Column columnToReach = board.getColumnAtPosition(currentColumn.getPosition()-1);
        card.delete();
        columnToReach.addCardAtPosition(card, posInColumn);
    }

    @Override
    public String toString() {
        return "Déplacement de la carte \"" + this.card + "\" vers la droite";
    }

}
