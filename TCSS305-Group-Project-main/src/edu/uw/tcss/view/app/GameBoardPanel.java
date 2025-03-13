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

import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import javax.swing.*;


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
    private boolean myGameOverDeath;

    private boolean myFlashColor;

    private final ImageIcon myDeathIcon;

    private final Timer myAnimator;

    private GameControls.FrozenBlocks myFrozen = Sprint1_values.frozenBlocks();
    private GraphicsHandler GraphicsModifier;

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
        myAnimator = new Timer(1000, new BackGroundColorAnimator());
    }

    private void draw3DBlocks(final Graphics2D graphics2D, final int x, final int y, final int width, final int height, final Color baseColor) {
        int offset = width / 6;


        boolean isPinkMode = ColorSchemeManager.getCurrentColorScheme().name().contains("Pink Mode");

        // Defining the color shades
        Color lighterShade = baseColor.brighter().brighter();
        Color darkerShade = baseColor.darker();


        GradientPaint topLeft = new GradientPaint(
                x, y, lighterShade, x + width, y + height, baseColor);

        GradientPaint bottomRight = new GradientPaint(
                x, y, baseColor, x + width, y + height, darkerShade);

        //draw base block
        graphics2D.setPaint(topLeft);
        graphics2D.fillRect(x, y, width, height);

        graphics2D.setPaint(bottomRight);
        graphics2D.fillRect(x + offset, y + offset, width - offset, height - offset);


        //glossy effect
        GradientPaint gloss = new GradientPaint(x, y, Color.WHITE, x + offset, y + offset, new Color(255, 255, 255, 50));
        graphics2D.setPaint(gloss);
        graphics2D.fillRect(x, y, width, height);


        // if pink mode is enabled, add sparkles!
        if (isPinkMode) {
            drawSparkles(graphics2D, x, y, width, height);
        }

        //outline
        graphics2D.setColor(Color.BLACK);
        graphics2D.drawRect(x, y, width, height);

    }
    // Sparkle effect only in Pink Mode.
    private void drawSparkles(final Graphics2D graphics2D, final int x, final int y, final int width, final int height) {
        graphics2D.setColor(Color.WHITE);
        for (int i = 0; i < 4; i++) { //Draw 4 tiny sparkles
            int sparkleX = x + (int) (Math.random() * width);
            int sparkleY = y + (int) (Math.random() * height);
            int sparkleSize = 3;

            // Different shapes
            if (i % 2 == 0) {
                graphics2D.fillOval(sparkleX, sparkleY, sparkleSize, sparkleSize); // small circles
            } else {
                graphics2D.fillRect(sparkleX, sparkleY, sparkleSize, sparkleSize); // small squares
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
        final Graphics2D g2d = (Graphics2D) theGraphics;
        GraphicsModifier.enableAntiAliasing(g2d);

        drawFrozenBlocks(g2d);
        drawPiece(g2d); // Draws all Sprint 1 pieces on board.
        drawGrid(g2d); //draw the grid lines on the board.
        if (myGameOverDeath) {
            drawGameOver(g2d);
        }
    }
    // TODO: magic numbers
    private void drawGameOver(final Graphics2D theGraphics) {
        theGraphics.drawImage(myDeathIcon.getImage(),
                (getWidth() - myDeathIcon.getIconWidth()) / 2,
                getHeight() / 10, this);
        theGraphics.setColor(Color.BLACK);
        theGraphics.fillRect(45, 350, 220, 80);


        final Font bigFont = new Font("Arial", Font.BOLD, 36); // 50px size
        theGraphics.setFont(bigFont);

        theGraphics.setColor(Color.WHITE);
        theGraphics.drawString("Game Over!", 53, 400);
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
                draw3DBlocks((Graphics2D) theGraphics, x, y, myBlockWidth, myBlockHeight, blockColor);

                if (ColorSchemeManager.getCurrentColorScheme().name().contains("Pink Mode \uD83C\uDF80✨")) {
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
               draw3DBlocks((Graphics2D) theGraphics, x, y, myBlockWidth, myBlockHeight, blockColor);
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

                    case GameState.RUNNING -> {
                        setBackground(ColorSchemeManager.getCurrentPrimaryColor());
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
                setBackground(new Color(182, 103, 103, 169));
                myFlashColor = !myFlashColor;
            } else {
                setBackground(ColorSchemeManager.getCurrentPrimaryColor());
                myFlashColor = !myFlashColor;
            }
        }
    }

}
