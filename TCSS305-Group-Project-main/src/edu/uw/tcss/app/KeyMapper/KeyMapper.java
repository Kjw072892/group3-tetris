package edu.uw.tcss.app.KeyMapper;

import edu.uw.tcss.model.TetrisGame;

import javax.swing.*;

/**
 * Class specifically designed to map key inputs to a JComponent
 * by abstract actions, input maps, and action maps.
 *
 * @author Roman Bureacov
 * @version 03-05-2025
 */
public final class KeyMapper {
    private static final  String BIND = "bind";

    private final InputMap myInputMap;
    private final ActionMap myActionMap;
    private final TetrisGame myTetrisGame;

    /**
     * Constructs a key mapper with the given component that will listen for keystrokes
     * (as ancestor of focused component) and call action on the game based on those keystrokes.
     *
     * @param theComponent the listening component
     * @param theGame the game to call actions on
     */
    public KeyMapper(final JComponent theComponent, final TetrisGame theGame) {
        myInputMap = theComponent.getInputMap(JComponent.WHEN_FOCUSED);
        myActionMap = theComponent.getActionMap();
        myTetrisGame = theGame;

        buildActionMap();
    }

    private void buildActionMap() {
        for (final TetrominoAction.Controls control : TetrominoAction.Controls.values()) {
            myActionMap.put(control, new TetrominoAction(control, myTetrisGame));
        }
        for (final GameAction.Controls control : GameAction.Controls.values()) {
            myActionMap.put(control, new GameAction(control, myTetrisGame));
        }
    }

    /**
     * Maps a keystroke to a control for the tetromino. See the Controls enum inside
     * the TetrominoAction class for possible mappings.
     *
     * @param theKeyStroke the keystroke to map from
     * @param theControl the control to map to
     */
    public void mapTetrominoAction(final KeyStroke theKeyStroke,
                                   final TetrominoAction.Controls theControl) {
        myInputMap.put(theKeyStroke, theControl);
    }

    /**
     * Unmaps the specified keystroke.
     *
     * @param theKeyStroke the keystroke to unmap
     */
    public void unmapTetrominoAction(final KeyStroke theKeyStroke) {
        myInputMap.remove(theKeyStroke);
    }

    /**
     * Maps a keystroke to a control for the game. See the Controls enum inside
     * the GameControl class for possible mappings.
     *
     * @param theKeyStroke the keystroke to map from
     * @param theControl the control to map to
     */
    public void mapGameAction(final KeyStroke theKeyStroke, final GameAction.Controls theControl) {
        myInputMap.put(theKeyStroke, theControl);
    }

    /**
     * Unmaps the specified keystroke.
     *
     * @param theKeyStroke the keystroke to unmap
     */
    public void unmapGameAction(final KeyStroke theKeyStroke) {
        myInputMap.remove(theKeyStroke);
    }
}
