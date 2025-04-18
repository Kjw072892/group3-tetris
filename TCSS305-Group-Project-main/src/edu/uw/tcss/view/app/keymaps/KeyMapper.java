package edu.uw.tcss.view.app.keymaps;

import edu.uw.tcss.model.TetrisGame;
import javax.swing.ActionMap;
import javax.swing.InputMap;
import javax.swing.JComponent;
import javax.swing.KeyStroke;

/**
 * Class specifically designed to map key inputs to a JComponent
 * by abstract actions, input maps, and action maps.
 *
 * @author Roman Bureacov
 * @version 03-05-2025
 */
public final class KeyMapper {

    private final InputMap myInputMap;
    private final ActionMap myActionMap;
    private final TetrisGame myTetrisGame;

    /**
     * Constructs a key mapper with the given component that will listen for keystrokes
     * and call action on the game based on those keystrokes.
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
        for (final AudioAction.Controls control : AudioAction.Controls.values()) {
            myActionMap.put(control, new AudioAction(control));
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
    @SuppressWarnings("unused")
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
    @SuppressWarnings("unused")
    public void unmapGameAction(final KeyStroke theKeyStroke) {
        myInputMap.remove(theKeyStroke);
    }

    /**
     * Maps a keystroke to a control for the GUI. See the Controls enum inside
     * the GUIAction.
     *
     * @param theKeyStroke the keystroke tot map from
     * @param theControl the control to map to
     */
    public void mapGUIAction(final KeyStroke theKeyStroke, final AudioAction.Controls theControl) {
        myInputMap.put(theKeyStroke, theControl);
    }
}
