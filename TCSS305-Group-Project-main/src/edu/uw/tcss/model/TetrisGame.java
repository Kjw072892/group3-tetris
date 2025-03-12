
package edu.uw.tcss.model;


import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Implementation of a Tetris game. As evidenced by implementing
 * <code>PropertyChangeEnabledGameControls</code>, this class leverages the
 * <code>PropertyChange</code> framework of the Observer Design Pattern to inform
 * interested parties of updates to this object's state.
 * <p>Note: This implementation uses <code>null</code> to represent empty frozen blocks.</p>
 *
 * @author Charles Bryan
 * @author Alan Fowler
 * @version Winter 2025
 */
public class TetrisGame implements PropertyChangeEnabledGameControls {

    private static int instance_count = 0;

    // Class constants
    
    /**
     * Default width of a Tetris game board.
     */
    private static final int DEFAULT_WIDTH = 10;

    /**
     * Default height of a Tetris game board.
     */
    private static final int DEFAULT_HEIGHT = 20;

    
    // Instance fields

    // Width of the game board.
    private final int myWidth;

    // Height of the game board.
    private final int myHeight;

    // The frozen blocks on the board.
    private final List<Block[]> myFrozenBlocks;

     // Piece that is next to play.
    private TetrisPiece myNextPiece;

    // Piece that is currently movable.
    private MovableTetrisPiece myCurrentPiece;

    // The current GameState
    private GameState myState;

    private boolean wasWorried;

    private boolean wasPanicing;

    /**
     * A flag to indicate when moving a piece down is part of a drop operation.
     * This is used to prevent the Board from notifying observers for each incremental
     * down movement in the drop.
     */
    private boolean myDrop;

    // In support of the PCL Framework
    private final PropertyChangeSupport myPcs;
    
    // Constructors

    /**
     * Default Tetris board constructor.
     * Creates a standard size tetris game board.
     */
    public TetrisGame() {
        this(DEFAULT_WIDTH, DEFAULT_HEIGHT);
    }

    /**
     * Tetris board constructor for non-default sized boards.
     * 
     * @param theWidth Width of the Tetris game board.
     * @param theHeight Height of the Tetris game board.
     */
    public TetrisGame(final int theWidth, final int theHeight) {
        super();

        if (instance_count > 0) {
            throw new IllegalStateException("There should be only one!");
        }

        myWidth = theWidth;
        myHeight = theHeight;
        myFrozenBlocks = new LinkedList<>();
         
        myState = GameState.OVER;

        myPcs = new PropertyChangeSupport(this);
        
        /*  myNextPiece and myCurrentPiece
         *  are initialized by the newGame() method.
         */

        instance_count++;
    }


    /**
     * {@inheritDoc}
     * <p>
     * When this action is performed, all interested parties will be notified
     * of the action via the following properties in the following order:
     * <ul>
     *     <li><code>PROPERTY_GAME_STATUS</code>
     *     <ul><li><code>GameState.NEW</code></li></ul></li>
     *     <li><code>PROPERTY_NEXT_PIECE</code>
     *     <ul><li>A new "next" movable piece to display</li></ul></li>
     *     <li><code>PROPERTY_CURRENT_PIECE</code>
     *     <ul><li>A new movable piece to display</li></ul></li>
     *     <li><code>PROPERTY_FROZEN_BLOCKS</code>
     *     <ul>
     *         <li>An empty collection of frozen blocks</li>
     *         <li>Note: This implementation uses <code>null</code> to represent empty blocks.</li>
     *     </ul></li>
     *     <li><code>PROPERTY_GAME_STATUS</code>
     *     <ul><li><code>GameState.RUNNING</code></li></ul></li>
     * </ul>
     * </p>
     */
    @Override
    public void newGame() {

        setGameState(GameState.NEW);

        myFrozenBlocks.clear();
        for (int h = 0; h < myHeight; h++) {
            myFrozenBlocks.add(new Block[myWidth]);
        }

        myCurrentPiece = nextMovablePiece(true);
        myDrop = false;

        setGameState(GameState.NEW);

        myPcs.firePropertyChange(
                PROPERTY_NEXT_PIECE,
                null,
                new IndividualPiece(myNextPiece.getMyPoints(), myNextPiece.getBlock()));
        final IndividualPiece ip =
                new IndividualPiece(
                        myCurrentPiece.getBoardPoints(),
                        myCurrentPiece.getTetrisPiece().getBlock());
        myPcs.firePropertyChange(PROPERTY_CURRENT_PIECE, null, ip);
        myPcs.firePropertyChange(
                PROPERTY_FROZEN_BLOCKS,
                null,
                new FrozenBlocks(List.copyOf(myFrozenBlocks)));
        setGameState(GameState.RUNNING);
    }

    /**
     * {@inheritDoc}
     * <p>
     * When this action is performed, all interested parties will be notified
     * of the action via the following properties:
     * <ul>
     *     <li><code>PROPERTY_GAME_STATUS</code>
     *     <ul><li><code>GameState.OVER</code></li></ul></li>
     * </ul>
     * </p>
     */
    @Override
    public void endGame() {
        if (isRunning() || myState == GameState.PAUSED) {
            setGameState(GameState.OVER);
        }
    }

    /**
     * {@inheritDoc}
     * <p>
     * <em><strong>If</strong></em> this action is performed, all interested parties will be
     * notified of the action via the following properties:
     * <ul>
     *     <li><code>PROPERTY_GAME_STATUS</code>
     *     <ul><li><code>GameState.PAUSED</code></li></ul></li>
     * </ul>
     * </p>
     */
    @Override
    public void pause() {
        if (isRunning()) {
            setGameState(GameState.PAUSED);
        }
    }

    /**
     * {@inheritDoc}
     * <p>
     * <em><strong>If</strong></em> this action is performed, all interested parties will be
     * notified of the action via the following properties:
     * <ul>
     *     <li><code>PROPERTY_GAME_STATUS</code>
     *     <ul><li><code>GameState.RUNNING</code></li></ul></li>
     * </ul>
     * </p>
     */
    @Override
    public void unPause() {
        if (myState == GameState.PAUSED) {

            if (wasWorried) {
                setGameState(GameState.WORRY);
            } else {
                setGameState(GameState.RUNNING);
            }

            if (wasPanicing) {
                setGameState(GameState.PANIC);
            } else {
                setGameState(GameState.RUNNING);
            }
        }
    }

    /**
     * {@inheritDoc}
     * <p>
     * <em><strong>If</strong></em> this action is performed, all interested parties will be
     * notified of the action via the following properties:
     * <ul>
     *     <li><code>PROPERTY_GAME_STATUS</code>
     *     <ul><li><code>GameState.RUNNING</code> <em><strong>OR</strong></em>
     *     <code>GameState.PAUSED</code></li></ul></li>
     * </ul>
     * </p>
     */
    @Override
    public void togglePause() {
        if (isRunning()) {
            setGameState(GameState.PAUSED);
//            System.out.println(
//                    new IndividualPiece(
//                            myCurrentPiece.getBoardPoints(),
//                            myCurrentPiece.getTetrisPiece().getBlock()));
            System.out.println(new FrozenBlocks(myFrozenBlocks));
        } else if (myState == GameState.PAUSED) {
            if (wasWorried) {
                setGameState(GameState.WORRY);
            } else {
                setGameState(GameState.RUNNING);
            }

            if (wasPanicing) {
                setGameState(GameState.PANIC);
            } else {
                setGameState(GameState.RUNNING);
            }
        }
    }

    /**
     * {@inheritDoc}
     * <p>
     * <em><strong>If</strong></em> this action is performed, all interested parties will be
     * notified of the action via the following properties in the following order:
     * <ul>
     *     <li><code><strong>*</strong> PROPERTY_ROWS_CLEARED</code>
     *     <ul><li>The number of rows cleared</li></ul></li>
     *     <li><code><strong>*</strong> PROPERTY_NEXT_PIECE</code>
     *     <ul><li>The new "next" movable piece</li></ul></li>
     *     <li><code>PROPERTY_CURRENT_PIECE</code>
     *     <ul>
     *         <li>An update to the movable piece to display</li>
     *         <li>This may be a "new" piece</li>
     *     </ul></li>
     *     <li><code><strong>*</strong> PROPERTY_FROZEN_BLOCKS</code>
     *     <ul><li>An update to the frozen blocks</li></ul></li>
     * </ul>
     * <strong>*</strong> Only when needed
     * </p>
     */
    @Override
    public void step() {
        /*
         * Calling the down() method from here should be sufficient
         * to advance the board by one 'step'.
         * However, more code could be added to this method
         * to implement additional functionality
         */
        if (isRunning()) {
            down();
        }
    }


    /**
     * {@inheritDoc}
     * <p>
     * <em><strong>If</strong></em> this action is performed, all interested parties will be
     * notified of the action via the following properties in the following order:
     * <ul>
     *     <li><code><strong>*</strong> PROPERTY_ROWS_CLEARED</code>
     *     <ul><li>The number of rows cleared</li></ul></li>
     *     <li><code><strong>*</strong> PROPERTY_NEXT_PIECE</code>
     *     <ul><li>The new "next" movable piece</li></ul></li>
     *     <li><code>PROPERTY_CURRENT_PIECE</code>
     *     <ul>
     *         <li>An update to the movable piece to display</li>
     *         <li>This may be a "new" piece</li>
     *     </ul></li>
     *     <li><code><strong>*</strong> PROPERTY_FROZEN_BLOCKS</code>
     *     <ul><li>An update to the frozen blocks</li></ul></li>
     * </ul>
     * <strong>*</strong> Only when needed
     * </p>
     */
    @Override
    public void down() {
        if (isRunning() && !move(myCurrentPiece.down())) {
            // the piece froze, so clear lines and update current piece
            addPieceToBoardData(myFrozenBlocks, myCurrentPiece);
            checkRows();
            if (isRunning()) {
                myCurrentPiece = nextMovablePiece(false);
                myPcs.firePropertyChange(
                        PROPERTY_NEXT_PIECE,
                        null,
                        new IndividualPiece(myNextPiece.getMyPoints(), myNextPiece.getBlock()));
                final IndividualPiece ip =
                        new IndividualPiece(
                                myCurrentPiece.getBoardPoints(),
                                myCurrentPiece.getTetrisPiece().getBlock());
                myPcs.firePropertyChange(PROPERTY_CURRENT_PIECE, null, ip);
                myPcs.firePropertyChange(
                        PROPERTY_FROZEN_BLOCKS,
                        null,
                        new FrozenBlocks(List.copyOf(myFrozenBlocks)));
            }
            checkHalfWay();
            checkThreeFourths();
        }
    }

    /**
     * {@inheritDoc}
     * <p>
     * <em><strong>If</strong></em> this action is performed, all interested parties will be
     * notified of the action via the following property:
     * <ul>
     *     <li><code>PROPERTY_CURRENT_PIECE</code>
     *     <ul>
     *         <li>An update to the movable piece to display</li>
     *     </ul></li>
     * </ul>
     * </p>
     */
    @Override
    public void left() {
        if (isRunning() && myCurrentPiece != null) {
            move(myCurrentPiece.left());
        }
    }

    /**
     * {@inheritDoc}
     * <p>
     * <em><strong>If</strong></em> this action is performed, all interested parties will be
     * notified of the action via the following property:
     * <ul>
     *     <li><code>PROPERTY_CURRENT_PIECE</code>
     *     <ul>
     *         <li>An update to the movable piece to display</li>
     *     </ul></li>
     * </ul>
     * </p>
     */
    @Override
    public void right() {
        if (isRunning() && myCurrentPiece != null) {
            move(myCurrentPiece.right());
        }
    }

    /**
     * {@inheritDoc}
     * <p>
     * <em><strong>If</strong></em> this action is performed, all interested parties will be
     * notified of the action via the following property:
     * <ul>
     *     <li><code>PROPERTY_CURRENT_PIECE</code>
     *     <ul>
     *         <li>An update to the movable piece to display</li>
     *     </ul></li>
     * </ul>
     * </p>
     */
    @Override
    public void rotateCW() {
        if (isRunning() && myCurrentPiece != null) {
            if (myCurrentPiece.getTetrisPiece() == TetrisPiece.O) {
                move(myCurrentPiece.rotateCW());
            } else {
                final MovableTetrisPiece cwPiece = myCurrentPiece.rotateCW();
                final Point[] offsets = WallKick.getWallKicks(cwPiece.getTetrisPiece(),
                                                    myCurrentPiece.getRotation(),
                                                    cwPiece.getRotation());
                for (final Point p : offsets) {
                    final Point offsetLocation = cwPiece.getPosition().transform(p);
                    final MovableTetrisPiece temp = cwPiece.setPosition(offsetLocation);
                    if (move(temp)) {
                        break;
                    }
                }
            }
        }
    }

    /**
     * {@inheritDoc}
     * <p>
     * <em><strong>If</strong></em> this action is performed, all interested parties will be
     * notified of the action via the following property:
     * <ul>
     *     <li><code>PROPERTY_CURRENT_PIECE</code>
     *     <ul>
     *         <li>An update to the movable piece to display</li>
     *     </ul></li>
     * </ul>
     * </p>
     */
    @Override
    public void rotateCCW() {
        if (isRunning() && myCurrentPiece != null) {
            if (myCurrentPiece.getTetrisPiece() == TetrisPiece.O) {
                move(myCurrentPiece.rotateCCW());
            } else {
                final MovableTetrisPiece ccwPiece = myCurrentPiece.rotateCCW();
                final Point[] offsets = WallKick.getWallKicks(ccwPiece.getTetrisPiece(),
                                                    myCurrentPiece.getRotation(),
                                                    ccwPiece.getRotation());
                for (final Point p : offsets) {
                    final Point offsetLocation = ccwPiece.getPosition().transform(p);
                    final MovableTetrisPiece temp = ccwPiece.setPosition(offsetLocation);
                    if (move(temp)) {
                        break;
                    }
                }
            }
        }
    }

    /**
     * {@inheritDoc}
     * <p>
     * <em><strong>If</strong></em> this action is performed, all interested parties will be
     * notified of the action via the following properties in the following order:
     * <ul>
     *     <li><code><strong>*</strong> PROPERTY_ROWS_CLEARED</code>
     *     <ul><li>The number of rows cleared</li></ul></li>
     *     <li><code>PROPERTY_NEXT_PIECE</code>
     *     <ul><li>The new "next" movable piece</li></ul></li>
     *     <li><code>PROPERTY_CURRENT_PIECE</code>
     *     <ul>
     *         <li>A new movable piece to display</li>
     *         <li>This will be a "new" piece</li>
     *     </ul></li>
     *     <li><code>PROPERTY_FROZEN_BLOCKS</code>
     *     <ul><li>An update to the frozen blocks</li></ul></li>
     * </ul>
     * <strong>*</strong> Only when needed
     * </p>
     */
    @Override
    public void drop() {
        if (isRunning()) {
            myDrop = true;
            while (isPieceLegal(myCurrentPiece.down())) {
                down();  // move down as far as possible
            }
            myDrop = false;
            down();  // move down one more time to freeze in place
        }
    }

    @Override
    public void addPropertyChangeListener(final PropertyChangeListener theListener) {
        myPcs.addPropertyChangeListener(theListener);
    }

    @Override
    public void addPropertyChangeListener(final String thePropertyName,
                                          final PropertyChangeListener theListener) {
        myPcs.addPropertyChangeListener(thePropertyName, theListener);
    }

    @Override
    public void removePropertyChangeListener(final PropertyChangeListener theListener) {
        myPcs.removePropertyChangeListener(theListener);
    }

    @Override
    public void removePropertyChangeListener(final String thePropertyName,
                                             final PropertyChangeListener theListener) {
        myPcs.removePropertyChangeListener(thePropertyName, theListener);
    }

    /**
     * {@inheritDoc}
     * <p>
     *     Returns a textual representation of the Tetris board. Use this for sandboxing the Tetris
     *     API and logic.
     * </p>
     * @return a textual representation of the Tetris board.
     */
    @SuppressWarnings("OverlyLongMethod")
    @Override
    public String toString() {
        final List<Block[]> board = getBoard();
        board.add(new Block[myWidth]);
        board.add(new Block[myWidth]);
        board.add(new Block[myWidth]);
        board.add(new Block[myWidth]);
        final String line = "-".repeat(this.myWidth);
        if (myCurrentPiece != null) {
            addPieceToBoardData(board, myCurrentPiece);
        }
        final StringBuilder sb = new StringBuilder();
        for (int i = board.size() - 1; i >= 0; i--) {
            final Block[] row = board.get(i);
            sb.append('|');
            for (final Block c : row) {
                if (c == null) {
                    sb.append(' ');
                } else {
                    sb.append('*');
                }
            }
            sb.append("|\n");
            if (i == this.myHeight) {
                sb.append(' ');
                sb.append(line);
                sb.append('\n');
            }
        }
        sb.append('|');
        sb.append(line);
        sb.append('|');
        return sb.toString();
    }

    
    // private helper methods

    /**
     * Helper function to check if the current piece can be shifted to the
     * specified position.
     *
     * @param theMovedPiece the position to attempt to shift the current piece
     * @return True if the move succeeded
     */
    private boolean move(final MovableTetrisPiece theMovedPiece) {
        boolean result = false;
        if (isPieceLegal(theMovedPiece)) {
            myCurrentPiece = theMovedPiece;
            result = true;
            if (!myDrop) {
                // TODO Publish Update!
                final IndividualPiece ip =
                        new IndividualPiece(
                                myCurrentPiece.getBoardPoints(),
                                myCurrentPiece.getTetrisPiece().getBlock());
                myPcs.firePropertyChange(PROPERTY_CURRENT_PIECE, null, ip);

            }
        }
        return result;
    }

    /**
     * Helper function to test if the piece is in a legal state.
     * <p>
     * Illegal states:
     * - points of the piece exceed the bounds of the board
     * - points of the piece collide with frozen blocks on the board
     *
     * @param thePiece MovableTetrisPiece to test.
     * @return Returns true if the piece is in a legal state; false otherwise
     */
    private boolean isPieceLegal(final MovableTetrisPiece thePiece) {
        boolean result = true;

        for (final Point p : thePiece.getBoardPoints()) {
            if (p.x() < 0 || p.x() >= myWidth) {
                result = false;
            }
            if (p.y() < 0) {
                result = false;
            }
        }
        return result && !collision(thePiece);
    }

    /**
     * Adds a movable Tetris piece into a list of board data.
     * <p>
     * Allows a single data structure to represent the current piece
     * and the frozen blocks.
     *
     * @param theFrozenBlocks Board to set the piece on.
     * @param thePiece Piece to set on the board.
     */
    private void addPieceToBoardData(final List<Block[]> theFrozenBlocks,
                                     final MovableTetrisPiece thePiece) {
        for (final Point p : thePiece.getBoardPoints()) {
            setPoint(theFrozenBlocks, p, thePiece.getTetrisPiece().getBlock());
        }
    }

    private void checkHalfWay() {
        final int halfway = myFrozenBlocks.size() / 2;

        boolean foundBlock = false;

        if (isRunning()) {
            for (int i = 0; i < myFrozenBlocks.get(halfway).length; i++) {
                if (myFrozenBlocks.get(halfway)[i] != null) {
                    foundBlock = true;
                    break;
                }
            }

            if (!wasWorried && foundBlock) {
                setGameState(GameState.WORRY);
                wasWorried = true;
            }

            if (!foundBlock) {
                setGameState(GameState.RUNNING);
                wasWorried = false;
            }
        }
    }

    private void checkThreeFourths() {
        final double threeFourths = myHeight * (double) 3/4;

        final int panicHeight = (int) threeFourths;

        boolean foundBlock = false;

        if (isRunning()) {
            for (int i = 0; i < myFrozenBlocks.get(panicHeight).length; i++) {
                if (myFrozenBlocks.get(panicHeight)[i] != null) {
                    foundBlock = true;
                    break;
                }
            }

            if (!wasPanicing && foundBlock) {
                setGameState(GameState.PANIC);
                wasPanicing = true;
            }

            if (!foundBlock) {
                setGameState(GameState.RUNNING);
                wasPanicing = false;
            }
        }
    }

    /**
     * Checks the board for complete rows.
     */
    private void checkRows() {



        final List<Integer> completeRows = new ArrayList<>();
        for (final Block[] row : myFrozenBlocks) {
            boolean complete = true;
            for (final Block b : row) {
                if (b == null) {
                    complete = false;
                    break;
                }
            }
            if (complete) {
                completeRows.add(myFrozenBlocks.indexOf(row));
            }
        }
        // loop through list backwards removing items by index
        if (!completeRows.isEmpty()) {
            for (int i = completeRows.size() - 1; i >= 0; i--) {
                final Block[] row = myFrozenBlocks.get(completeRows.get(i));
                myFrozenBlocks.remove(row);
                myFrozenBlocks.add(new Block[myWidth]);
            }
            myPcs.firePropertyChange(PROPERTY_ROWS_CLEARED, null, completeRows.size());
        }
    }

    /**
     * Helper function to copy the board.
     *
     * @return A new copy of the board.
     */
    private List<Block[]> getBoard() {
        final List<Block[]> board = new ArrayList<>();
        for (final Block[] row : myFrozenBlocks) {
            board.add(row.clone());
        }
        return board;
    }

    /**
     * Determines if a point is on the game board.
     *
     * @param theBoard Board to test.
     * @param thePoint Point to test.
     * @return True if the point is on the board otherwise false.
     */
    private boolean isPointOnBoard(final List<Block[]> theBoard, final Point thePoint) {
        return thePoint.x() >= 0 && thePoint.x() < myWidth && thePoint.y() >= 0
               && thePoint.y() < theBoard.size();
    }

    /**
     * Sets a block at a board point.
     *
     * @param theBoard Board to set the point on.
     * @param thePoint Board point.
     * @param theBlock Block to set at board point.
     */
    private void setPoint(final List<Block[]> theBoard,
                          final Point thePoint,
                          final Block theBlock) {

        if (isPointOnBoard(theBoard, thePoint)) {
            final Block[] row = theBoard.get(thePoint.y());
            row[thePoint.x()] = theBlock;
        } else if (isRunning()) {
            setGameState(GameState.OVER);
        }
    }

    /**
     * Returns the block at a specific board point.
     *
     * @param thePoint the specific Point to check
     * @return the Block type at point or null if no block exists.
     */
    private Block getPoint(final Point thePoint) {
        Block b = null;
        if (isPointOnBoard(myFrozenBlocks, thePoint)) {
            b = myFrozenBlocks.get(thePoint.y())[thePoint.x()];
        }
        return b;
    }

    /**
     * Helper function to determine of a movable block has collided with set
     * blocks.
     *
     * @param theTest movable TetrisPiece to test for collision.
     * @return Returns true if any of the blocks has collided with a set board
     *         block.
     */
    private boolean collision(final MovableTetrisPiece theTest) {
        boolean res = false;
        for (final Point p : theTest.getBoardPoints()) {
            if (getPoint(p) != null) {
                res = true;
            }
        }
        return res;
    }

    /**
     * Gets the next MovableTetrisPiece.
     *
     * @param theRestart Restart the non-random cycle.
     * @return A new MovableTetrisPiece.
     */
    private MovableTetrisPiece nextMovablePiece(final boolean theRestart) {

        if (myNextPiece == null || theRestart) {
            prepareNextMovablePiece();
        }

        final TetrisPiece next = myNextPiece;

        int startY = myHeight - 1;
        if (myNextPiece == TetrisPiece.I) {
            startY--;
        }

        prepareNextMovablePiece();
        return new MovableTetrisPiece(
                       next,
                       new Point((myWidth - myNextPiece.getWidth()) / 2, startY));
    }

    /**
     * Prepares the Next movable piece for preview.
     */
    private void prepareNextMovablePiece() {
        myNextPiece = TetrisPiece.getRandomPiece();
    }

    private void setGameState(final GameState theStatus) {
        final GameState oldStatus = myState;
        myState = theStatus;
        myPcs.firePropertyChange(PROPERTY_GAME_STATE, oldStatus, myState);
    }

    private boolean isRunning() {
        return myState == GameState.RUNNING
                || myState == GameState.PANIC || myState == GameState.WORRY;
    }
}
