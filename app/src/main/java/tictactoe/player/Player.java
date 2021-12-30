package tictactoe.player;

import tictactoe.board.CellValue;

public class Player {
    private String name;
    private PlayerType type;
    private boolean isHuman;

    public Player(String name, PlayerType type, boolean isHuman) {
        this.name = name;
        this.type = type;
        this.isHuman = isHuman;
    }

    public Player(String name, int id, boolean isHuman) {
        this.name = name;
        this.type = (id == 1) ? PlayerType.PLAYER_X : PlayerType.PLAYER_O;
        this.isHuman = isHuman;
    }

    public String getName() {
        return name;
    }

    public int getId() {
        return type.getId();
    }

    public PlayerType getType() {
        return type;
    }

    public CellValue getOpponentsCellValue() {
        return type.getOpponentsCellValue();
    }

    public boolean getIsHuman() {
        return isHuman;
    }

    public CellValue getCellValue() {
        return type.getCellValue();
    }

    public void setName(String name) {
        this.name = name;
    }
}
