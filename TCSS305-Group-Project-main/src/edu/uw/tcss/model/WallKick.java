/*
 * TCSS 305
 * 
 * An implementation of the classic game "Tetris".
 */

package edu.uw.tcss.model;

import edu.uw.tcss.model.GameControls.Point;

/**
 * Enumeration of Rotation types.
 * 
 * @author Alan Fowler
 * @version Spring 2014
 */
final class WallKick {
    
    /**
     * The kick offsets for rotations of J, L, S, T, and Z pieces. 
     */
    private static final Point[][] JLSTZ_OFFSETS = {
        {new Point(0, 0), new Point(-1, 0), new Point(-1, +1),
            new Point(0, -2), new Point(-1, -2)},  // NONE to QUARTER
        {new Point(0, 0), new Point(+1, 0), new Point(+1, -1),
            new Point(0, +2), new Point(+1, +2) }, // QUARTER to NONE
        {new Point(0, 0), new Point(+1, 0), new Point(+1, -1),
            new Point(0, +2), new Point(+1, +2) }, // QUARTER to HALF
        {new Point(0, 0), new Point(-1, 0), new Point(-1, +1),
            new Point(0, -2), new Point(-1, -2) }, // HALF to QUARTER
        {new Point(0, 0), new Point(+1, 0), new Point(+1, +1),
            new Point(0, -2), new Point(+1, -2) }, // HALF to THREEQUARTER
        {new Point(0, 0), new Point(-1, 0), new Point(-1, -1),
            new Point(0, +2), new Point(-1, +2) }, // THREEQUARTER to HALF
        {new Point(0, 0), new Point(-1, 0), new Point(-1, -1),
            new Point(0, +2), new Point(-1, +2) }, // THREEQUARTER to NONE
        {new Point(0, 0), new Point(+1, 0), new Point(+1, +1),
            new Point(0, -2), new Point(+1, -2) }  // NONE to THREEQUARTER
    };
    
    /**
     * The kick offsets for rotations of I pieces.
     */
    private static final Point[][] I_OFFSETS = {
        {new Point(0, 0), new Point(-2, 0), new Point(+1, 0),
            new Point(-2, -1), new Point(+1, +2)},  // NONE to QUARTER
        {new Point(0, 0), new Point(+2, 0), new Point(-1, 0),
            new Point(+2, +1), new Point(-1, -2) }, // QUARTER to NONE
        {new Point(0, 0), new Point(-1, 0), new Point(+2, 0),
            new Point(-1, +2), new Point(+2, -1) }, // QUARTER to HALF
        {new Point(0, 0), new Point(+1, 0), new Point(-2, 0),
            new Point(+1, -2) , new Point(-2, +1) }, // HALF to QUARTER
        {new Point(0, 0), new Point(+2, 0), new Point(-1, 0),
            new Point(+2, +1), new Point(-1, -2) }, // HALF to THREEQUARTER
        {new Point(0, 0), new Point(-2, 0), new Point(+1, 0),
            new Point(-2, -1), new Point(+1, +2) }, // THREEQUARTER to HALF
        {new Point(0, 0), new Point(+1, 0), new Point(-2, 0),
            new Point(+1, -2), new Point(-2, +1) }, // THREEQUARTER to NONE
        {new Point(0, 0), new Point(-1, 0), new Point(+2, 0),
            new Point(-1, +2), new Point(+2, -1) }  // NONE to THREEQUARTER
    };
    
    /**
     * Private constructor to inhibit external instantiation.
     */
    private WallKick() {
        super();
        // do nothing
    }
    
    /**
     * Returns an array of Points representing the wall kick offsets
     * for the situation defined by the parameters.
     * 
     * @param thePiece the piece type being rotated
     * @param theOriginalRotation the rotational state before the rotation
     * @param theGoalRotation the desired rotational state
     * @return The wall kick offsets for these conditions
     */
    static Point[] getWallKicks(final TetrisPiece thePiece,
                                       final Rotation theOriginalRotation,
                                       final Rotation theGoalRotation) {
        Point[] result = new Point[0];
        if (thePiece == TetrisPiece.I) {
            result = kickI(theOriginalRotation, theGoalRotation);
        } else if (thePiece != TetrisPiece.O) {
            result = kickNonO(theOriginalRotation, theGoalRotation);
        }
        return result;
    }

    @SuppressWarnings("OverlyComplexMethod")
    private static Point[] kickI(final Rotation theOriginalRotation,
                                 final Rotation theGoalRotation) {
        return switch (theOriginalRotation) {
            case final Rotation r when
                    r == Rotation.NONE && theGoalRotation == Rotation.QUARTER ->
                        I_OFFSETS[0];
            case final Rotation r
                    when r == Rotation.NONE && theGoalRotation == Rotation.THREEQUARTER ->
                        I_OFFSETS[7];
            case final Rotation r
                    when r == Rotation.QUARTER && theGoalRotation == Rotation.HALF ->
                        I_OFFSETS[2];
            case final Rotation r
                    when r == Rotation.QUARTER && theGoalRotation == Rotation.NONE ->
                        I_OFFSETS[1];
            case final Rotation r
                    when r == Rotation.HALF && theGoalRotation == Rotation.THREEQUARTER ->
                        I_OFFSETS[4];
            case final Rotation r
                    when r == Rotation.HALF && theGoalRotation == Rotation.QUARTER ->
                        I_OFFSETS[3];
            case final Rotation r
                    when r == Rotation.THREEQUARTER && theGoalRotation == Rotation.NONE ->
                        I_OFFSETS[6];
            case final Rotation r
                    when r == Rotation.THREEQUARTER && theGoalRotation == Rotation.HALF ->
                        I_OFFSETS[5];
            default -> throw new IllegalStateException("This rotation should not happen");

        };
    }

    @SuppressWarnings("OverlyComplexMethod")
    private static Point[] kickNonO(final Rotation theOriginalRotation,
                                 final Rotation theGoalRotation) {
        return switch (theOriginalRotation) {
            case final Rotation r when r == Rotation.NONE && theGoalRotation == Rotation.QUARTER ->
                    JLSTZ_OFFSETS[0];
            case final Rotation r when r == Rotation.NONE && theGoalRotation == Rotation.THREEQUARTER ->
                    JLSTZ_OFFSETS[7];
            case final Rotation r when r == Rotation.QUARTER && theGoalRotation == Rotation.HALF ->
                    JLSTZ_OFFSETS[2];
            case final Rotation r when r == Rotation.QUARTER && theGoalRotation == Rotation.NONE ->
                    JLSTZ_OFFSETS[1];
            case final Rotation r when r == Rotation.HALF && theGoalRotation == Rotation.THREEQUARTER ->
                    JLSTZ_OFFSETS[4];
            case final Rotation r when r == Rotation.HALF && theGoalRotation == Rotation.QUARTER ->
                    JLSTZ_OFFSETS[3];
            case final Rotation r when r == Rotation.THREEQUARTER && theGoalRotation == Rotation.NONE ->
                    JLSTZ_OFFSETS[6];
            case final Rotation r when r == Rotation.THREEQUARTER && theGoalRotation == Rotation.HALF ->
                    JLSTZ_OFFSETS[5];
            default -> throw new IllegalStateException("This rotation state should not happen");
        };
//        switch (theOriginalRotation) {
//            case NONE:
//                if (theGoalRotation == Rotation.QUARTER) {
//                    result = JLSTZ_OFFSETS[0];
//                } else if (theGoalRotation == Rotation.THREEQUARTER) {
//                    result = JLSTZ_OFFSETS[7];
//                }
//                break;
//            case QUARTER:
//                if (theGoalRotation == Rotation.HALF) {
//                    result = JLSTZ_OFFSETS[2];
//                } else if (theGoalRotation == Rotation.NONE) {
//                    result = JLSTZ_OFFSETS[1];
//                }
//                break;
//            case HALF:
//                if (theGoalRotation == Rotation.THREEQUARTER) {
//                    result = JLSTZ_OFFSETS[4];
//                } else if (theGoalRotation == Rotation.QUARTER) {
//                    result = JLSTZ_OFFSETS[3];
//                }
//                break;
//            case THREEQUARTER:
//                if (theGoalRotation == Rotation.NONE) {
//                    result = JLSTZ_OFFSETS[6];
//                } else if (theGoalRotation == Rotation.HALF) {
//                    result = JLSTZ_OFFSETS[5];
//                }
//                break;
//            default:
//                // should never happen
//                break;
//        }
    }

}
