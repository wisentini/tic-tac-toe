package tictactoe.game;

public enum GameStatus {
    RUNNING  (0),
    X_WIN   (10),
    O_WIN   (10),
    TIE      (0);

    private final int score;

    private GameStatus(int score) {
        this.score = score;
    }

    public int getScore() {
        return score;
    }

    public int getScore(boolean isMaximizing) {
        return isMaximizing ? -score : score;
    }
}
