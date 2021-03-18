package mvvm.commands;

import model.Board;
import model.Card;
import model.Column;

public class CardMoveToDown implements Command {

    private String cardTitle;
    private Board board;
    private int posColumn;
    private int posCard;

    public CardMoveToDown(Card card) {
        board = card.getBoard();
        posColumn = card.getColumn().getPosition();
        posCard = card.getPosition();
        cardTitle = card.toString();
        ++posCard;
    }

    @Override
    public void execute() {
        getCard().changePositionInColumn(1, 0);
        ++posCard;
    }

    @Override
    public void unexecute() {
        getCard().changePositionInColumn(-1, 0);
        --posCard;
    }

    @Override
    public String toString() {
        return "Déplacement de la carte \"" + cardTitle + "\" vers le bas";
    }

    private Card getCard(){
        Column column = board.getColumnAtPosition(posColumn);
        Card card = column.getCardAtPosition(posCard);
        return card;
    }

}
