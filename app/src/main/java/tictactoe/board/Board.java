package tictactoe.board;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Board {
    private final int numOfRows = 3;
    private final int numOfCols = 3;
    private Cell cells[][];

    public Board() {
        cells = new Cell[numOfRows][numOfCols];
        init(CellValue.EMPTY);
    }

    private void init(CellValue value) {
        for (int row = 0; row < numOfRows; row++) {
            for (int col = 0; col < numOfCols; col++) {
                CellPosition position = new CellPosition(row, col);
                cells[row][col] = new Cell(position, value);
            }
        }
    }

    public void clean() {
        init(CellValue.EMPTY);
    }

    public boolean isFull() {
        for (Cell[] row : cells) {
            for (Cell cell : row) {
                if (cell.isEmpty()) {
                    return false;
                }
            }
        }

        return true;
    }

    public boolean isEmpty() {
        for (Cell[] row : cells) {
            for (Cell cell : row) {
                if (!cell.isEmpty()) {
                    return false;
                }
            }
        }

        return true;
    }

    private void drawUpperLine() {
        System.out.println();
        System.out.println("        A   B   C");
        System.out.print("      ┌");

        for (int col = 0; col < numOfCols - 1; col++) {
            System.out.print("───┬");
        }

        System.out.println("───┐");
    }

    private void drawMiddleLine() {
        System.out.println();
        System.out.print("      ├");

        for (int col = 0; col < numOfCols - 1; col++) {
            System.out.print("───┼");
        }

        System.out.println("───┤");
    }

    private void drawLowerLine() {
        System.out.println();
        System.out.print("      └");

        for (int col = 0; col < numOfCols - 1; col++) {
            System.out.print("───┴");
        }

        System.out.println("───┘");
    }

    public void draw() {
        drawUpperLine();

        for (int row = 0; row < numOfRows; row++) {
            System.out.print("    " + (row + 1) + " │");

            for (int col = 0; col < numOfCols; col++) {
                System.out.print(" " + cells[row][col] + " │");
            }
            
            if (row < numOfRows - 1) {
                drawMiddleLine();
            }
        }

        drawLowerLine();
    }

    private boolean checkRow(CellValue value) {
        for (int row = 0; row < numOfRows; row++) {
            int col;

            for (col = 0; col < numOfCols; col++) {
                if (!cells[row][col].isValue(value)) {
                    break;
                }
            }

            if (col == numOfCols) return true;
        }
    
        return false;
    }

    private boolean checkCol(CellValue value) {
        for (int col = 0; col < numOfCols; col++) {
            int row;

            for (row = 0; row < numOfRows; row++) {
                if (!cells[row][col].isValue(value)) {
                    break;
                }
            }

            if (row == numOfRows) return true;
        }
    
        return false;
    }

    private boolean checkMainDiag(CellValue value) {
        for (int i = 0; i < numOfRows; i++) {
            if (!cells[i][i].isValue(value)) {
                return false;
            }
        }

        return true;
    }

    private boolean checkSecDiag(CellValue value) {
        int col = numOfCols - 1;

        for (int row = 0; row < numOfRows; row++) {
            if (!cells[row][col].isValue(value)) {
                return false;
            }

            col--;
        }

        return true;
    }

    public boolean checkWin(CellValue value) {
        return checkRow(value) || checkCol(value) || checkMainDiag(value) || checkSecDiag(value);
    }

    public boolean checkTie(CellValue value) {
        return isFull() && !checkWin(value);
    }

    public Cell getCellAt(int row, int col) {
        assert(row >= 0 && row < numOfRows && col >= 0 && col < numOfCols);
        return cells[row][col];
    }

    public Cell getCellAt(CellPosition position) {
        int row = position.getRow();
        int col = position.getCol();

        assert(row >= 0 && row < numOfRows && col >= 0 && col < numOfCols);
        return cells[row][col];
    }

    public CellValue getCellValueAt(int row, int col) {
        return getCellAt(row, col).getValue();
    }

    public CellValue getCellValueAt(CellPosition position) {
        return getCellAt(position).getValue();
    }

    public void setCellValueAt(CellValue value, int row, int col) {
        assert(row >= 0 && row < numOfRows && col >= 0 && col < numOfCols);
        cells[row][col].setValue(value);
    }

    public void setCellValueAt(CellValue value, CellPosition position) {
        int row = position.getRow();
        int col = position.getCol();
        
        assert(row >= 0 && row < numOfRows && col >= 0 && col < numOfCols);
        cells[row][col].setValue(value);;
    }

    public boolean emptyAt(int row, int col) {
        assert(row >= 0 && row < numOfRows && col >= 0 && col < numOfCols);
        return cells[row][col].isEmpty();
    }

    public boolean emptyAt(CellPosition position) {
        return getCellAt(position).isEmpty();
    }

    public int getNumOfRows() {
        return numOfRows;
    }
    
    public int getNumOfCols() {
        return numOfCols;
    }

    public List<Cell> getAvailableCells() {
        List<Cell> availableCells = new ArrayList<Cell>();

        for (int row = 0; row < numOfRows; row++) {
            for (int col = 0; col < numOfCols; col++) {
                Cell cell = cells[row][col];

                if (cell.isEmpty()) {
                    availableCells.add(cell);
                }
            }
        }

        Collections.shuffle(availableCells);
        return availableCells;
    }

    public List<CellPosition> getAvailableCellPositions() {
        List<CellPosition> availableCellPositions = new ArrayList<CellPosition>();

        for (int row = 0; row < numOfRows; row++) {
            for (int col = 0; col < numOfCols; col++) {
                Cell cell = cells[row][col];

                if (cell.isEmpty()) {
                    availableCellPositions.add(cell.getPosition());
                }
            }
        }

        //Collections.shuffle(availableCellPositions);
        return availableCellPositions;
    }
}
