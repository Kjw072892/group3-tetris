package edu.uw.tcss.app;

import static edu.uw.tcss.model.PropertyChangeEnabledGameControls.PROPERTY_ROWS_CLEARED;
import static edu.uw.tcss.model.PropertyChangeEnabledGameControls.PROPERTY_GAME_STATE;

import edu.uw.tcss.model.GameControls;
import edu.uw.tcss.model.TetrisGame;
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
final class GameLogic implements PropertyChangeListener {

    private static final int SCORE_PER_ROW_CLEARED = 100;
    private static final int SCORE_PER_ADD_ROW = 50;
    private static final int LINES_PER_LEVEL = 5;

    /** Default millisecond delay of the timer */
    private static final int DEFAULT_DELAY = 1000;
    private static final int DELAY_DECREMENT = 25;

    private final Timer myTimer;
    private final TetrisGame myTetrisGame;
    private int myCurrentLevel = 1;
    private int myScore;
    private int myLinesCleared;
    private GameControls.GameState myLastGameState = GameControls.GameState.NEW;

    GameLogic(final TetrisGame theTetrisGame) {
        myTetrisGame = theTetrisGame;
        myTimer = new Timer(DEFAULT_DELAY, theEvent -> myTetrisGame.step());
    }

    @Override
    public void propertyChange(final PropertyChangeEvent theEvent) {
        if (theEvent.getPropertyName().equals(PROPERTY_ROWS_CLEARED)) {
            final Integer rowsCleared = (Integer) theEvent.getNewValue();

            myLinesCleared += rowsCleared;

            updateLevel();
            updateScore(rowsCleared);

        } else if (PROPERTY_GAME_STATE.equals(theEvent.getPropertyName())) {
            final GameControls.GameState newGameState =
                    (GameControls.GameState) theEvent.getNewValue();

            switch (newGameState) {
                case GameControls.GameState.NEW -> myTimer.restart();
                case GameControls.GameState.PAUSED,
                     GameControls.GameState.OVER -> myTimer.stop();
                case GameControls.GameState.RUNNING -> {
                    if (!GameControls.GameState.RUNNING.equals(myLastGameState)) {
                        myTimer.start();
                    }
                }
                default -> throw
                        new EnumConstantNotPresentException(
                                GameControls.GameState.class, String.valueOf(newGameState));
            }

            myLastGameState = newGameState;
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
        final int newLevel = myLinesCleared / LINES_PER_LEVEL + 1;

        if (newLevel > myCurrentLevel) {
            myTimer.setDelay(DEFAULT_DELAY - myCurrentLevel * DELAY_DECREMENT);
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
        myScore += SCORE_PER_ROW_CLEARED * theLinesCleared;
        myScore += SCORE_PER_ADD_ROW * (theLinesCleared - 1);
    }

    /**
     * Gets the current amount of lines cleared in the tetris game.
     *
     * @return the number of lines cleared in the tetris game thus far.
     */
    public int getLinesCleared() {
        return myLinesCleared;
    }
}