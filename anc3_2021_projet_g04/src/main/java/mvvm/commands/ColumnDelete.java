package mvvm.commands;

import model.Column;

//TODO: à supprimer car ne fonctionne pas sans memento!
public class ColumnDelete implements Command{

    private String columnTitle;
    private Column column;
    private int positionInList;

    public ColumnDelete(Column column) {
        this.column = column;
        columnTitle = column.getTitle().getValue();
        positionInList = column.getPosition();
    }

    @Override
    public void execute() {
        column.getBoard().deleteColumn(positionInList);
    }

    @Override
    public void unexecute() {
        column.getBoard().addColumnAtPosition(column, positionInList);
    }

    @Override
    public String toString() {
        return "Supprimer la colonne \"" + columnTitle + "\"";
    }

}
