package edu.uw.tcss.app;

import edu.uw.tcss.model.TetrisGame;

/**
 * SandBox Class
 *
 * @author James, Kassie, Roman, Zainab
 * @version 02.20.25
 */
public class SandBox {
    private SandBox(){}

    /**
     * Main method that prints the tetris game board and pieces to the console.
     */
    public static void main(final String[] theArgs) {


        final TetrisGame game = new TetrisGame();
        game.newGame();

        System.out.println(game);
        game.step();
        System.out.println(game);
        game.step();
        System.out.println(game);
        game.step();
        System.out.println(game);
        game.rotateCW();
        System.out.println(game);
        game.rotateCW();
        System.out.println(game);
        game.rotateCW();
        System.out.println(game);
        game.rotateCW();
        System.out.println(game);
        game.drop();
        System.out.println(game);

    }

}
