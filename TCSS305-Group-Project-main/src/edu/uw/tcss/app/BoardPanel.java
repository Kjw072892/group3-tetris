package edu.uw.tcss.app;
import javax.swing.*;
import java.awt.*;
import edu.uw.tcss.model.GameControls.Point;
import edu.uw.tcss.model.GameControls.IndividualPiece;
import edu.uw.tcss.model.GameControls.Block;



/**  Individual pieces of Tetris game.
 * Using the 'IndividualPieces' objects.
 */
public class BoardPanel extends JPanel {

    //Properties of the board & blocks.
    private static final int BOARD_WIDTH =300;
    private static final int BOARD_HEIGHT = 600;
    private static final int COLUMNS = 10;         // Number of columns & rows.
    private static final int ROWS = 20;
    private static final int PIECE_SIZE = BOARD_WIDTH / COLUMNS;       // The size of each Piece.

    private IndividualPiece[] myTetrisPiece;



    /*Construct game board */
    public BoardPanel() {
        setPreferredSize(new Dimension(BOARD_WIDTH, BOARD_HEIGHT)); //Preferred size set to fit in layout.
        setBackground(new Color(162, 33, 33)); //background red.

        spawnNewPiece(); // Load all Sprint 1 pieces to the board.

    }
/*
 * Gets all the Sprint 1 pieces and stores them for display.
 */
    private void spawnNewPiece() {
        myTetrisPiece = Sprint1_values.pieces(); //Store Pieces in myTetrisPiece
        System.out.println("Spawning New Piece"+ myTetrisPiece.length);
        repaint(); // Repaint board with pieces.

    }

    /**
     * Paints the board grid lines and pieces.
     *
     * @param g the Graphics object for drawing.
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        drawGrid(g2d); //draw the grid lines on the board.
        // drawFrozenBlocks(g2d);

        drawPiece(g2d); // Draws all Sprint 1 pieces on board.
    }


    /**
     * Get the correct color for blocks
     * @param block the block types
     * @return the color related to block
     */
    private Color getBlockColor(Block block) {
        return switch (block) {
            case I -> Color.CYAN;
            case O -> Color.YELLOW;
            case T -> Color.MAGENTA;
            case Z -> Color.RED;
            case L -> Color.ORANGE;
            case S -> Color.GREEN;
            case J -> Color.BLUE;
            default -> Color.DARK_GRAY;
        };
    }

    /**
     * Drawing the grid, 10 columns, 20 rows.
     *
     * @param g graphics for object drawing.
     */
    private void drawGrid(Graphics g) {
        g.setColor(Color.BLACK);
        for (int column = 0; column <= COLUMNS; column++) { //vertical lines for column
            int x = column * PIECE_SIZE;
            g.drawLine(x, 0, x, BOARD_HEIGHT);
        }
        // horizontal line for rows
        for (int row = 0; row < ROWS; row++) {
            int y = row * PIECE_SIZE;
            g.drawLine(0, y, BOARD_WIDTH, y);
        }

    }

// draw the pieces.
    private void drawPiece(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        for (IndividualPiece piece : myTetrisPiece) {
            // Loop
            for (Point block : piece.location()) {
                int x = block.x() * PIECE_SIZE;
                int y = block.y() *  PIECE_SIZE;

                g2d.setPaint(getBlockColor(piece.block()));
                g.fillRect(x, y, PIECE_SIZE, PIECE_SIZE);
                g2d.setPaint(Color.BLACK);
                g2d.drawRect(x, y, PIECE_SIZE, PIECE_SIZE);
            }
        }
    }
}

