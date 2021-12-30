package tictactoe.player;

import tictactoe.board.CellValue;

public enum PlayerType {
    PLAYER_X,
    PLAYER_O;
    
    private CellValue cellValue;
    private PlayerType opponent;
    private int id;

    static {
        PLAYER_X.cellValue = CellValue.X;
        PLAYER_X.opponent = PLAYER_O;
        PLAYER_X.id = 1;
        PLAYER_O.cellValue = CellValue.O;
        PLAYER_O.opponent = PLAYER_X;
        PLAYER_O.id = 2;
    }

    public PlayerType getOpponent() {
        return opponent;
    }

    public CellValue getCellValue() {
        return cellValue;
    }

    public CellValue getOpponentsCellValue() {
        return opponent.cellValue;
    }

    public int getId() {
        return id;
    }
}
