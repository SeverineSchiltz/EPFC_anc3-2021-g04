package mvvm.commands;

import model.Board;
import model.Column;

public class ColumnAdd implements Command{

    private final Board board;
    private final Column column;

    public ColumnAdd(Board board) {
        this.board = board;
        // This is to create a new column, in order to keep the reference, column is not added on the board.
        this.column = this.board.createNewColumn();
    }

    @Override
    public void execute() {
        board.addColumnAtPosition(column, board.getNumberOfColumn());
    }

    @Override
    public void unexecute() {
        column.delete();
    }

    @Override
    public String toString() {
        return "Ajout d'une colonne";
    }

}
