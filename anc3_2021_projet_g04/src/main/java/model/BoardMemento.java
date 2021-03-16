package model;

public class BoardMemento {

    // Question: 2 boards? reference & board to restore

    private Board copiedBoard;
    private Board currentBoard; // the one linked to the view

    public BoardMemento(Board board) { // board in param linked to the view => currentBoard
        // 1 board will be copied and the other one will keep the reference
        // the copied one will have a new reference (before the changes)
        this.currentBoard = board; // reflected in the view (as we see it)
        copiedBoard = new Board(currentBoard); // or new Board(copiedBoard);
    }


    public void restore() {
        Board tmp = new Board(currentBoard);
        currentBoard.restore(copiedBoard);
        copiedBoard = tmp;
    }

}
