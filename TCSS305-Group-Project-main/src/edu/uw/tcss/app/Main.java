package edu.uw.tcss.app;
import edu.uw.tcss.model.TetrisGame;
import java.util.Scanner;

/**
 * Group Project 3 Tetris.
 *
 * @author James 
 * @author Kassie 
 * @author Roman 
 * @author Zainab
 * @version 2.21.25
 */
public final class Main {

    private Main() {

    }

    /**
     * Ensures the console is only focusing on current game frame.
     */
    public static void clearConsole() {
        final int thirtyLines = 30;
        for (int i = 0; i < thirtyLines; i++) {
            System.out.println(" ");
        }
    }

    /**
     * The main method initialized the game, takes user input in a loop.
     * Updates the game state. The loop continues until the user Quits the game.
     *
     * @param theArgs (unused)
     */
    public static void main(final String[] theArgs) {


        //boolean to control game.
        boolean isRunning = true;

        //boolean to indicate that the game is paused.
        boolean isPaused = false;

        //Scanner to get user input.
        final Scanner scanner = new Scanner(System.in);

        //Instantiate the TetrisGame with the board dimensions.
        final TetrisGame game = new TetrisGame(20, 20);

        // Starts a new game by initializing the game state.
        game.newGame();

        // Main game loop: runs continuously until isRunning becomes false.
        while (isRunning) {

            // Clear the console for a refreshed display.
            clearConsole();

            // Display the current state of the game.
            System.out.println(game);

            if (isPaused) {
                System.out.println("The game is paused");
            }

            // Display the available commands.
            System.out.println("A: move left, D: move right, S: move down,"
                    + " Quit: Quit, Q: Rotate Counter ClockWise, "
                    + "E: Rotate C ClockWise, P: Pause Game");

            // Stores users input into a variable. and converts to uppercase.
            final String userInput = scanner.nextLine().toUpperCase();

            // Processes the command using a switch expression.
            switch (userInput) {
                case "A" -> game.left();  // Move the piece left.

                case "D" -> game.right();  // Move the piece right.

                case "S" -> game.down();   // Move the piece down.

                case "Q" -> game.rotateCCW(); // Rotates the pieces counterclockwise.

                case "E" -> game.rotateCW(); // Rotates the pieces clockwise.

                case "P" -> {
                    game.togglePause(); // Toggle pause state.
                    isPaused = !isPaused;
                }

                case "QUIT" -> isRunning = false; // Quits the game, Exits the game loop

                default -> System.out.println("Invalid input");

            }
            // Moves the game piece down after every movement call.
            game.down();

        }
        // End the game by calling endGame().
        game.endGame();

        // Close the scanner.
        scanner.close();

    }
}
