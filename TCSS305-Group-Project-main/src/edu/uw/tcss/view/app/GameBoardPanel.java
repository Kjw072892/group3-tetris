package edu.uw.tcss.view.app;
import static edu.uw.tcss.model.GameControls.GameState;

import edu.uw.tcss.model.GameControls;
import edu.uw.tcss.model.GameControls.Block;
import edu.uw.tcss.model.GameControls.IndividualPiece;
import edu.uw.tcss.model.GameControls.Point;
import edu.uw.tcss.model.TetrisGame;
import edu.uw.tcss.view.app.assets.AssetsManager;
import edu.uw.tcss.view.util.ColorSchemeManager;
import edu.uw.tcss.view.util.DrawingFactory;
import edu.uw.tcss.view.util.GraphicsHandler;
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
 * The gameBoard class construct and manages the Tetris game board.
 * @author Zainab
 * @author James
 * @author Kassie
 * @author Roman
 * @version 3.7.25
 */
public class GameBoardPanel extends JPanel implements PropertyChangeListener {

    private static final int COLUMNS = 10;
    private static final int ROWS = 20;
    private static final int GAME_OVER_Y_POSITION = 400;
    private static final int GAME_OVER_BOX_WIDTH = 220;
    private static final int GAME_OVER_BOX_X = 45;
    private static final int GAME_OVER_BOX_Y = 350;
    private static final int GAME_OVER_FONT_SIZE = 36;
    private static final int GAME_OVER_TEXT_X = 53;
    private static final int GAME_OVER_TEXT_Y = 10;
    private static final int SPARKLE_COUNT = 4;
    private static final int SPARKLE_SIZE = 3;
    private static final int BACKGROUND_ANIMATION_DELAY = 1000;
    private static final Color FLASH_COLOR = new Color(182, 103, 103, 169);
    private final int myBlockWidth;
    private final int myBlockHeight;
    private IndividualPiece[] myTetrisPieces;
    private boolean myGameOverDeath;
    private boolean myFlashColor;
    private final ImageIcon myDeathIcon;
    private final Timer myAnimator;
    private GameControls.FrozenBlocks myFrozen = Sprint1_values.frozenBlocks();

    {
        myTetrisPieces = Sprint1_values.pieces();
    }

    /**
     * Constructs the game board.
     *
     * @param theWidth  Width of game board.
     * @param theHeight Height of game board.
     */
    public GameBoardPanel(final int theWidth, final int theHeight) {
        /* Preferred size set and color to fit in layout. */
        setPreferredSize(new Dimension(theWidth, theHeight));
        setBackground(Color.RED);

        myBlockWidth = theWidth / COLUMNS;
        myBlockHeight = theHeight / ROWS;
        myDeathIcon = new ImageIcon(
                AssetsManager.getFilePath(AssetsManager.IMAGES_PATH, "oof-noob.gif"));
        myAnimator = new Timer(BACKGROUND_ANIMATION_DELAY, new BackGroundColorAnimator());
    }

    /** Paints the board grid lines and pieces. */
    @Override
    protected void paintComponent(final Graphics theGraphics) {
        super.paintComponent(theGraphics);
        final Graphics2D g2d = GraphicsHandler.enableAntiAliasingAndReturn(theGraphics);

        drawFrozenBlocks(g2d);
        drawPiece(g2d);
        drawGrid(g2d);
        if (myGameOverDeath) {
            drawGameOver(g2d);
        }
    }

    /** Draws the Game Over screen. */
    private void drawGameOver(final Graphics2D theGraphics) {
        theGraphics.drawImage(myDeathIcon.getImage(),
                (getWidth() - myDeathIcon.getIconWidth()) / 2,
                getHeight() / GAME_OVER_TEXT_Y, this);
        theGraphics.setColor(Color.BLACK);
        theGraphics.fillRect(GAME_OVER_BOX_X, GAME_OVER_BOX_Y,
                GAME_OVER_BOX_WIDTH, GAME_OVER_FONT_SIZE);

        final Font bigFont = new Font("Arial", Font.BOLD, GAME_OVER_FONT_SIZE); // 50px size
        theGraphics.setFont(bigFont);
        theGraphics.setColor(Color.WHITE);
        theGraphics.drawString("Game Over!", GAME_OVER_TEXT_X, GAME_OVER_Y_POSITION);
    }


    /** Draws sparkles for Pink Mode.*/
    private void drawSparkles(final Graphics2D theGraphics, final int theX, final int theY,
                              final int theWidth, final int theHeight) {
        theGraphics.setColor(Color.WHITE);
        for (int i = 0; i < SPARKLE_COUNT; i++) {
            final int sparkleX = theX + (int) (Math.random() * theWidth);
            final int sparkleY = theY + (int) (Math.random() * theHeight);

            // Different shapes
            if (i % 2 == 0) {
                theGraphics.fillOval(sparkleX, sparkleY, SPARKLE_SIZE, SPARKLE_SIZE);
            } else {
                theGraphics.fillRect(sparkleX, sparkleY, SPARKLE_SIZE, SPARKLE_SIZE);
            }
        }
    }


    private void draw3DBlocks(final Graphics2D theGraphics2D, final int theX, final int theY,
                              final int theWidth, final int theHeight, final Color theBaseColor) {

        DrawingFactory.drawGlossy(theGraphics2D, theX, theY, theWidth, theHeight, theBaseColor);

        if (ColorSchemeManager.getCurrentColorScheme().name().contains("Pink Mode \uD83C\uDF80✨")) {
            drawSparkles(theGraphics2D, theX, theY, theWidth, theHeight);

        }
    }

    private void drawFrozenBlocks(final Graphics theGraphics) {
        final Graphics2D g2d = GraphicsHandler.enableAntiAliasingAndReturn(theGraphics);


        for (int column = 0; column < COLUMNS; column++) {
            for (int row = 0; row < ROWS; row++) {
                final Block block = myFrozen.blocks().get(row)[column];

                if (block == null) {
                    continue;
                }
                final Color blockColor = ColorSchemeManager.getBlockColor(block);
                if (blockColor == null) {
                    continue;
                }

                final int x = column * myBlockWidth;
                final int y = ((ROWS - 1) - row) * myBlockHeight;

                draw3DBlocks(g2d, x, y, myBlockWidth, myBlockHeight, blockColor);
            }
        }
    }

    /*** Drawing the grid, 10 columns, 20 rows. */
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

    private void drawPiece(final Graphics theGraphics) {
        for (IndividualPiece piece : myTetrisPieces) {
            if (piece == null) {
                continue;
            }

            if (piece.block() == null) {
                continue;
            }
            for (Point block : piece.location()) {
                final int x = block.x() * myBlockWidth;
                final int y = ((ROWS - 1) - block.y()) * myBlockHeight;

                Color blockColor = ColorSchemeManager.getBlockColor(piece.block());
                if (blockColor == null) {
                    blockColor = Color.BLACK;
                }
                draw3DBlocks((Graphics2D) theGraphics, x, y,
                           myBlockWidth, myBlockHeight, blockColor);
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
            case ColorSchemeManager.PROPERTY_COLOR_SCHEME -> {
                setBackground(ColorSchemeManager.getCurrentPrimaryColor());
                repaint();
            }

            case TetrisGame.PROPERTY_GAME_STATE -> {
                switch (theEvent.getNewValue()) {
                    case GameState.NEW -> {
                        myTetrisPieces = new IndividualPiece[1];
                        myGameOverDeath = false;
                        if (myAnimator.isRunning()) {
                            myAnimator.stop();
                            setBackground(ColorSchemeManager.getCurrentPrimaryColor());
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


                    case GameState.WORRY -> {
                        Color currentColor = ColorSchemeManager.getCurrentPrimaryColor();
                        currentColor = currentColor.darker();
                        setBackground(currentColor);
                    }

                    case GameState.PANIC -> {
                        Color currentColor = ColorSchemeManager.getCurrentPrimaryColor();
                        currentColor = currentColor.darker();
                        currentColor = currentColor.darker();
                        setBackground(currentColor);
                    }

                    default -> {
                        setBackground(ColorSchemeManager.getCurrentPrimaryColor());
                    }
                }
            }
        }
    }
    private final class BackGroundColorAnimator implements ActionListener {
        public void actionPerformed(final ActionEvent theEvent) {
            if (!myFlashColor) {
                setBackground(FLASH_COLOR);
            } else {
                setBackground(ColorSchemeManager.getCurrentPrimaryColor());
            }
            myFlashColor = !myFlashColor;
        }
    }

}
