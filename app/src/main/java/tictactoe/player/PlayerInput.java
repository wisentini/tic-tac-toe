package tictactoe.player;

import java.util.Scanner;

import tictactoe.board.CellPosition;
import tictactoe.game.GameMode;

public class PlayerInput {
    private static final Scanner scanner = new Scanner(System.in);

    public static int getInteger(String message, int min, int max) {
        while (true) {
            System.out.print(message);

            String input = scanner.nextLine();

            if (input.isBlank() || input.isEmpty()) {
                System.out.println("\nError: input cannot be blank or empty.");
            } else {
                int integer;

                try {
                    integer = Integer.parseInt(input);    
                } catch (NumberFormatException nfe) {
                    System.out.println("\nError: input must be an integer.");
                    continue;
                }

                if (integer < min || integer > max) {
                    System.out.println(String.format("\nError: input must be in range [%d, %d].", min, max));
                } else {
                    return integer;
                }
            }
        }
    }

    public static String getName() {
        while (true) {
            System.out.print("     Name: ");

            String name = scanner.nextLine();

            if (name.isBlank() || name.isEmpty()) {
                System.out.println("\nError: name cannot be blank or empty.");
            } else if (name.length() > 30) {
                System.out.println("\nError: name cannot exceed 30 characters.");
            } else {
                return name;
            }
        }
    }

    public static CellPosition getMove() {
        while (true) {
            System.out.print("\n    Move (e.g., C2): ");

            String input = scanner.nextLine();

            if (input.isBlank() || input.isEmpty()) {
                System.out.println("\nError: move cannot be blank or empty.");
            } else if (input.length() != 2) {
                System.out.println("\nError: move must have exactly 2 characters.");
            } else if (!Character.isLetter(input.charAt(0))) {
                System.out.println("\nError: move's column (first character) must be a letter.");
            } else if (!Character.isDigit(input.charAt(1))) {
                System.out.println("\nError: move's row (second character) must be a number.");
            } else {
                int row = input.charAt(1) - '0' - 1;
                int col = Character.toLowerCase(input.charAt(0)) - 'a';

                if (col < 0 || col >= 3) {
                    System.out.println(String.format("\nError: move's column (first character) must be 'A', 'B' or 'C'."));
                } else if (row < 0 || row >= 3) {
                    System.out.println(String.format("\nError: move's row (second character) must be in range [1, 3]."));
                } else {
                    return new CellPosition(row, col);
                }
            }
        }
    }

    public static GameMode chooseGameMode() {
        System.out.println("\nGAME MODES:");
        System.out.println("    1. Human VS Human");
        System.out.println("    2. Human VS Machine");
        System.out.println("    3. Machine VS Machine");

        int chosenGameMode = getInteger("\n    Choose a game mode: ", 1, 3);

        return GameMode.values()[chosenGameMode - 1];
    }

    public static boolean playAgain() {
        while (true) {
            System.out.println("\nDo you want to play again? [y/n]");

            String choice = scanner.nextLine().toLowerCase();

            if (choice.isBlank() || choice.isEmpty()) {
                System.out.println("\nError: choice cannot be blank or empty.");
            } else if (!choice.equals("y") && !choice.equals("n")) {
                System.out.println("\nError: choice must be 'y' (yes) or 'n' (no).");
            } else {
                return choice.equals("y");
            }
        }
    }

    public static void closeScanner() {
        scanner.close();
    }
}
