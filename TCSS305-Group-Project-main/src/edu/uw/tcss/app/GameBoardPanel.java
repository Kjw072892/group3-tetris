package edu.uw.tcss.app;

import edu.uw.tcss.model.GameControls;
import edu.uw.tcss.model.GameControls.Block;
import edu.uw.tcss.model.GameControls.IndividualPiece;
import edu.uw.tcss.model.GameControls.Point;
import edu.uw.tcss.model.TetrisGame;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.Objects;
import javax.swing.JPanel;


/**
 * The gameBoard class (constructs and handles game logic).
 * @author Zainab
 * @author James
 * @author Kassie
 * @author Roman
 * @version 3.7.25
 */
public class GameBoardPanel extends JPanel implements PropertyChangeListener {

    //Properties of the board & blocks.
    // TODO: might want to refrain from hard-coding these dimensions - RB
    private static final int BOARD_WIDTH = 300;
    private static final int BOARD_HEIGHT = 600;
    private static final int COLUMNS = 10;         // Number of columns & rows.
    private static final int ROWS = 20;
    // TODO: there's constant use of
    //  BOARD_WIDTH / COLUMNS and similar, perhaps those could be constants
    private IndividualPiece[] myTetrisPieces;
    //private Block[][] myFrozenBlocks = new Block[COLUMNS][ROWS];
    private GameControls.FrozenBlocks myFrozen = Sprint1_values.frozenBlocks();

    {
        myTetrisPieces = Sprint1_values.pieces();   // Store Pieces in myTetrisPiece
    }

    /**
     * Constructs the game board.
     */
    public GameBoardPanel() {
        //Preferred size set to fit in layout.
        setPreferredSize(new Dimension(BOARD_WIDTH, BOARD_HEIGHT));
        // TODO: for later, we could consider making
        //  a class that houses the preferences or something
        setBackground(Color.RED); //background red.

        // Load all Sprint 1 pieces to the board.

    }



    /**
     * Paints the board grid lines and pieces.
     *
     * @param theGraphics the Graphics object for drawing.
     */
    @Override
    protected void paintComponent(final Graphics theGraphics) {
        super.paintComponent(theGraphics);
        final Graphics2D g2d = (Graphics2D) theGraphics;
        drawGrid(g2d); //draw the grid lines on the board.
        drawFrozenBlocks(g2d);
        drawPiece(g2d); // Draws all Sprint 1 pieces on board.
    }
    private void drawFrozenBlocks(final Graphics theGraphics) {
        for (int column = 0; column < COLUMNS; column++) {
            for (int row = 0; row < ROWS; row++) {
                // TODO: variable declarations could make this more concise - RB
                if (myFrozen.blocks().get(row)[column] != null) {
                    theGraphics.setColor(getBlockColor(myFrozen.blocks().get(row)[column]));

                    final int x = column * (BOARD_WIDTH / COLUMNS);
                    final int y =  ((ROWS - 1) - row) * (BOARD_HEIGHT / ROWS);

                    theGraphics.fillRect(x, y, BOARD_WIDTH / COLUMNS,  BOARD_HEIGHT / ROWS);
                    theGraphics.setColor(Color.BLACK);
                    theGraphics.drawRect(x, y, BOARD_WIDTH / COLUMNS, BOARD_HEIGHT / ROWS);
                }
            }
        }
    }

    /**
     * Get the correct color for blocks
     * @param theBlock the theBlock types
     * @return the color related to theBlock
     */
    private Color getBlockColor(final Block theBlock) {
        return switch (theBlock) {
            case I -> Color.CYAN;
            case O -> Color.YELLOW;
            case T -> Color.MAGENTA;
            case Z -> Color.WHITE;
            case L -> Color.ORANGE;
            case S -> Color.GREEN;
            case J -> Color.BLUE;
            default -> Color.DARK_GRAY;
        };
    }

    /**
     * Drawing the grid, 10 columns, 20 rows.
     *
     * @param theGraphics graphics for object drawing.
     */
    private void drawGrid(final Graphics theGraphics) {
        theGraphics.setColor(Color.BLACK);
        for (int column = 0; column <= COLUMNS; column++) { //vertical lines for column
            final int x = column * (BOARD_WIDTH / COLUMNS);
            theGraphics.drawLine(x, 0, x, BOARD_HEIGHT);
        }
        // horizontal line for rows
        for (int row = 0; row < ROWS; row++) {
            final int y = row * (BOARD_HEIGHT / ROWS);
            theGraphics.drawLine(0, y, BOARD_WIDTH, y);
        }

    }

    // draw the pieces.
    private void drawPiece(final Graphics theGraphics) {
        final Graphics2D g2d = (Graphics2D) theGraphics;


        for (IndividualPiece piece : myTetrisPieces) {
            // Loop through sprint 1 piece.
            if (piece == null) {
                break;
            }
            for (Point block : piece.location()) {
                final int x = block.x() * (BOARD_WIDTH / COLUMNS);
                final int y = ((ROWS - 1) - block.y()) * (BOARD_HEIGHT / ROWS);

                g2d.setPaint(getBlockColor(piece.block()));
                theGraphics.fillRect(x, y, BOARD_WIDTH / COLUMNS, BOARD_WIDTH / COLUMNS);
                // TODO: maybe we could remove this and have the grid drawn after the blocks - RB
                g2d.setPaint(Color.BLACK);
                g2d.drawRect(x, y, BOARD_HEIGHT / ROWS, BOARD_HEIGHT / ROWS);
            }
        }
    }

    @Override
    public void propertyChange(final PropertyChangeEvent theEvent) {
        switch (theEvent.getPropertyName()) {
            case TetrisGame.PROPERTY_GAME_STATE -> myTetrisPieces = new IndividualPiece[1];
            case TetrisGame.PROPERTY_CURRENT_PIECE -> {
                myTetrisPieces[0] = (IndividualPiece) theEvent.getNewValue();
                repaint();
            }
            case TetrisGame.PROPERTY_FROZEN_BLOCKS -> {
                myFrozen = (GameControls.FrozenBlocks) theEvent.getNewValue();
                repaint();
            }
            default -> { }
        }

    }
}

