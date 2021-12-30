package tictactoe.board;

public enum CellValue {
    EMPTY (' '),
    X     ('X'),
    O     ('O');

    private final char value;

    private CellValue(char value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return Character.toString(value).toUpperCase();
    }
}
