package edu.uw.tcss.view.app;

import static edu.uw.tcss.model.GameControls.GameState;

import edu.uw.tcss.model.GameControls;
import edu.uw.tcss.model.GameControls.Block;
import edu.uw.tcss.model.GameControls.IndividualPiece;
import edu.uw.tcss.model.GameControls.Point;
import edu.uw.tcss.model.TetrisGame;
import edu.uw.tcss.view.app.assets.AssetsManager;
import edu.uw.tcss.view.util.ColorSchemeManager;
import edu.uw.tcss.view.util.GraphicsHandler;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GradientPaint;
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

    //Properties of the board & blocks.
    private static final int COLUMNS = 10;         // Number of columns & rows.
    private static final int ROWS = 20;
    private static final int COLORTOFLASH = 0xa9b66767;
    private static final int DELAY = 1000;
    private static final int TEXT_SIZE_GAME_OVER = 36;
    private static final int TEXT_GAME_OVER_X = TEXT_SIZE_GAME_OVER;
    private static final int BLACKSCREEN_X = 45;
    private static final int BLACKSCREEN_Y = 350;
    private static final int WIDTH1 = 220;
    private static final int HEIGHT1 = 80;
    private static final int TEXT_Y = 400;
    private static final int PAUSE_SIZE = 30;
    private static final int TEXT_GAMEPAUSED_X = 51;
    private static final int DIVISOR_FOR_GIFDEATH = 10;
    private static final int HALF = 2;
    private static final String TEXT_FONT = "Arial";
    private static final int SPARKLE_AMOUNT = 4;
    private final int myBlockWidth;
    private final int myBlockHeight;
    private IndividualPiece[] myTetrisPieces;
    private boolean myGameOverDeath;

    private boolean myGamePaused;

    private boolean myFlashColor;

    private final ImageIcon myDeathIcon;

    private final Timer myAnimator;

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
        myDeathIcon = new ImageIcon(
                AssetsManager.getFilePath(AssetsManager.IMAGES_PATH, "oof-noob.gif"));
        myAnimator = new Timer(DELAY, new BackGroundColorAnimator());

    }

    private void draw3DBlocks(final Graphics2D theGraphics, final int theX, final int theY,
                              final int theWidth, final int theHeight, final Color theBaseColor) {
        final int offset = theWidth / 6;


        final boolean isPinkMode = ColorSchemeManager.getCurrentColorScheme().
                name().contains("Pink Mode");

        // Defining the color shades
        final Color lighterShade = theBaseColor.brighter().brighter();
        final Color darkerShade = theBaseColor.darker();


        final GradientPaint topLeft = new GradientPaint(
                theX, theY, lighterShade, theX + theWidth, theY + theHeight, theBaseColor);

        final GradientPaint bottomRight = new GradientPaint(
                theX, theY, theBaseColor, theX + theWidth, theY + theHeight, darkerShade);

        //draw base block
        theGraphics.setPaint(topLeft);
        theGraphics.fillRect(theX, theY, theWidth, theHeight);

        theGraphics.setPaint(bottomRight);
        theGraphics.fillRect(theX + offset, theY + offset, theWidth - offset, theHeight - offset);


        //glossy effect
        final GradientPaint gloss = new GradientPaint(theX, theY, Color.WHITE, theX + offset,
                theY + offset, new Color(255, 255, 255, 50));
        theGraphics.setPaint(gloss);
        theGraphics.fillRect(theX, theY, theWidth, theHeight);


        // if pink mode is enabled, add sparkles!
        if (isPinkMode) {
            drawSparkles(theGraphics, theX, theY, theWidth, theHeight);
        }

        //outline
        theGraphics.setColor(Color.BLACK);
        theGraphics.drawRect(theX, theY, theWidth, theHeight);

    }
    // Sparkle effect only in Pink Mode.
    private void drawSparkles(final Graphics2D theGraphics, final int theX,
                              final int theY, final int theWidth, final int theHeight) {
        theGraphics.setColor(Color.WHITE);
        for (int i = 0; i < SPARKLE_AMOUNT; i++) { //Draw 4 tiny sparkles
            final int sparkleX = theX + (int) (Math.random() * theWidth);
            final int sparkleY = theY + (int) (Math.random() * theHeight);
            final int sparkleSize = 3;

            // Different shapes
            if (i % 2 == 0) {
                theGraphics.fillOval(sparkleX, sparkleY, sparkleSize, sparkleSize); // small circles
            } else {
                theGraphics.fillRect(sparkleX, sparkleY, sparkleSize, sparkleSize); // small squares
            }

        }
    }

    /**
     * Paints the board grid lines and pieces.
     *
     * @param theGraphics the Graphics object for drawing.
     */
    @Override
    protected void paintComponent(final Graphics theGraphics) {
        super.paintComponent(theGraphics);
        final Graphics2D g2d = GraphicsHandler.enableAntiAliasingAndReturn(theGraphics);

        drawFrozenBlocks(g2d);
        drawPiece(g2d); // Draws all Sprint 1 pieces on board.
        drawGrid(g2d); //draw the grid lines on the board.
        if (myGameOverDeath) {
            drawGameOver(g2d);
        }

        if (myGamePaused) {
            drawPaused(g2d);
        }
    }
    // TODO: magic numbers
    private void drawGameOver(final Graphics2D theGraphics) {
        theGraphics.drawImage(myDeathIcon.getImage(),
                (getWidth() - myDeathIcon.getIconWidth()) / HALF,
                getHeight() / DIVISOR_FOR_GIFDEATH,
                this);
        theGraphics.setColor(Color.BLACK);
        theGraphics.fillRect(BLACKSCREEN_X, BLACKSCREEN_Y, WIDTH1, HEIGHT1);


        final Font bigFont = new Font("Arial", Font.BOLD, TEXT_SIZE_GAME_OVER);
        theGraphics.setFont(bigFont);

        theGraphics.setColor(Color.WHITE);
        theGraphics.drawString("Game Over!", TEXT_GAME_OVER_X, TEXT_Y);
    }

    private void drawPaused(final Graphics2D theGraphics) {
        theGraphics.setColor(Color.BLACK);
        theGraphics.fillRect(BLACKSCREEN_X, BLACKSCREEN_Y, WIDTH1, HEIGHT1);


        final Font bigFont = new Font(TEXT_FONT, Font.BOLD, PAUSE_SIZE);
        theGraphics.setFont(bigFont);

        theGraphics.setColor(Color.WHITE);
        theGraphics.drawString("Game Paused!", TEXT_GAMEPAUSED_X, TEXT_Y);
    }

    private void drawFrozenBlocks(final Graphics theGraphics) {
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
                draw3DBlocks((Graphics2D) theGraphics, x, y,
                        myBlockWidth, myBlockHeight, blockColor);

                if (ColorSchemeManager.getCurrentColorScheme().
                        name().contains("Pink Mode \uD83C\uDF80✨")) {
                    drawSparkles((Graphics2D) theGraphics, x, y, myBlockWidth, myBlockHeight);
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

            case TetrisGame.PROPERTY_GAME_STATE -> gameStateSwitches(theEvent);

            default -> { }
        }

    }

    private void gameStateSwitches(final PropertyChangeEvent theEvent) {

        int test = 0;

        switch (theEvent.getNewValue()) {
            case GameState.NEW -> {
                myGamePaused = false;
                myTetrisPieces = new IndividualPiece[1];
                myGameOverDeath = false;
                if (myAnimator.isRunning()) {
                    myAnimator.stop();
                    setBackground(ColorSchemeManager.getCurrentPrimaryColor());
                }
                repaint();
            }
            case GameState.OVER -> {
                myGamePaused = false;
                myGameOverDeath = true;
                if (!myAnimator.isRunning()) {
                    myAnimator.start();
                }
                repaint();
            }


            case GameState.WORRY -> {
                checkPaused();
                Color currentColor = ColorSchemeManager.getCurrentPrimaryColor();
                currentColor = currentColor.darker();
                setBackground(currentColor);
            }

            case GameState.PANIC -> {
                checkPaused();
                Color currentColor = ColorSchemeManager.getCurrentPrimaryColor();
                currentColor = currentColor.darker();
                currentColor = currentColor.darker();
                setBackground(currentColor);
            }

            case GameState.RUNNING -> {
                checkPaused();
                setBackground(ColorSchemeManager.getCurrentPrimaryColor());
            }

            case GameState.PAUSED -> {
                myGamePaused = true;
                repaint();
            }

            default -> { }
        }
    }

    private void checkPaused() {
        if (myGamePaused) {
            repaint();
            myGamePaused = false;
        }
    }

    private final class BackGroundColorAnimator implements ActionListener {
        public void actionPerformed(final ActionEvent theEvent) {
            if (!myFlashColor) {
                setBackground(new Color(COLORTOFLASH, true));
            } else {
                setBackground(ColorSchemeManager.getCurrentPrimaryColor());
            }
            myFlashColor = !myFlashColor;
        }
    }

}
