package tictactoe.board;

public class CellPosition {
    private int row;
    private int col;

    public CellPosition() {
        this.row = 0;
        this.col = 0;
    }

    public CellPosition(CellPosition cellPosition) {
        this.row = cellPosition.row;
        this.col = cellPosition.col;
    }
    
    public CellPosition(int row, int col) {
        this.row = row;
        this.col = col;
    }

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public void setCol(int col) {
        this.col = col;
    }

    @Override
    public String toString() {
        return String.format("row: %d, col: %d", row, col);
    }
}
