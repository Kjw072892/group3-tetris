package edu.uw.tcss.util;

import edu.uw.tcss.model.TetrisGame;

import javax.swing.*;
import java.awt.event.ActionEvent;

public class TetrominoAction extends AbstractAction {

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

    TetrominoAction(final Controls theControlBind, final TetrisGame theTetrisGame) {
        myBind = theControlBind;
        myTetrisGame = theTetrisGame;
    }

    @Override
    public void actionPerformed(final ActionEvent theEvent) {
        switch (myBind) {
            case Controls.LEFT -> myTetrisGame.left();
            case Controls.RIGHT -> myTetrisGame.right();
            case Controls.DOWN -> myTetrisGame.down();
            case Controls.DROP -> myTetrisGame.drop();
            case Controls.ROTATE_CW -> myTetrisGame.rotateCW();
            case Controls.ROTATE_CCW -> myTetrisGame.rotateCCW();
            default -> throw
                    new EnumConstantNotPresentException(
                            Controls.class,
                            myBind.name());
        }
    }
}