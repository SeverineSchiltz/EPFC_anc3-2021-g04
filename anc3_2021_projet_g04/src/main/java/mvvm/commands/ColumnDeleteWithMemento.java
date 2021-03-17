package mvvm.commands;

import model.BoardMemento;
import model.Column;

public class ColumnDeleteWithMemento implements Command {

    private String columnTitle;
    private BoardMemento boardMemento;

    public ColumnDeleteWithMemento(Column column) {
        this.columnTitle = column.getTitle().getValue();
        this.boardMemento = new BoardMemento(column.getBoard());
    }
    @Override
    public void execute() {
        boardMemento.restore();
    }

    @Override
    public void unexecute() {
        boardMemento.restore();
    }

    @Override
    public String toString() {
        return "Supprimer la colonne \"" + columnTitle + "\"";
    }

}
