package mvvm.commands;

import model.Board;
import model.Card;
import model.Column;

public class CardMoveToLeft implements Command {

    private String cardTitle;
    private Board board;
    private int firstPosColumn;
    private int firstPosCard;
    private int secondPosColumn;
    private int secondPosCard;

    public CardMoveToLeft(Card card) {
        board = card.getBoard();
        firstPosColumn = card.getColumn().getPosition();
        firstPosCard = card.getPosition();
        cardTitle = card.toString();
        secondPosColumn = firstPosColumn -1;
        Column c = board.getColumnAtPosition(secondPosColumn);
        secondPosCard = c.getNumberOfCards();
    }

    @Override
    public void execute() {
        Card card = getCardAtPos(firstPosColumn, firstPosCard);
        card.changePositionInColumn(0, -1);
    }

    @Override
    public void unexecute() {
        Card card = getCardAtPos(secondPosColumn, secondPosCard);
        Column c = board.getColumnAtPosition(firstPosColumn);
        card.delete();
        c.addCardAtPosition(card, firstPosCard);
    }

    @Override
    public String toString() {
        return "Déplacement de la carte \"" + cardTitle + "\" vers la gauche";
    }

    private Card getCardAtPos(int posColumn, int posCardInColumn){
        Column column = board.getColumnAtPosition(posColumn);
        Card card = column.getCardAtPosition(posCardInColumn);
        return card;
    }



}
