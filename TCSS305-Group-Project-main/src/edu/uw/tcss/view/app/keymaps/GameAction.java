package edu.uw.tcss.view.app.keymaps;

import edu.uw.tcss.model.TetrisGame;
import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;

/**
 * Class that is responsible for running a game action based on a bind instantiated with.
 *
 * @author Roman Bureacov
 * @version 2025-03-08
 */
public final class GameAction extends AbstractAction {

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

    /**
     * Instantiates this action object with the bind that corresponds to a game action when action
     * performed is called.
     *
     * @param theControlBind the enum constant key
     * @param theTetrisGame the tetris game that will have actions called on
     */
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