package mvvm.commands;

import model.Column;

public class ColumnChangeTitle implements Command{

    private final Column column;
    private final String oldTitle;
    private final String newTitle;

    public ColumnChangeTitle(Column column, String newTitle) {
        this.column = column;
        oldTitle = column.toString();
        this.newTitle = newTitle;
    }
    @Override
    public void execute() {
        column.changeTitle(newTitle);
    }

    @Override
    public void unexecute() {
        column.changeTitle(oldTitle);
    }

    @Override
    public String toString() {
        return "Changement de titre de la colonne \"" + oldTitle + "\" vers \"" + newTitle + "\"";
    }

}
