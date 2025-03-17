/*
 * TCSS 305
 * 
 * An implementation of the classic game "Tetris".
 */

package edu.uw.tcss.model;

import edu.uw.tcss.model.GameControls.Block;
import edu.uw.tcss.model.GameControls.Point;
import java.util.Random;


/**
 * Enumeration of the TetrisPiece types.
 *
 * @author Charles Bryan
 * @version Winter 2025
 */
enum TetrisPiece {

    /** The 'I' TetrisPiece. */
    I(4, 1,
            Block.I,
            new Point(0, 2), new Point(1, 2), new Point(2, 2), new Point(3, 2)),

    /** The 'J' TetrisPiece. */
    J(3, 2,
            Block.J,
            new Point(0, 2), new Point(0, 1), new Point(1, 1), new Point(2, 1)),

    /** The 'L' TetrisPiece. */
    L(3, 2,
            Block.L,
            new Point(2, 2), new Point(0, 1), new Point(1, 1), new Point(2, 1)),

    /** The 'O' TetrisPiece. */
    O(2, 2,
            Block.O,
            new Point(1, 2), new Point(2, 2), new Point(1, 1), new Point(2, 1)),

    /** The 'S' TetrisPiece. */
    S(3, 2,
            Block.S,
            new Point(1, 2), new Point(2, 2), new Point(0, 1), new Point(1, 1)),

    /** The 'T' TetrisPiece. */
    T(3, 2,
            Block.T,
            new Point(1, 2), new Point(0, 1), new Point(1, 1), new Point(2, 1)),

    /** The 'Z' TetrisPiece. */
    Z(3, 2,
            Block.Z,
            new Point(0, 2), new Point(1, 2), new Point(1, 1), new Point(2, 1));


    // Other class constants

    private static final Random RANDOM = new Random();


    // instance fields
    private final int myWidth;

    private final int myHeight;

    private final Point[] myPoints;

    private final Block myBlock;

    TetrisPiece(final int theWidth, final int theHeight,
                final Block theBlock, final Point... thePoints) {
        myWidth = theWidth;
        myHeight = theHeight;
        myBlock = theBlock;
        myPoints = thePoints.clone();
    }

    /**
     * Return the width of the TetrisPiece.
     *
     * @return the width of the TetrisPiece.
     */
    public int getWidth() {
        return myWidth;
    }

    /**
     * Return the height of the TetrisPiece.
     *
     * @return the height of the TetrisPiece.
     */
    public int getHeight() {
        return myHeight;
    }

    /**
     * Return the Block type of the TetrisPiece.
     *
     * @return The Block type of the TetrisPiece.
     */
    public Block getBlock() {
        return myBlock;
    }

    /**
     * Returns the Points of the TetrisPiece.
     *
     * @return the Points of the TetrisPiece.
     */
    public Point[] getMyPoints() {
        return myPoints.clone();
    }

    static TetrisPiece getRandomPiece() {
        return values()[RANDOM.nextInt(values().length)];
    }
}
