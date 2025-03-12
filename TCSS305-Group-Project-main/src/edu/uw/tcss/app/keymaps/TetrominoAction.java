package edu.uw.tcss.app.keymaps;

import edu.uw.tcss.model.TetrisGame;
import edu.uw.tcss.util.AudioManagerFX;
import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;


/**
 * Class that is responsible for running a tetromino
 * action based on a bind instantiated with.
 *
 * @author Roman Bureacov
 * @version 2025-03-08
 */
public final class TetrominoAction extends AbstractAction {

    private static final String MOVED = "moved";

    private static final String ROTATED = "rotated";

    private static final String SOFT_DROPPED = "soft_dropped";

    private final TetrisGame myTetrisGame;

    /**
     * The possible tetromino controls.
     */
    public enum Controls {
        /**
         * action to move the tetromino left.
         */
        LEFT,
        /**
         * action to move the tetromino right.
         */
        RIGHT,
        /**
         * action to move the tetromino down.
         */
        DOWN,
        /**
         * action to move the tetromino down until it freezes.
         */
        DROP,
        /**
         * action to rotate the tetromino clockwise.
         */
        ROTATE_CW,
        /**
         * action to rotate the tetromino counter-clockwise.
         */
        ROTATE_CCW,
    }

    private final Controls myBind;

    /**
     * Instantiates this action object with the bind that corresponds to a tetromino
     * action when action performed is called.
     *
     * @param theControlBind the enum constant key
     * @param theTetrisGame the tetris game that will have actions called on
     */
    TetrominoAction(final Controls theControlBind, final TetrisGame theTetrisGame) {
        myBind = theControlBind;
        myTetrisGame = theTetrisGame;
    }

    @Override
    public void actionPerformed(final ActionEvent theEvent) {
        switch (myBind) {
            case Controls.LEFT -> {
                myTetrisGame.left();
                AudioManagerFX.playFX(MOVED);
            }
            case Controls.RIGHT -> {
                myTetrisGame.right();
                AudioManagerFX.playFX(MOVED);
            }

            case Controls.DOWN -> {
                myTetrisGame.down();
                AudioManagerFX.playFX(MOVED);
            }
            case Controls.DROP -> {
                myTetrisGame.drop();
                AudioManagerFX.playFX(SOFT_DROPPED);
            }

            case Controls.ROTATE_CW -> {
                myTetrisGame.rotateCW();
                AudioManagerFX.playFX(ROTATED);
            }

            case Controls.ROTATE_CCW -> {
                myTetrisGame.rotateCCW();
                AudioManagerFX.playFX(ROTATED);
            }

            default -> throw
                    new EnumConstantNotPresentException(
                            Controls.class,
                            myBind.name());
        }
    }
}