package edu.uw.tcss.app;

import edu.uw.tcss.model.TetrisGame;

public class SandBox {

    public static void main(final String[] theArgs) {
        TetrisGame game = new TetrisGame();
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
