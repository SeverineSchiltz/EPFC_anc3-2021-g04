package mvvm.commands;

import model.Board;
import model.Column;

public class ColumnAdd implements Command{

    private final Board board;
    private Column column;

    public ColumnAdd(Board board) {
        this.board = board;
    }
    @Override
    public void execute() {
        board.addColumnAtPosition(column, board.getNumberOfColumn());
    }

    @Override
    public void unexecute() {
        int indexOfColumnToDelete = board.getNumberOfColumn()-1;
        column =  board.getColumnAtPosition(indexOfColumnToDelete);
        board.deleteColumn(indexOfColumnToDelete);
    }

    @Override
    public String toString() {
        return "Ajout d'une colonne";
    }

}
