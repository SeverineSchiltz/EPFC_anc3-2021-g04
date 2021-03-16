package mvvm.commands;

import model.Board;
import model.BoardMemento;

public class BoardChangeTitleWithMemento implements Command {

    private Board board;
    private BoardMemento boardMemento;

    public BoardChangeTitleWithMemento(Board board) {
        this.board = board;
        this.boardMemento = new BoardMemento(board);
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
        return "Changement de titre de la colonne \"" + board.getTitle() + "\" vers \"" + board.getTitle() + "\"";
    }

}
