package model;

public class BoardMemento {

    private Board copiedBoard;
    private final Board currentBoard; // the one linked to the view

    public BoardMemento(Board board) { // board in param linked to the view => currentBoard
        // 1 board will be copied and the other one will keep the reference
        // the copied one will have a new reference (before the changes)
        this.currentBoard = board; // reflected in the view (as we see it)
        copiedBoard = new Board(currentBoard); // or new Board(copiedBoard);
    }

    public void restore() {
        currentBoard.restore(copiedBoard);
    }

}