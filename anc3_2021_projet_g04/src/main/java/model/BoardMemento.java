package model;

public class BoardMemento {

    private Board copiedBoard;

    public BoardMemento(Board board) {
        copiedBoard = new Board(board);
    }

    Board getCopiedBoard() {
        return copiedBoard;
    }
}
