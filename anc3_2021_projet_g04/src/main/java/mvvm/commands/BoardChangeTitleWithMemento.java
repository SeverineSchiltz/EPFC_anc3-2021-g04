package mvvm.commands;

import model.Board;
import model.BoardMemento;

public class BoardChangeTitleWithMemento implements Command {

    private Board board;
    private BoardMemento boardMemento;
    private String oldTitle;
    private String newTitle;

    public BoardChangeTitleWithMemento(Board board, String newTitle) {
        this.board = board;
        oldTitle = board.toString();
        this.newTitle = newTitle;
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
        return "Changement de titre du board \"" + oldTitle + "\" vers \"" + newTitle + "\"";
    }

}
