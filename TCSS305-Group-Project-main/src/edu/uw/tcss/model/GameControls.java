package edu.uw.tcss.model;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * The controls for a Tetris game. A Tetris game consists of the following constructs:
 * <ul>
 *     <li>A game state as defined by the <code>GameState</code> enum.</li>
 *     <li>A game "board" made up of width (w) by height (h) blocks.
 *          <ul>
 *              <li>The data class <code>Point</code> is used to represent a
 *              location on the board.</li>
 *              <li>The "bottom" row of the board is represented by <code>y = 0</code>
 *              <ul><li>Positive y moves "up" the board</li></ul></li>
 *              <li>The "right" column of the board is represented by <code>x = 0</code>
 *              <ul><li>Positive x moves "right" across the board</li></ul></li>
 *          </ul>
 *     </li>
 *     <li>A current movable piece that the user may manipulate.
 *          <ul>
 *              <li>The data class <code>IndividualPiece</code> is used to represent the
 *              current movable piece.</li>
 *          </ul>
 *     </li>
 *     <li>A set of frozen blocks that represent all Tetris <code>Block</code> objects
 *     that have frozen into place.</li>
 * </ul>
 *
 * @author Charles Bryan
 * @version Winter 25
 */
public interface GameControls {

    /**
     * Places the game in <code>GameState.NEW</code> followed by
     * placing the game into <code>GameState.RUNNING</code>.
     * This method must be called before the first game
     * and before each new game. No restriction on the current
     * GameState is enforced.
     */
    void newGame();

    /**
     * Places the game in <code>GameState.OVER</code>. The game must be in
     * <code>GameState.PAUSED</code> or <code>GameState.RUNNING</code>
     * for this action to have an effect.
     */
    void endGame();

    /**
     * Advances the game by one 'step'. The game must be in
     *  <code>GameState.RUNNING</code> for this action to have an effect.
     * <br />
     * This action could include:
     * <ul>
     * <li>moving the movable piece down 1 line</li>
     * <li>freezing the movable piece if appropriate</li>
     * (which may end the game placing it into <code>GameState.OVER</code>)
     * <li>clearing full lines as needed</li>
     * </ul>
     */
    void step();

    /**
     * Try to move the movable piece down. The game must be in
     * <code>GameState.RUNNING</code> for this action to have an effect.
     * <br />
     * This action could include:
     * <ul>
     * <li>freezing the movable piece if appropriate</li>
     * (which may end the game placing it into <code>GameState.OVER</code>)
     * <li>clearing full lines as needed</li>
     * </ul>
     */
    void down();

    /**
     * Try to move the movable piece left.  The game must be in
     * <code>GameState.RUNNING</code> for this action to have an effect.
     */
    void left();

    /**
     * Try to move the movable piece right.  The game must be in
     * <code>GameState.RUNNING</code> for this action to have an effect.
     */
    void right();

    /**
     * Try to rotate the movable piece in the clockwise direction. The game must be in
     * <code>GameState.RUNNING</code> for this action to have an effect.
     */
    void rotateCW();

    /**
     * Try to rotate the movable piece in the counter-clockwise direction. The game must be in
     * <code>GameState.RUNNING</code> for this action to have an effect.
     */
    void rotateCCW();

    /**
     * Drop the movable piece until it is set. The game must be in
     * <code>GameState.RUNNING</code> for this action to have an effect.
     * This action will include:
     * <ul>
     * <li>freezing the movable piece</li>
     * (which may end the game placing it into <code>GameState.OVER</code>)
     * <li>clearing full lines as needed</li>
     * </ul>
     */
    void drop();

    /**
     * Places the game into <code>GameState.PAUSED</code>. The game
     * must be in <code>GameState.RUNNING</code> for this action to
     * have an effect.
     */
    void pause();

    /**
     * Places the game into <code>GameState.RUNNING</code>. The game
     * must be in <code>GameState.PAUSED</code> for this action to
     * have an effect.
     */
    void unPause();

    /**
     * Toggles the game into either <code>GameState.RUNNING</code> or
     * <code>GameState.PAUSED</code>. More formally, if the game is in
     * <code>GameState.PAUSED</code> this action places the game into
     * <code>GameState.RUNNING</code> and if the game is in
     * <code>GameState.RUNNING</code>, this action places the game in
     * <code>GameState.PAUSED</code>. The game must be in
     * <code>GameState.PAUSED</code> or <code>GameState.RUNNING</code>
     * for this action to have an effect.
     */
    void togglePause();

    /**
     * Data class that represents a "point" on the Tetris game board. Positive x
     * moves right across "columns" of <code>Block</code> objects. Positive y
     * moves up "rows" of <code>Block</code> objects.
     * @param x a singular x coordinate on the Tetris game board.
     * @param y a singular y coordinate on the Tetris game board.
     */
    record Point(int x, int y) {
        Point transform(final int theX, final int theY) {
            return new Point(x + theX, y + theY);
        }

        Point transform(final Point thePoint) {
            return transform(thePoint.x(), thePoint.y());
        }

        @Override
        public String toString() {
            return "new Point(" + x + ", " + y + ")";
        }
    }

    /**
     * Data class that represents a single Tetris tetromino in a location on the Tetris board.
     * The <code>Point[] location</code> contains the points for all four individual
     * <code>Block</code> objects that make up a single tetromino on the Game board.
     * <br />
     * Note, it is possible for some <code>Block</code> objects to be "above" the limit of
     * the game board size. This occurs when a new tetromino is added to the board and its
     * top half is outside the board.
     *
     * @param location the set of points for all four individual blocks that make up a single
     *      tetromino on the Game board
     * @param block the enum of the current tetromino
     */
    record IndividualPiece(Point[] location, Block block) {

        @Override
        public String toString() {
            return "new IndividualPiece(new Point[] {"
                    + Arrays.stream(location)
                            .map(Point::toString)
                            .collect(Collectors.joining(", "))
                    + "}, Block." + block + ")";
        }
    }

    /**
     * Data class that represents all Tetris <code>Block</code> objects that have frozen into place.
     * A frozen <code>Block</code> object is individual <code>Block</code> object from a complete
     * tetromino that has completed its trajectory down the board and has frozen into place.
     * <br />
     * Note: Implementing classes my use <code>Block.EMPTY</code> or <code>null</code> for
     * Empty spaces in the collection. Please refer to that documentation for implementation
     * specific details.
     *
     * @param blocks all Tetris <code>Block</code> objects that have frozen into place
     */
    record FrozenBlocks(List<Block[]> blocks) {

        @Override
        public String toString() {
            return "new GameControls.FrozenBlocks(List.of("
                    + blocks.stream()
                        .map(rowOfBlocks -> Arrays.stream(rowOfBlocks)
                                .map(b -> Optional.ofNullable(b)
                                        .map(block -> "Block." + block)
                                        .orElse("null"))
                                .collect(Collectors.joining(", ", "\nnew Block[]{", "}")))
                        .collect(Collectors.joining(", "))
                    + "));";
        }
    }

    /**
     * Specifies a game state for Tetris. The implementing class of the GameControls interface
     * will always be in one of these defined states.
     *
     * @author Charles Bryan
     * @version Winter 2025
     */
    enum GameState {

        /**
         * A new Tetris game has started. The game is not yet RUNNING. It may not
         * become PAUSED or OVER. The next state is RUNNING.
         */
        NEW,
        /**
         * A Tetris game is RUNNING. The game is not PAUSED or over but may become PAUSED or OVER.
         */
        RUNNING,
        /** A tetris game is PAUSED. The game is not OVER.  */
        PAUSED,
        /**
         * A Tetris game is OVER. It may not be PAUSED.
         * It may not become RUNNING without requesting a new game.
         */
        OVER,
    }

    /**
     * The different types of blocks that can be stored in a Board's grid.
     *
     * @author Charles Bryan
     * @version Winter 2025
     */
    enum Block {

        /**
         * An empty space in the grid.
         */
        EMPTY,
        /**
         * A Block from an IPiece.
         */
        I,
        /**
         * A Block from a JPiece.
         */
        J,
        /**
         * A Block from an LPiece.
         */
        L,
        /**
         * A Block from an OPiece.
         */
        O,
        /**
         * A Block from an SPiece.
         */
        S,
        /**
         * A Block from a TPiece.
         */
        T,
        /**
         * A Block from a ZPiece.
         */
        Z
    }

}
