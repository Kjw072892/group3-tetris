package edu.uw.tcss.util;

import edu.uw.tcss.model.TetrisGame;

import javax.swing.*;
import java.awt.event.ActionEvent;

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
     * The possible tetromino controls.
     */
    public enum TetrominoControls {
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

    /**
     * The possible game controls.
     */
    public enum GameControls {
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

    /**
     * Constructs a key mapper with the given component that will listen for keystrokes
     * (as ancestor of focused component) and call action on the game based on those keystrokes
     *
     * @param theComponent the listening component
     * @param theGame the game to call actions on
     */
    public KeyMapper(final JComponent theComponent, final TetrisGame theGame) {
        myInputMap = theComponent.getInputMap(JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
        myActionMap = theComponent.getActionMap();
        myTetrisGame = theGame;

        buildActionMap();
    }

    private void buildActionMap() {
        for (final TetrominoControls control : TetrominoControls.values()) {
            myActionMap.put(control, new TetrominoAction(control));
        }
        for (final GameControls control : GameControls.values()) {
            myActionMap.put(control, new GameAction(control));
        }
    }

    /**
     * Maps a keystroke to a control for the tetromino. See the `TetrominoControls`
     * enum for possible mappings.
     *
     * @param theKeyStroke the keystroke to map from
     * @param theControl the control to map to
     */
    public void mapTetrominoAction(final KeyStroke theKeyStroke,
                                   final TetrominoControls theControl) {
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
     * Maps a keystroke to a control for the game. See the `GameControls`
     * enum for mappings.
     *
     * @param theKeyStroke the keystroke to map from
     * @param theControl the control to map to
     */
    public void mapGameAction(final KeyStroke theKeyStroke, final GameControls theControl) {
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

    // key listeners, created using abstract action
    private class TetrominoAction extends AbstractAction {

        TetrominoAction(final TetrominoControls theControlBind) {
            putValue(BIND, theControlBind);
        }

        @Override
        public void actionPerformed(final ActionEvent theEvent) {
            final TetrominoControls control = (TetrominoControls) getValue(BIND);

            switch (control) {
                case TetrominoControls.LEFT -> myTetrisGame.left();
                case TetrominoControls.RIGHT -> myTetrisGame.right();
                case TetrominoControls.DOWN -> myTetrisGame.down();
                case TetrominoControls.DROP -> myTetrisGame.drop();
                case TetrominoControls.ROTATE_CW -> myTetrisGame.rotateCW();
                case TetrominoControls.ROTATE_CCW -> myTetrisGame.rotateCCW();
                default -> throw
                        new EnumConstantNotPresentException(
                                TetrominoControls.class,
                                control.name());
            }

            System.out.println("Action: " + control);
        }
    }

    private class GameAction extends AbstractAction {

        GameAction(final GameControls theControlBind) {
            putValue(Action.NAME, "Control Bind");
            putValue(BIND, theControlBind);
        }

        @Override
        public void actionPerformed(final ActionEvent theEvent) {
            switch (getValue(BIND)) {
                case GameControls.END_GAME -> myTetrisGame.endGame();
                case GameControls.NEW_GAME -> myTetrisGame.newGame();
                case GameControls.PAUSE -> myTetrisGame.pause();
                case GameControls.UNPAUSE -> myTetrisGame.unPause();
                default -> myTetrisGame.togglePause();
            }
        }
    }
}
