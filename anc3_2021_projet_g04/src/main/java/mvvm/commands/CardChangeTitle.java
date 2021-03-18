package mvvm.commands;

import model.Board;
import model.Card;
import model.Column;

public class CardChangeTitle implements Command {

    private String oldTitle;
    private String newTitle;
    private Board board;
    private int posColumn;
    private int posCard;

    public CardChangeTitle(Card card, String newTitle) {
        board = card.getBoard();
        posColumn = card.getColumn().getPosition();
        posCard = card.getPosition();
        oldTitle = card.toString();
        this.newTitle = newTitle;
    }

    @Override
    public void execute() {
        getCard().changeTitle(newTitle);
    }

    @Override
    public void unexecute() {
        getCard().changeTitle(oldTitle);
    }

    @Override
    public String toString() {
        return "Changement de titre de la carte \"" + oldTitle + "\" vers \"" + newTitle + "\"";
    }

    private Card getCard(){
        Column column = board.getColumnAtPosition(posColumn);
        Card card = column.getCardAtPosition(posCard);
        return card;
    }

}
