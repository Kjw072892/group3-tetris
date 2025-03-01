package edu.uw.tcss.app;

import edu.uw.tcss.model.GameControls.Block;
import edu.uw.tcss.model.GameControls.IndividualPiece;
import edu.uw.tcss.model.GameControls.Point;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import javax.swing.JPanel;


/**
 * The gameBoard class (constructs and handles game logic).
 * @author Zainab
 * @author James
 * @author Kassie
 * @author Roman
 * @version 2.28.25
 */
public class BoardPanel extends JPanel {

    //Properties of the board & blocks.
    private static final int BOARD_WIDTH = 300;
    private static final int BOARD_HEIGHT = 600;
    private static final int COLUMNS = 10;         // Number of columns & rows.
    private static final int ROWS = 20;
    //private static final int PIECE_SIZE = 20;       // The size of each Piece.

    private IndividualPiece[] myTetrisPiece;

    /**
     * Constructs the game board.
     */
    public BoardPanel() {
        //Preferred size set to fit in layout.
        setPreferredSize(new Dimension(BOARD_WIDTH, BOARD_HEIGHT));
        setBackground(Color.RED); //background red.

        spawnNewPiece(); // Load all Sprint 1 pieces to the board.

    }

   //Gets all the Sprint 1 pieces and stores them for display.
    private void spawnNewPiece() {
        myTetrisPiece = Sprint1_values.pieces(); //Store Pieces in myTetrisPiece
        //System.out.println("Spawning New Piece"+ myTetrisPiece.length);
        repaint(); // Repaint board with pieces.

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
        // drawFrozenBlocks(g2d);

        drawPiece(g2d); // Draws all Sprint 1 pieces on board.
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


        for (IndividualPiece piece : myTetrisPiece) {
            // Loop through sprint 1 piece.
            for (Point block : piece.location()) {
                final int x = block.x() * (BOARD_WIDTH / COLUMNS);
                final int y = ((ROWS - 1) - block.y()) * (BOARD_HEIGHT / ROWS);

                g2d.setPaint(getBlockColor(piece.block()));
                theGraphics.fillRect(x, y, BOARD_WIDTH / COLUMNS, BOARD_WIDTH / COLUMNS);
                g2d.setPaint(Color.BLACK);
                g2d.drawRect(x, y, BOARD_HEIGHT / ROWS, BOARD_HEIGHT / ROWS);
            }
        }
    }
}

