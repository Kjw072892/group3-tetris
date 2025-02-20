package edu.uw.tcss.app;

import edu.uw.tcss.model.TetrisGame;

/**
 * stub doc
 *
 */
public class SandBox {

    private SandBox() {

    }

    /**
     * stub doc
     *
     * @param theArgs
     */
    public static void main(final String[] theArgs) {
        final TetrisGame game = new TetrisGame();
        game.newGame();
        System.out.println(game);

        // James Makes A comment.
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
