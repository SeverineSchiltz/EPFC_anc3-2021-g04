package mvvm.commands;

import model.Board;
import model.Column;

public class CardAdd implements Command {

    private Board board;
    private int posColumn;
    private String columnTitle;

    public CardAdd(Column column) {
        this.board = column.getBoard();
        columnTitle = column.getTitle().getValue();
        posColumn = column.getPosition();
    }

    @Override
    public void execute() {
        board.getColumnAtPosition(posColumn).addCard();
    }

    @Override
    public void unexecute() {
        Column column = board.getColumnAtPosition(posColumn);
        int indexOfCardToDelete = column.getNumberOfCards() - 1;
        column.deleteCard(indexOfCardToDelete);
    }

    @Override
    public String toString() {
        return "Ajout d'une carte sur la colonne \"" + columnTitle + "\"";
    }

}
