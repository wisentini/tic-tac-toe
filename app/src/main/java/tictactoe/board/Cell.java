package tictactoe.board;

public class Cell {
    private CellPosition position;
    private CellValue value;

    public Cell(CellPosition position, CellValue value) {
        this.position = position;
        this.value = value;
    }

    public Cell(Cell cell) {
        position = cell.position;
        value = cell.value;
    }

    public Cell(CellPosition position) {
        this.position = position;
        value = CellValue.EMPTY;
    }

    public boolean isEmpty() {
        return value == CellValue.EMPTY;
    }

    public boolean isValue(CellValue value) {
        return this.value == value;
    }

    public CellPosition getPosition() {
        return position;
    }

    public CellValue getValue() {
        return value;
    }

    public void setPosition(int row, int col) {
        this.position = new CellPosition(row, col);
    }

    public void setPosition(CellPosition position) {
        this.position = position;
    }

    public void setValue(CellValue value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return value.toString();
    }
}
