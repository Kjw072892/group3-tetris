package edu.uw.tcss.app;

/**
 * Interface that lets an object set the score for an object that implements this
 * interface. Intended for the game board to set the score for the JPanel that
 * displays the score and stats.
 *
 * @author Roman Bureacov
 * @version 2025-02-28
 */
public interface ScoreHandler {

    /**
     * Tell the object to set the score information using a record
     * of the current scores.
     *
     * @param theRecord the record of the score, number of lines cleared, and
     *                  current level.
     */
    void setScore(ScoreRecord theRecord);

    /**
     * The record class of the score, number of lines cleared, and the current
     * level.
     *
     * @param score the current score
     * @param linesCleared the number of lines that have been cleared
     * @param level the current level
     */
    record ScoreRecord(int score, int linesCleared, int level) { }

}
