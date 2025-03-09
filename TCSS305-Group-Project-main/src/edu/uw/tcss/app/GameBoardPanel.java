package edu.uw.tcss.app;

import edu.uw.tcss.model.GameControls;
import edu.uw.tcss.model.GameControls.Block;
import edu.uw.tcss.model.GameControls.IndividualPiece;
import edu.uw.tcss.model.GameControls.Point;
import edu.uw.tcss.util.ColorScheme;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.Map;
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
    private static final int COLUMNS = 10;         // Number of columns & rows.
    private static final int ROWS = 20;
    private final int myBlockWidth;
    private final int myBlockHeight;
    private IndividualPiece[] myTetrisPieces;
    private Map<Block, Color> myColorScheme;

    private GameControls.FrozenBlocks myFrozen = Sprint1_values.frozenBlocks();

    {
        myTetrisPieces = Sprint1_values.pieces();   // Store Pieces in myTetrisPiece
    }

    /**
     * Constructs the game board.
     */
    public GameBoardPanel(final int theWidth, final int theHeight) {
        //Preferred size set to fit in layout.
        setPreferredSize(new Dimension(theWidth, theHeight));
        // TODO: for later, we could consider making
        //  a class that houses the preferences or something
        setBackground(Color.RED); //background red.

        myBlockWidth = theWidth / COLUMNS;
        myBlockHeight = theHeight / ROWS;

        myColorScheme = ColorScheme.getColorScheme();
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
                if (myFrozen.blocks().get(row)[column] != null) {
                    theGraphics.setColor(getBlockColor(myFrozen.blocks().get(row)[column]));

                    final int x = column * myBlockWidth;
                    final int y =  ((ROWS - 1) - row) * myBlockHeight;

                    theGraphics.fillRect(x, y, myBlockWidth,  myBlockHeight);
                    theGraphics.setColor(Color.BLACK);
                    theGraphics.drawRect(x, y, myBlockWidth, myBlockHeight);
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
        return myColorScheme.getOrDefault(theBlock, Color.PINK);
    }

    /**
     * Drawing the grid, 10 columns, 20 rows.
     *
     * @param theGraphics graphics for object drawing.
     */
    private void drawGrid(final Graphics theGraphics) {
        theGraphics.setColor(Color.BLACK);
        for (int column = 0; column <= COLUMNS; column++) { //vertical lines for column
            final int x = column * myBlockWidth;
            theGraphics.drawLine(x, 0, x, getHeight());
        }
        // horizontal line for rows
        for (int row = 0; row < ROWS; row++) {
            final int y = row * myBlockHeight;
            theGraphics.drawLine(0, y, getWidth(), y);
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
                final int x = block.x() * myBlockWidth;
                final int y = ((ROWS - 1) - block.y()) * myBlockHeight;

                g2d.setPaint(getBlockColor(piece.block()));
                theGraphics.fillRect(x, y, myBlockWidth, myBlockHeight);
                // TODO: maybe we could remove this and have the grid drawn after the blocks - RB
                g2d.setPaint(Color.BLACK);
                g2d.drawRect(x, y, myBlockWidth, myBlockHeight);
            }
        }
    }

    /**
     * Sets the color scheme for the tetris pieces.
     *
     * @param theColorScheme the color scheme for the tetris pieces to follow.
     */
    public void setColorScheme(final Map<Block, Color> theColorScheme) {
        myColorScheme = theColorScheme;
    }

    @Override
    public void propertyChange(final PropertyChangeEvent theEvent) {

        if (Objects.equals(theEvent.getPropertyName(), "The Game State has updated "
                + "to something new!")) {
            myTetrisPieces = new IndividualPiece[1];
        }
        if (Objects.equals(theEvent.getPropertyName(), "This is the current piece!")
                && theEvent.getNewValue() != null) {
            myTetrisPieces[0] = (IndividualPiece) theEvent.getNewValue();
            repaint();
        }

        if (Objects.equals(theEvent.getPropertyName(), "These are the frozen blocks!")
                && theEvent.getNewValue() != null) {
            myFrozen = (GameControls.FrozenBlocks) theEvent.getNewValue();
            repaint();
        }

    }
}

