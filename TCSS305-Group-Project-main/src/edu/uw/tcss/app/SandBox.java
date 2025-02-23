package edu.uw.tcss.app;

import edu.uw.tcss.model.TetrisGame;

/**
 * Sandbox class. Used to test git pulling and pushing. Nothing more, nothing less.
 *
 * @author Kassie
 * @author James
 * @author Roman
 * @author Zainab
 * @version February 2025
 */
public final class SandBox {

    private SandBox() {

    }

    /**
     * The running method to showcase the tetris API.
     *
     * @param theArgs string arguments. Not used in this main method.
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
