package edu.uw.tcss.view.app;

import static edu.uw.tcss.model.PropertyChangeEnabledGameControls.PROPERTY_FROZEN_BLOCKS;
import static edu.uw.tcss.model.PropertyChangeEnabledGameControls.PROPERTY_ROWS_CLEARED;
import static edu.uw.tcss.model.PropertyChangeEnabledGameControls.PROPERTY_GAME_STATE;
import static edu.uw.tcss.model.GameControls.GameState;
import static edu.uw.tcss.view.util.AudioFXManager.Channels;

import edu.uw.tcss.model.TetrisGame;
import edu.uw.tcss.view.util.AudioFXManager;
import edu.uw.tcss.view.util.AudioMusicFactory;
import edu.uw.tcss.view.util.AudioMusicManager;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.logging.Level;
import java.util.logging.Logger;
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
    private static final int LINES_UNTIL_NEXT_LEVEL = 5;
    private static final int SCORE_PER_PIECE = 4;

    /** single line cleared */
    private static final int SINGLE = 1;

    /** double, two lines cleared */
    private static final int DOUBLE = 2;

    /** triple, three lines cleared */
    private static final int TRIPLE = 3;

    /** quad, four lines cleared */
    private static final int QUADRUPLE = 4;

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
            final GameState newGameState = (GameState) theEvent.getNewValue();
            handleGameState(newGameState);
            myLastGameState = newGameState;
        }

    }

    private void handleGameState(final GameState theGameState) {
        switch (theGameState) {
            case GameState.NEW -> {
                myScore = 0;
                myLinesCleared = 0;
                myCurrentLevel = 1;

                myTimer.setDelay(DEFAULT_DELAY);
                AudioMusicManager.restartMusic();
            }
            case GameState.PAUSED,
                 GameState.OVER -> myTimer.stop();
            case GameState.RUNNING,
                 GameState.WORRY,
                 GameState.PANIC -> {
                if (!GameState.RUNNING.equals(myLastGameState)) {
                    myTimer.start();
                }
                // TODO: need to fix backend never sending out the worry nor panic game states
                Logger.getAnonymousLogger().log(Level.INFO,
                        String.format("Current running game state: %s", theGameState));
            }
            default -> throw
                    new EnumConstantNotPresentException(
                            GameState.class, theGameState.toString());
        }

        if (GameState.PANIC.equals(theGameState)) {
            AudioMusicManager.setMusic(AudioMusicFactory.getMusicPanic());
            AudioMusicManager.startMusic();
        }
    }

    private void playLinesFX(final int theNumLinesCleared) {
        if (theNumLinesCleared == TRIPLE) {
            AudioFXManager.playSoundFX(Channels.THREE_LINES_FX);

        } else if (theNumLinesCleared == QUADRUPLE) {

            AudioFXManager.playSoundFX(Channels.FOUR_LINES_FX);
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
        final int newLevel = myLinesCleared / LINES_UNTIL_NEXT_LEVEL + 1;

        if (newLevel > myCurrentLevel) {
            myTimer.setDelay(DEFAULT_DELAY - myCurrentLevel * DELAY_DECREMENT);

            AudioFXManager.playSoundFX(Channels.NEW_LEVEL_FX);
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

        myScore += switch (theLinesCleared) {
            case SINGLE -> ONE_LINE_SCORE * myCurrentLevel;
            case DOUBLE -> TWO_LINE_SCORE * myCurrentLevel;
            case TRIPLE -> THREE_LINE_SCORE * myCurrentLevel;
            case QUADRUPLE -> FOUR_LINE_SCORE * myCurrentLevel;
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

    /**
     * Gets the number of lines until the next level is reached.
     *
     * @return the number of lines until a new level
     */
    public int getLinesUntilNextLevel() {
        return LINES_UNTIL_NEXT_LEVEL - myLinesCleared % LINES_UNTIL_NEXT_LEVEL;
    }
}