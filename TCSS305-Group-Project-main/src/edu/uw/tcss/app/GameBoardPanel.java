package edu.uw.tcss.app;

import static edu.uw.tcss.model.GameControls.GameState;

import edu.uw.tcss.app.assets.AssetsManager;
import edu.uw.tcss.model.GameControls;
import edu.uw.tcss.model.GameControls.Block;
import edu.uw.tcss.model.GameControls.IndividualPiece;
import edu.uw.tcss.model.GameControls.Point;
import edu.uw.tcss.model.TetrisGame;
import edu.uw.tcss.util.ColorSchemeFactory;
import edu.uw.tcss.util.GraphicsModifier;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.Timer;


/**
 * The gameBoard class (constructs and handles game logic).
 * @author Zainab
 * @author James
 * @author Kassie
 * @author Roman
 * @version 3.7.25
 */
public class GameBoardPanel extends JPanel implements PropertyChangeListener {

    private static final int ONE_EIGHT_TWO = 182;
    private static final int ONE_HUNDRED_THREE = 103;
    private static final int ONE_HUNDRED_SIXTY_NINE = 169;
    private static final int FORTY_FIVE = 45;
    private static final int THIRTY_FIVE = 35;
    private static final int THREE_HUNDRED_FIFTY = 350;
    private static final int TWO_TWENTY = 220;
    private static final int EIGHTY = 80;
    private static final int DELAY = 100;
    private static final int THIRTY_SIX = 36;
    private static final int FIFTY_THREE = 53;
    private static final int FOUR_HUNDRED = 400;

    //Properties of the board & blocks.
    private static final int COLUMNS = 10;         // Number of columns and rows.
    private static final int ROWS = 20;
    private final int myBlockWidth;
    private final int myBlockHeight;
    private IndividualPiece[] myTetrisPieces;
    private boolean myGameOverDeath;

    private boolean myFlashColor;

    private final ImageIcon myDeathIcon;

    private final Timer myAnimator;

    private GameControls.FrozenBlocks myFrozen = Sprint1Values.frozenBlocks();

    {
        myTetrisPieces = Sprint1Values.pieces();   // Store Pieces in myTetrisPiece
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
        myDeathIcon = new ImageIcon(
                AssetsManager.getFilePath(AssetsManager.IMAGES_PATH, "oof-noob.gif"));
        myAnimator = new Timer(DELAY, new BackGroundColorAnimator());
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
        GraphicsModifier.enableAntiAliasing(g2d);

        drawFrozenBlocks(g2d);
        drawPiece(g2d); // Draws all Sprint 1 pieces on board.
        drawGrid(g2d); //draw the grid lines on the board.
        if (myGameOverDeath) {
            drawGameOver(g2d);
        }
    }


    private void drawGameOver(final Graphics2D theGraphics) {
        theGraphics.drawImage(myDeathIcon.getImage(), FORTY_FIVE, THIRTY_FIVE, this);
        theGraphics.setColor(Color.BLACK);
        theGraphics.fillRect(FORTY_FIVE, THREE_HUNDRED_FIFTY, TWO_TWENTY, EIGHTY);


        final Font bigFont = new Font("Arial", Font.BOLD, THIRTY_SIX); // 50 px size
        theGraphics.setFont(bigFont);

        theGraphics.setColor(Color.WHITE);
        theGraphics.drawString("Game Over!", FIFTY_THREE, FOUR_HUNDRED);
    }

    private void drawFrozenBlocks(final Graphics theGraphics) {
        for (int column = 0; column < COLUMNS; column++) {
            for (int row = 0; row < ROWS; row++) {
                final Block block = myFrozen.blocks().get(row)[column];

                if (block != null) {
                    final Color blockColor = ColorSchemeFactory.getBlockColor(block);
                    theGraphics.setColor(blockColor);

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

                g2d.setPaint(ColorSchemeFactory.getBlockColor(piece.block()));
                theGraphics.fillRect(x, y, myBlockWidth, myBlockHeight);
                // TODO: maybe we could remove this and have the grid drawn after the blocks - RB
                g2d.setPaint(Color.BLACK);
                g2d.drawRect(x, y, myBlockWidth, myBlockHeight);
            }
        }
    }

    @Override
    public void propertyChange(final PropertyChangeEvent theEvent) {
        switch (theEvent.getPropertyName()) {
            case TetrisGame.PROPERTY_CURRENT_PIECE -> {
                myTetrisPieces[0] = (IndividualPiece) theEvent.getNewValue();
                repaint();
            }
            case TetrisGame.PROPERTY_FROZEN_BLOCKS -> {
                myFrozen = (GameControls.FrozenBlocks) theEvent.getNewValue();
                repaint();
            }
            case ColorSchemeFactory.PROPERTY_COLOR_SCHEME -> {
                setBackground(ColorSchemeFactory.getCurrentPrimaryColor());
                repaint();
            }

            case TetrisGame.PROPERTY_GAME_STATE -> {
                switch (theEvent.getNewValue()) {
                    case GameState.NEW -> {
                        myTetrisPieces = new IndividualPiece[1];
                        myGameOverDeath = false;
                        if (myAnimator.isRunning()) {
                            myAnimator.stop();
                            setBackground(ColorSchemeFactory.getCurrentPrimaryColor());
                        }
                        repaint();
                    }
                    case GameState.OVER -> {
                        myGameOverDeath = true;
                        if (!myAnimator.isRunning()) {
                            myAnimator.start();
                        }
                        repaint();
                    }
                    default -> { }
                }
            }

            default -> { }
        }

    }

    private final class BackGroundColorAnimator implements ActionListener {


        public void actionPerformed(final ActionEvent theEvent) {
            if (!myFlashColor) {
                setBackground(new Color(ONE_EIGHT_TWO, ONE_HUNDRED_THREE,
                        ONE_HUNDRED_THREE, ONE_HUNDRED_SIXTY_NINE));
            } else {
                setBackground(ColorSchemeFactory.getCurrentPrimaryColor());
            }
            myFlashColor = !myFlashColor;
        }
    }

}
