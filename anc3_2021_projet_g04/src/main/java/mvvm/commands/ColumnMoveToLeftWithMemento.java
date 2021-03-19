package mvvm.commands;
import model.BoardMemento;
import model.Column;

//TODO : à supprimer sinon provoque des erreurs avec les références dans les commandes
public class ColumnMoveToLeftWithMemento implements Command {

    private Column column;
    private BoardMemento boardMemento;

    public ColumnMoveToLeftWithMemento(Column column) {
        this.column = column;
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
        return "déplacement de la colonne \"" + column + "\" vers la gauche";
    }

}
