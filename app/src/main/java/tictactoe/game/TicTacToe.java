package tictactoe.game;

import java.util.Random;

import tictactoe.board.*;
import tictactoe.player.*;

public class TicTacToe {
    private GameMode gameMode;
    private final int numOfPlayers = 2;
    private Player players[];
    private Board board;
    private Player currentPlayer;
    private int numOfMatches;

    public TicTacToe() {
        players = new Player[numOfPlayers];
        board = new Board();
        this.numOfMatches = 0;
    }

    private void setHumanPlayer(PlayerType playerType) {
        int id = playerType.getId();
        CellValue cellValue = playerType.getCellValue();

        System.out.println(String.format("\nPLAYER %d", id));
        System.out.println(String.format("    Piece: %s", cellValue));
        String name = PlayerInput.getName();

        players[id - 1] = new Player(name, id, true);
    }

    private void setMachinePlayer(String name, PlayerType playerType) {
        int id = playerType.getId();

        players[id - 1] = new Player(name, id, false);
    }

    private void setFirstPlayer() {
        assert(players.length == 2);
        currentPlayer = players[randomInteger(0, 1)];
    }

    private void initialSettings() {
        if (numOfMatches != 0) {
            board.clean();
        }

        if (gameMode == GameMode.HUMAN_VS_HUMAN) {
            setHumanPlayer(PlayerType.PLAYER_X);
            setHumanPlayer(PlayerType.PLAYER_O);
        } else if (gameMode == GameMode.HUMAN_VS_MACHINE) {
            setHumanPlayer(PlayerType.PLAYER_X);
            setMachinePlayer("Machine", PlayerType.PLAYER_O);
        } else {
            setMachinePlayer("Machine 1", PlayerType.PLAYER_X);
            setMachinePlayer("Machine 2", PlayerType.PLAYER_O);
        }

        setFirstPlayer();
    }

    private void humanVersusHumanGameplay() {
        while (true) {
            showCurrentPlayer();
            board.draw();
            humanMove();
            if (isFinished()) break;
            switchPlayers();
        }
    }

    private void humanVersusMachineGameplay() {
        while (true) {
            if (currentPlayer.getIsHuman()) {
                showCurrentPlayer();
            } else {
                sleep(randomInteger(1, 3), "\nWaiting for machine's move...");
            }

            board.draw();

            if (currentPlayer.getIsHuman()) {
                humanMove();
            } else {
                machineMove(true);
            }

            if (isFinished()) break;

            switchPlayers();
        }
    }

    private void machineVersusMachineGameplay() {
        while (true) {
            String message = String.format("\nWaiting for %s's move...", currentPlayer.getName());
            sleep(randomInteger(1, 3), message);
            machineMove(true);
            board.draw();
            if (isFinished()) break;
            switchPlayers();
        }
    }

    public void play() {
        do {
            gameMode = PlayerInput.chooseGameMode();
            numOfMatches++;
            initialSettings();

            if (gameMode == GameMode.HUMAN_VS_HUMAN) {
                humanVersusHumanGameplay();
            } else if (gameMode == GameMode.HUMAN_VS_MACHINE) {
                humanVersusMachineGameplay();
            } else {
                machineVersusMachineGameplay();
            }

            board.draw();
        } while (PlayerInput.playAgain());

        PlayerInput.closeScanner();
    }

    private int checkWinner() {
        for (Player player : players) {
            if (board.checkWin(player.getCellValue())) {
                return player.getId();
            }
        }

        return 0;
    }

    private boolean isFinished() {
        GameStatus gameStatus = getGameStatus();

        if (gameStatus == GameStatus.RUNNING) {
            return false;
        } else if (gameStatus == GameStatus.TIE) {
            showTieMessage();
        } else {
            showWinner();
        }

        return true;
    }

    private void showWinner() {
        String name = this.currentPlayer.getName();
        CellValue cellValue = this.currentPlayer.getCellValue();
        int id = this.currentPlayer.getId();

        System.out.println(String.format("\nPLAYER %d (%s, %s) WON!", id, name, cellValue));
    }

    private void showTieMessage() {
        System.out.println("\n    IT'S A TIE :(");
    }

    private void showCurrentPlayer() {
        String name = this.currentPlayer.getName();
        CellValue cellValue = this.currentPlayer.getCellValue();
        int id = this.currentPlayer.getId();

        System.out.println(String.format("\nIt's player %d's (%s, %s) turn!", id, name, cellValue));
    }

    private void humanMove() {
        while (true) {
            CellPosition position = PlayerInput.getMove();
            CellValue cellValue = currentPlayer.getCellValue();

            if (board.emptyAt(position)) {
                board.setCellValueAt(cellValue, position);
                break;
            } else {
                System.out.println(String.format("Error: cell at %s is already set.", position));
            }
        }
    }

    private GameStatus getGameStatus() {
        int winnerId = checkWinner();

        if (winnerId == 0) {
            if (board.isFull()) {
                return GameStatus.TIE;
            } else {
                return GameStatus.RUNNING;
            }
        } else {
            return GameStatus.values()[winnerId];
        }
    }

    private int minimax(Board board, PlayerType playerType, boolean isMaximizing) {
        GameStatus gameStatus = getGameStatus();
        int bestScore = gameStatus.getScore(isMaximizing);
        
        if (gameStatus == GameStatus.RUNNING) {
            if (isMaximizing) {
                bestScore = Integer.MIN_VALUE;
    
                for (CellPosition position : board.getAvailableCellPositions()) {
                    board.setCellValueAt(playerType.getCellValue(), position);
                    int score = minimax(board, playerType.getOpponent(), false);
                    board.setCellValueAt(CellValue.EMPTY, position);
                    bestScore = Math.max(score, bestScore);
                }
            } else {
                bestScore = Integer.MAX_VALUE;
    
                for (CellPosition position : board.getAvailableCellPositions()) {
                    board.setCellValueAt(playerType.getCellValue(), position);
                    int score = minimax(board, playerType.getOpponent(), true);
                    board.setCellValueAt(CellValue.EMPTY, position);
                    bestScore = Math.min(score, bestScore);
                }
            }
        }

        return bestScore;
    }

    private void machineMove(boolean isMaximizing) {
        CellPosition bestPosition = new CellPosition();
        int bestScore = (isMaximizing) ? Integer.MIN_VALUE : Integer.MAX_VALUE;
        CellValue cellValue = currentPlayer.getCellValue();

        for (CellPosition position : board.getAvailableCellPositions()) {
            board.setCellValueAt(cellValue, position);
            int score = minimax(board, currentPlayer.getType().getOpponent(), !isMaximizing);
            board.setCellValueAt(CellValue.EMPTY, position);
            
            if (score > bestScore) {
                bestScore = score;
                bestPosition = position;
            }
        }

        board.setCellValueAt(cellValue, bestPosition);
    }

    private void switchPlayers() {
        currentPlayer = players[numOfPlayers - currentPlayer.getId()];
    }

    private int randomInteger(int min, int max) {
        return new Random().nextInt(min, max + 1);
    }

    private void sleep(int seconds, String message) {
        try {
            System.out.println(message);
            Thread.sleep(seconds * 1000);
        } catch (InterruptedException ie) {
            ie.printStackTrace();
        }
    }   
}
