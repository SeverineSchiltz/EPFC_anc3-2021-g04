package mvvm.commands;

import model.Board;

public class ColumnAdd implements Command{

    private Board board;

    public ColumnAdd(Board board) {
        this.board = board;
    }
    @Override
    public void execute() {
        board.addColumn();
    }

    @Override
    public void unexecute() {
        int indexOfColumnToDelete = board.getNumberOfColumn()-1;
        board.deleteColumn(indexOfColumnToDelete);
    }

    @Override
    public String toString() {
        return "Ajout d'une colonne";
    }

}
