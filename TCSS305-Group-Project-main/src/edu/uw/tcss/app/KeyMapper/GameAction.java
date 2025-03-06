package edu.uw.tcss.app.KeyMapper;

import edu.uw.tcss.model.TetrisGame;

import javax.swing.*;
import java.awt.event.ActionEvent;

public class GameAction extends AbstractAction {

    private final TetrisGame myTetrisGame;

    /**
     * The possible game controls.
     */
    public enum Controls {
        /** action to rotate the tetromino counter-clockwise. */
        END_GAME,
        /** action to start a new game. */
        NEW_GAME,
        /** action to pause the game. */
        PAUSE,
        /** action to unpause the game. */
        UNPAUSE,
        /** action to toggle-pause the game. */
        TOGGLE_PAUSE,
    }

    private final Controls myBind;

    GameAction(final Controls theControlBind, final TetrisGame theTetrisGame) {
        myBind = theControlBind;
        myTetrisGame = theTetrisGame;
    }

    @Override
    public void actionPerformed(final ActionEvent theEvent) {
        switch (myBind) {
            case Controls.END_GAME -> myTetrisGame.endGame();
            case Controls.NEW_GAME -> myTetrisGame.newGame();
            case Controls.PAUSE -> myTetrisGame.pause();
            case Controls.UNPAUSE -> myTetrisGame.unPause();
            case Controls.TOGGLE_PAUSE -> myTetrisGame.togglePause();
            default -> throw
                    new EnumConstantNotPresentException(
                            Controls.class,
                            myBind.name());
        }
    }
}