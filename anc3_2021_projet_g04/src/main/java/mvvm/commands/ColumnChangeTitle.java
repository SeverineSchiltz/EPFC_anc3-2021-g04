package mvvm.commands;

import model.Board;
import model.Column;

public class ColumnChangeTitle implements Command{

    private Board board;
    private int positionInList;
    private String oldTitle;
    private String newTitle;

    public ColumnChangeTitle(Column column, String newTitle) {
        this.board = column.getBoard();
        positionInList = column.getPosition();
        oldTitle = column.toString();
        this.newTitle = newTitle;
    }
//    @Override
//    public void execute() {
//        column.changeTitle(newTitle);
//    }
//
//    @Override
//    public void unexecute() {
//        column.changeTitle(oldTitle);
//    }

        @Override
    public void execute() {
            board.getColumnAtPosition(positionInList).changeTitle(newTitle);
    }

    @Override
    public void unexecute() {
        board.getColumnAtPosition(positionInList).changeTitle(oldTitle);
    }

    @Override
    public String toString() {
        return "Changement de titre de la colonne \"" + oldTitle + "\" vers \"" + newTitle + "\"";
    }

}
