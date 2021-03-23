package mvvm.commands;

import model.Column;

public class ColumnDelete implements Command{

    private final Column column;
    private final int positionInList;

    public ColumnDelete(Column column) {
        this.column = column;
        positionInList = column.getPosition();
    }

    @Override
    public void execute() {
        column.delete();
    }

    @Override
    public void unexecute() {
        column.getBoard().addColumnAtPosition(column, positionInList);
    }

    @Override
    public String toString() {
        return "Supprimer la colonne \"" + column + "\"";
    }

}
