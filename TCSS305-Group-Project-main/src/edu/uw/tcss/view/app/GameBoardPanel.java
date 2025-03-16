package edu.uw.tcss.view.app;

import static edu.uw.tcss.model.GameControls.GameState;

import edu.uw.tcss.model.GameControls;
import edu.uw.tcss.model.GameControls.Block;
import edu.uw.tcss.model.GameControls.IndividualPiece;
import edu.uw.tcss.model.GameControls.Point;
import edu.uw.tcss.model.TetrisGame;
import edu.uw.tcss.view.app.assets.AssetsManager;
import edu.uw.tcss.view.util.ColorSchemeFactory;
import edu.uw.tcss.view.util.ColorSchemeManager;
import edu.uw.tcss.view.util.DrawingFactory;
import edu.uw.tcss.view.util.DrawingManager;
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
import java.util.List;
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

    // for starter screen
    private static final GameControls.FrozenBlocks FROZEN_STARTERS =
        new GameControls.FrozenBlocks(List.of(
            new GameControls.Block[]{null, Block.I, Block.I,
                Block.J, Block.Z, Block.T, Block.T, Block.T, Block.Z, Block.Z},
            new Block[]{null, Block.I, Block.I, Block.J,
                Block.Z, Block.Z, Block.T, Block.Z, Block.Z, null},
            new Block[]{null, Block.I, Block.I, Block.J, Block.J,
                Block.Z, Block.L, Block.L, Block.L, Block.L},
            new Block[]{null, Block.I, Block.I, Block.J, Block.J,
                Block.J, Block.Z, Block.Z, Block.L, Block.L},
            new Block[]{null, Block.T, Block.J, Block.J, Block.J,
                Block.J, Block.S, Block.S, Block.L, Block.L},
            new Block[]{Block.O, Block.O, null, Block.J, Block.J,
                Block.J, Block.S, null, Block.L, null},
            new Block[]{Block.O, Block.O, null, null, null, null, null, null, Block.L, null},
            new Block[]{null, null, null, null, null, null, null, null, null, null},
            new Block[]{null, null, null, null, null, null, null, null, null, null},
            new Block[]{null, null, null, null, null, null, null, null, null, null},
            new Block[]{null, null, null, null, null, null, null, null, null, null},
            new Block[]{null, null, null, null, null, null, null, null, null, null},
            new Block[]{null, null, null, null, null, null, null, null, null, null},
            new Block[]{null, null, null, null, null, null, null, null, null, null},
            new Block[]{null, null, null, null, null, null, null, null, null, null},
            new Block[]{null, null, null, null, null, null, null, null, null, null},
            new Block[]{null, null, null, null, null, null, null, null, null, null},
            new Block[]{null, null, null, null, null, null, null, null, null, null},
            new Block[]{null, null, null, null, null, null, null, null, null, null},
            new Block[]{null, null, null, null, null, null, null, null, null, null}));

    private static final IndividualPiece[] PIECES_STARTER = new IndividualPiece[]{
        new IndividualPiece(new GameControls.Point[]{new Point(9, 17),
            new Point(7, 16), new Point(8, 16), new Point(9, 16)}, Block.L),
        new IndividualPiece(new Point[]{new Point(5, 17), new Point(6, 17),
            new Point(5, 16), new Point(6, 16)}, Block.O),
        new IndividualPiece(new Point[]{new Point(2, 16), new Point(3, 16),
            new Point(3, 15), new Point(4, 15)}, Block.Z),
        new IndividualPiece(new Point[]{new Point(0, 16), new Point(0, 15),
            new Point(0, 14), new Point(0, 13)}, Block.I),
        new IndividualPiece(new Point[]{new Point(7, 13), new Point(7, 12),
            new Point(8, 12), new Point(9, 12)}, Block.J),
        new IndividualPiece(new Point[]{new Point(1, 13), new Point(0, 12),
            new Point(1, 12), new Point(2, 12)}, Block.T),
        new IndividualPiece(new Point[]{new Point(5, 12), new Point(4, 12),
            new Point(6, 13), new Point(5, 13)}, Block.S)
    };

    //Properties of the board & blocks.
    private static final int COLUMNS = 10;         // Number of columns & rows.
    private static final int ROWS = 20;
    private static final int COLOR_TO_FLASH = 0xa9b66767;
    private static final int DELAY = 1000;

    // TODO: this is a misuse of constants
    private static final int TEXT_SIZE_GAME_OVER = 36;
    private static final int TEXT_GAME_OVER_X = 53;
    private static final int BLACK_SCREEN_X = 45;
    private static final int BLACK_SCREEN_Y = 350;
    private static final int WIDTH1 = 220;
    private static final int HEIGHT1 = 80;
    private static final int TEXT_Y = 400;
    private static final int PAUSE_SIZE = 30;
    private static final int TEXT_GAME_PAUSED_X = 51;
    private static final int DIVISOR_FOR_GIF_DEATH = 10;
    private static final int HALF = 2;

    private static final String TEXT_FONT = "Arial";
    private final int myBlockWidth;
    private final int myBlockHeight;
    private IndividualPiece[] myTetrisPieces;
    private boolean myGameOverDeath;

    private boolean myGamePaused;

    private boolean myFlashColor;

    private final ImageIcon myDeathIcon;

    private final Timer myAnimator;

    private GameControls.FrozenBlocks myFrozen = FROZEN_STARTERS;

    {
        myTetrisPieces = PIECES_STARTER;   // Store Pieces in myTetrisPiece
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
                getHeight() / DIVISOR_FOR_GIF_DEATH,
                this);
        theGraphics.setColor(Color.BLACK);
        theGraphics.fillRect(BLACK_SCREEN_X, BLACK_SCREEN_Y, WIDTH1, HEIGHT1);


        final Font bigFont = new Font(TEXT_FONT, Font.BOLD, TEXT_SIZE_GAME_OVER);
        theGraphics.setFont(bigFont);

        theGraphics.setColor(Color.WHITE);
        theGraphics.drawString("Game Over!", TEXT_GAME_OVER_X, TEXT_Y);
    }

    private void drawPaused(final Graphics2D theGraphics) {
        theGraphics.setColor(Color.BLACK);
        theGraphics.fillRect(BLACK_SCREEN_X, BLACK_SCREEN_Y, WIDTH1, HEIGHT1);


        final Font bigFont = new Font(TEXT_FONT, Font.BOLD, PAUSE_SIZE);
        theGraphics.setFont(bigFont);

        theGraphics.setColor(Color.WHITE);
        theGraphics.drawString("Game Paused!", TEXT_GAME_PAUSED_X, TEXT_Y);
    }

    private void drawFrozenBlocks(final Graphics2D theGraphics) {
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

                DrawingManager.getDrawer().drawBlock(theGraphics,
                        x, y,
                        myBlockWidth, myBlockHeight,
                        blockColor);

                if (ColorSchemeFactory.getPinkModeColors()
                        .equals(ColorSchemeManager.getCurrentColorScheme())) {
                    DrawingFactory.drawSparkles(theGraphics, x, y, myBlockWidth, myBlockHeight);
                }
            }
        }
    }


    /**
     * Drawing the grid, 10 columns, 20 rows.
     *
     * @param theGraphics graphics for object drawing.
     */
    private void drawGrid(final Graphics2D theGraphics) {
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
    private void drawPiece(final Graphics2D theGraphics) {


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
                DrawingManager.getDrawer().drawBlock(theGraphics, x, y,
                        myBlockWidth, myBlockHeight, blockColor);

                if (ColorSchemeFactory.getPinkModeColors()
                        .equals(ColorSchemeManager.getCurrentColorScheme())) {
                    DrawingFactory.drawSparkles(theGraphics, x, y,
                            myBlockWidth, myBlockHeight);
                }

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
            case TetrisGame.PROPERTY_GAME_STATE ->
                    gameStateSwitches((GameState) theEvent.getNewValue());
            default -> { }
        }

    }

    private void gameStateSwitches(final GameState theState) {
        switch (theState) {
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
                setBackground(new Color(COLOR_TO_FLASH, true));
            } else {
                setBackground(ColorSchemeManager.getCurrentPrimaryColor());
            }
            myFlashColor = !myFlashColor;
        }
    }

}
