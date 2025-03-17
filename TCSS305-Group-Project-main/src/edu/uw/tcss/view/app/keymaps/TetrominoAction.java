package edu.uw.tcss.view.app.keymaps;

import static edu.uw.tcss.view.util.AudioFXManager.Channels;

import edu.uw.tcss.model.TetrisGame;
import edu.uw.tcss.view.util.AudioFXManager;
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
                AudioFXManager.playSoundFX(Channels.CHANGED_POSITION_FX);
                myTetrisGame.left();
            }
            case Controls.RIGHT -> {
                AudioFXManager.playSoundFX(Channels.CHANGED_POSITION_FX);
                myTetrisGame.right();
            }
            case Controls.DOWN -> {
                AudioFXManager.playSoundFX(Channels.CHANGED_POSITION_FX);
                myTetrisGame.down();
            }
            case Controls.DROP -> {
                AudioFXManager.playSoundFX(Channels.SOFT_DROP_FX);
                myTetrisGame.drop();
            }
            case Controls.ROTATE_CW -> {
                AudioFXManager.playSoundFX(Channels.ROTATE_FX);
                myTetrisGame.rotateCW();
            }
            case Controls.ROTATE_CCW -> {
                AudioFXManager.playSoundFX(Channels.ROTATE_FX);
                myTetrisGame.rotateCCW();
            }
            default -> throw
                    new EnumConstantNotPresentException(
                            Controls.class,
                            myBind.name());
        }
    }
}