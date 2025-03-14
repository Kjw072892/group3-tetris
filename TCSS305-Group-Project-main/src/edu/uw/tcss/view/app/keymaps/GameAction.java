package edu.uw.tcss.view.app.keymaps;

import static edu.uw.tcss.view.util.AudioFXManager.Channels;

import edu.uw.tcss.model.TetrisGame;
import edu.uw.tcss.view.util.AudioFXManager;
import java.awt.event.ActionEvent;
import java.util.Map;
import javax.swing.AbstractAction;

/**
 * Class that is responsible for running a game action based on a bind instantiated with.
 *
 * @author Roman Bureacov
 * @version 2025-03-08
 */
public final class GameAction extends AbstractAction {

    private final TetrisGame myTetrisGame;
    private final Map<Controls, Runnable> myKeyActions;

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

        TOGGLE_PINK_MODE,
        /** action to toggle-pause the game. */
        TOGGLE_PAUSE
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
        
        myKeyActions = getBinds();
    }
    
    private Map<Controls, Runnable> getBinds() {
        return Map.of(
                Controls.END_GAME, myTetrisGame::endGame,
                Controls.NEW_GAME, myTetrisGame::newGame,
                Controls.PAUSE, myTetrisGame::down,
                Controls.UNPAUSE, myTetrisGame::unPause,
                Controls.TOGGLE_PAUSE, myTetrisGame::togglePause
        );
    }

    @Override
    public void actionPerformed(final ActionEvent theEvent) {
        final Runnable action = myKeyActions.get(myBind);
        if (action != null) {
            action.run();
        } else {
            throw new EnumConstantNotPresentException(Controls.class, myBind.name());
        }
    }
}