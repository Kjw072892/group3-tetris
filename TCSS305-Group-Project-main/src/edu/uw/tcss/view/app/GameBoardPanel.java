package edu.uw.tcss.view.app;

import static edu.uw.tcss.model.GameControls.GameState;

import edu.uw.tcss.model.GameControls;
import edu.uw.tcss.model.GameControls.Block;
import edu.uw.tcss.model.GameControls.IndividualPiece;
import edu.uw.tcss.model.GameControls.Point;
import edu.uw.tcss.model.TetrisGame;
import edu.uw.tcss.view.app.assets.AssetsManager;
import edu.uw.tcss.view.util.ColorSchemeManager;
import edu.uw.tcss.view.util.DrawingManager;
import edu.uw.tcss.view.util.GraphicsHandler;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
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
            new Block[]{null, null, null, null, null, null, null, null, null, null},
            new Block[]{null, null, null, null, null, null, null, null, null, null},
            new Block[]{null, null, null, null, null, null, null, Block.J, Block.J, null},
            new Block[]{null, null, null, null, null, null, null, null, Block.J, null},
            new Block[]{null, null, null, null, null, null, null, null, Block.J, null},
            new Block[]{null, null, Block.L, null, Block.T, Block.T, Block.T, null, Block.J, null},
            new Block[]{Block.I, null, Block.L, null, null, Block.T, null, null, Block.J, null},
            new Block[]
                {Block.I, Block.L, Block.L, null, null, Block.T, null, null, Block.J, Block.J},
            new Block[]
                {Block.I, Block.O, Block.O, null, Block.T, Block.T, Block.T, null, null, null},
            new Block[]{Block.I, Block.O, Block.O, null, null, null, null, null, null, null},
            new Block[]{null, null,    null, null, null, null, null, null, null, null},
            new Block[]{null, null,    null, Block.J, Block.J, Block.J, null, Block.I, null, null},
            new Block[]{null, null,    null, Block.J, null, null, null, Block.I, null, null},
            new Block[]{null, Block.I, null, Block.T, null, null, null, Block.I, null, null},
            new Block[]{null, Block.I, null, Block.T, Block.T, null, null, Block.I, null, null},
            new Block[]{null, Block.I, null, Block.T, null, null, null, Block.T, null, null},
            new Block[]{null, Block.I, null, Block.L, null, null, Block.T, Block.T, Block.T, null},
            new Block[]{null, Block.T, null, Block.L, Block.L, Block.L, null, null, null, null},
            new Block[]{Block.T, Block.T, Block.T, null, null, null, null, null, null, null},
            new Block[]{null, null, null, null, null, null, null, null, null, null}));

    private static final IndividualPiece[] PIECES_STARTER = new IndividualPiece[]{
        new IndividualPiece(new GameControls.Point[]{new Point(9, 19),
            new Point(7, 18), new Point(8, 18), new Point(9, 18)}, Block.L),
        new IndividualPiece(new GameControls.Point[]{new Point(0, 2),
            new Point(0, 1), new Point(1, 1), new Point(2, 1)}, Block.J),
        new IndividualPiece(new GameControls.Point[]{new Point(4, 0),
            new Point(5, 0), new Point(4, 1), new Point(5, 1)}, Block.O),
        new IndividualPiece(new GameControls.Point[]{new Point(2, 3),
            new Point(3, 3), new Point(4, 3), new Point(5, 3)}, Block.I)
    };

    //Properties of the board & blocks.
    private static final int COLUMNS = 10;         // Number of columns & rows.
    private static final int ROWS = 20;
    private static final int DELAY = 1000;

    private static final int BLACK_SCREEN_Y = 350;
    private static final int BLACK_SCREEN_HEIGHT = 80;
    private static final int DIVISOR_FOR_GIF_DEATH = 10;

    private static final Font BIG_FONT = new Font("Arial", Font.BOLD, 33);

    private final int myBlockWidth;
    private final int myBlockHeight;

    private GameState myLastGameState;

    private final ImageIcon myDeathIcon;

    private final Timer myAnimator;

    private IndividualPiece[] myTetrisPieces = PIECES_STARTER;
    private GameControls.FrozenBlocks myFrozen = FROZEN_STARTERS;

    /**
     * Constructs the game board.
     */
    public GameBoardPanel(final int theWidth, final int theHeight) {
        //Preferred size set to fit in layout.
        setPreferredSize(new Dimension(theWidth, theHeight));

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
        if (GameState.OVER.equals(myLastGameState)) {
            drawGameOver(g2d);
        }

        if (GameState.PAUSED.equals(myLastGameState)) {
            drawPaused(g2d);
        }
    }
    private void drawGameOver(final Graphics2D theGraphics) {
        theGraphics.drawImage(myDeathIcon.getImage(),
                (getWidth() - myDeathIcon.getIconWidth()) / 2,
                getHeight() / DIVISOR_FOR_GIF_DEATH,
                this);
        theGraphics.setColor(Color.BLACK);
        theGraphics.fillRect(0, BLACK_SCREEN_Y, getWidth(), BLACK_SCREEN_HEIGHT);

        theGraphics.setFont(BIG_FONT);

        theGraphics.setColor(Color.WHITE);

        final String gameOverString = "Game Over!";
        final Point stringPoint = getStringPositionOnBlackScreen(theGraphics, gameOverString);
        theGraphics.drawString(gameOverString, stringPoint.x(), stringPoint.y());

    }

    private void drawPaused(final Graphics2D theGraphics) {
        theGraphics.setColor(Color.BLACK);
        theGraphics.fillRect(0, BLACK_SCREEN_Y, getWidth(), BLACK_SCREEN_HEIGHT);

        theGraphics.setFont(BIG_FONT);

        theGraphics.setColor(Color.WHITE);

        final String gamePausedString = "Game Paused!";
        final Point stringPoint = getStringPositionOnBlackScreen(theGraphics, gamePausedString);
        theGraphics.drawString(gamePausedString, stringPoint.x(), stringPoint.y());

    }

    private Point getStringPositionOnBlackScreen(final Graphics2D theGraphics,
                                                 final String theString) {
        final FontMetrics metrics = theGraphics.getFontMetrics(BIG_FONT);

        final int stringWidth = metrics.stringWidth(theString);
        final int stringX = (getWidth() - stringWidth) / 2;

        final int stringHeight = metrics.getAscent();
        final int stringY = BLACK_SCREEN_Y + (BLACK_SCREEN_HEIGHT + stringHeight) / 2;

        return new Point(stringX, stringY);
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

                DrawingManager.getDrawer().drawingObject().drawBlock(theGraphics,
                        x, y,
                        myBlockWidth, myBlockHeight,
                        blockColor);
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
        theGraphics.setStroke(new BasicStroke(1));
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
            if (piece != null && piece.block() != null) {
                for (Point block : piece.location()) {
                    final int x = block.x() * myBlockWidth;
                    final int y = ((ROWS - 1) - block.y()) * myBlockHeight;

                    Color blockColor = ColorSchemeManager.getBlockColor(piece.block());
                    if (blockColor == null) {
                        blockColor = Color.BLACK;
                    }

                    DrawingManager.getDrawer().drawingObject().drawBlock(theGraphics,
                            x, y,
                            myBlockWidth, myBlockHeight,
                            blockColor);

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
            case TetrisGame.PROPERTY_GAME_STATE -> {
                gameStateSwitches((GameState) theEvent.getNewValue());
                myLastGameState = (GameState) theEvent.getNewValue();
            }
            case DrawingManager.PROPERTY_DRAWING_OBJECT -> repaint();
            default -> { }
        }

    }

    private void gameStateSwitches(final GameState theState) {
        switch (theState) {
            case GameState.NEW -> {
                myTetrisPieces = new IndividualPiece[1];
                if (myAnimator.isRunning()) {
                    myAnimator.stop();
                    setBackground(ColorSchemeManager.getCurrentPrimaryColor());
                }
                repaint();
            }
            case GameState.OVER -> {
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

            case GameState.PAUSED -> repaint();

            default -> { }
        }
    }

    private void checkPaused() {
        if (GameState.PAUSED.equals(myLastGameState)) {
            repaint();
        }
    }

    private final class BackGroundColorAnimator implements ActionListener {

        private static final int COLOR_TO_FLASH = 0xa9b66767;
        private boolean myFlashColor;

        public void actionPerformed(final ActionEvent theEvent) {
            if (!myFlashColor) {
                setBackground(new Color(COLOR_TO_FLASH));
            } else {
                setBackground(ColorSchemeManager.getCurrentPrimaryColor());
            }
            myFlashColor = !myFlashColor;
        }
    }

}
