package mvvm.commands;

import model.Board;
import model.Column;

public class ColumnMoveToLeft implements Command{

    private String columnTitle;
    private Board board;
    private int positionInList;

    public ColumnMoveToLeft(Column column) {
        columnTitle = column.getTitle().getValue();
        this.board = column.getBoard();
        positionInList = column.getPosition();
        --positionInList; //car la position change juste après dans le modèle
    }
//    @Override
//    public void execute() {
//        this.column.changePositioninBoard(-1);
//    }
//
//    @Override
//    public void unexecute() {
//        this.column.changePositioninBoard(1);
//    }

        @Override
    public void execute() {
        this.board.getColumnAtPosition(positionInList).changePositioninBoard(-1);
        --positionInList;
    }

    @Override
    public void unexecute() {
        Column c= this.board.getColumnAtPosition(positionInList);
        c.changePositioninBoard(1);
        ++positionInList;
    }

    @Override
    public String toString() {
        return "déplacement de la colonne \"" + columnTitle + "\" vers la gauche";
    }

}
