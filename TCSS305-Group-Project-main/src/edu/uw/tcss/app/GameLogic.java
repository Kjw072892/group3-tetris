package edu.uw.tcss.app;

import edu.uw.tcss.model.PropertyChangeEnabledGameControls;
import edu.uw.tcss.model.TetrisGame;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import javax.swing.Timer;

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

    GameLogic(final TetrisGame theTetrisGame) {
        myTetrisGame = theTetrisGame;
        myTimer = new Timer(DEFAULT_DELAY,
                theEvent -> { myTetrisGame.step(); System.out.println("Hello World!");});
        myTimer.start();
    }

    @Override
    public void propertyChange(final PropertyChangeEvent theEvent) {
        if (theEvent.getPropertyName()
                .equals(PropertyChangeEnabledGameControls.PROPERTY_ROWS_CLEARED)) {

            final Integer rowsCleared = (Integer) theEvent.getNewValue();

            myLinesCleared += rowsCleared;

            updateLevel();
            updateScore(rowsCleared);

            myCurrentLevel = 1;
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