package mvvm.commands;

import model.Column;

public class ColumnMoveToLeft implements Command{

    private final Column column;

    public ColumnMoveToLeft(Column column) {
        this.column = column;
    }
    @Override
    public void execute() {
        this.column.changePositioninBoard(-1);
    }

    @Override
    public void unexecute() {
        this.column.changePositioninBoard(1);
    }
    @Override
    public String toString() {
        return "déplacement de la colonne \"" + column + "\" vers la gauche";
    }

}
