package edu.uw.tcss.view.app;

import static edu.uw.tcss.model.PropertyChangeEnabledGameControls.PROPERTY_FROZEN_BLOCKS;
import static edu.uw.tcss.model.PropertyChangeEnabledGameControls.PROPERTY_ROWS_CLEARED;
import static edu.uw.tcss.model.PropertyChangeEnabledGameControls.PROPERTY_GAME_STATE;
import static edu.uw.tcss.model.GameControls.GameState;

import edu.uw.tcss.model.TetrisGame;
import edu.uw.tcss.view.util.AudioFXManager;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import javax.swing.Timer;

/**
 * Class that handles some game logic, specifically how the game is scored,
 * calling the steps, and advancing levels.
 *
 * @author Roman Bureacov
 * @author Kassie Whitney
 * @version 2025-03-08
 */
public final class GameLogic implements PropertyChangeListener {

    private static final int ONE_LINE_SCORE = 40;
    private static final int TWO_LINE_SCORE = 100;
    private static final int THREE_LINE_SCORE = 300;
    private static final int FOUR_LINE_SCORE = 1200;
    private static final int LINES_PER_SCORE = 5;
    private static final int SCORE_PER_PIECE = 4;
    private static final int THREE_LINES_CLEARED = 3;
    private static final int FOUR_LINES_CLEARED = 4;

    /** Default millisecond delay of the timer */
    private static final int DEFAULT_DELAY = 1000;
    private static final int DELAY_DECREMENT = 100;

    private final Timer myTimer;
    private final TetrisGame myTetrisGame;
    private int myCurrentLevel = 1;
    private int myScore;
    private int myLinesCleared;
    private GameState myLastGameState = GameState.OVER;

    GameLogic(final TetrisGame theTetrisGame) {
        myTetrisGame = theTetrisGame;
        myTimer = new Timer(DEFAULT_DELAY, theEvent -> myTetrisGame.step());
    }

    @Override
    public void propertyChange(final PropertyChangeEvent theEvent) {
        if (PROPERTY_ROWS_CLEARED.equals(theEvent.getPropertyName())) {
            final Integer rowsCleared = (Integer) theEvent.getNewValue();

            myLinesCleared += rowsCleared;

            updateLevel();
            updateScore(rowsCleared);

        } else if (PROPERTY_FROZEN_BLOCKS.equals(theEvent.getPropertyName())) {
            if (!GameState.NEW.equals(myLastGameState)) {
                myScore += SCORE_PER_PIECE;
            }
        } else if (PROPERTY_GAME_STATE.equals(theEvent.getPropertyName())) {
            final GameState newGameState =
                    (GameState) theEvent.getNewValue();

            switch (newGameState) {
                case GameState.NEW -> {
                    myScore = 0;
                    myLinesCleared = 0;
                    myCurrentLevel = 1;

                    myTimer.setDelay(DEFAULT_DELAY);
                }
                case GameState.PAUSED,
                     GameState.OVER -> myTimer.stop();
                case GameState.RUNNING -> {
                    if (!GameState.RUNNING.equals(myLastGameState)) {
                        myTimer.start();
                    }
                }
                default -> throw
                        new EnumConstantNotPresentException(
                                GameState.class, String.valueOf(newGameState));
            }

            myLastGameState = newGameState;
        }

    }

    private void playLinesFX(final int theNumLinesCleared) {
        if (theNumLinesCleared == THREE_LINES_CLEARED) {
            AudioFXManager.playSoundFX(AudioFXManager.Channels.THREE_LINES_FX);

        } else if (theNumLinesCleared == FOUR_LINES_CLEARED) {

            AudioFXManager.playSoundFX(AudioFXManager.Channels.FOUR_LINES_FX);
        }
    }

    /**
     * Method to get the current level dictated by the game logic.
     *
     * @return the current level of the game.
     */
    public int getLevel() {
        return myCurrentLevel;
    }

    private void updateLevel() {
        final int newLevel = myLinesCleared / LINES_PER_SCORE + 1;

        if (newLevel > myCurrentLevel) {
            myTimer.setDelay(DEFAULT_DELAY - myCurrentLevel * DELAY_DECREMENT);

            AudioFXManager.playSoundFX(AudioFXManager.Channels.NEW_LEVEL_FX);
        }

        myCurrentLevel = newLevel;
    }

    /**
     * Method to get the current score dictated by the game logic.
     *
     * @return the current score of the game.
     */
    public int getScore() {
        return myScore;
    }

    private void updateScore(final int theLinesCleared) {

        final int oneLine = 1;
        final int twoLine = 2;
        final int threeLine = 3;
        final int fourLine = 4;

        myScore += switch (theLinesCleared) {
            case oneLine -> ONE_LINE_SCORE * myCurrentLevel;
            case twoLine -> TWO_LINE_SCORE * myCurrentLevel;
            case threeLine -> THREE_LINE_SCORE * myCurrentLevel;
            case fourLine -> FOUR_LINE_SCORE * myCurrentLevel;
            default -> 0;
        };
        playLinesFX(theLinesCleared);

    }

    /**
     * Gets the current number of lines cleared in the tetris game.
     *
     * @return the number of lines cleared in the tetris game thus far.
     */
    public int getLinesCleared() {
        return myLinesCleared;
    }

    /**
     * Gets the last remembered game state.
     *
     * @return last remembered game state.
     */
    public GameState getLastGameState() {
        return myLastGameState;
    }
}