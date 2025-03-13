package edu.uw.tcss.view.app;

import static edu.uw.tcss.model.PropertyChangeEnabledGameControls.PROPERTY_NEXT_PIECE;

import edu.uw.tcss.model.GameControls.IndividualPiece;
import edu.uw.tcss.model.GameControls.Point;

import edu.uw.tcss.view.util.ColorSchemeManager;
import edu.uw.tcss.view.util.DrawingFactory;
import edu.uw.tcss.view.util.DrawingObject;
import edu.uw.tcss.view.util.GraphicsHandler;

import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import javax.swing.JPanel;

/**
 * The Panel that draws the next tetris piece upcoming.
 *
 * @author James Parker Strand
 * @version 1
 */
public class NextPiecePanel extends JPanel implements PropertyChangeListener {

    // TODO: might want to refrain from hard-coding these dimensions - RB
    /** The width of the panel. */
    private static final int WIDTH = 100;

    /** The height of the panel. */
    private static final int HEIGHT = 133;

    /** The stroke width in pixels. */
    private static final int STROKE_WIDTH = 3;

    /** The width for the rectangle. */
    private static final int RECTANGLE_WIDTH = 30;

    /** The height for the rectangle. */
    private static final int RECTANGLE_HEIGHT = 30;

    private IndividualPiece myNextPiece;

    private DrawingObject myDrawer = GraphicsHandler.getCurrentDrawingObject();

    /**
     * Constructs a new ellipse panel.
     */
    public NextPiecePanel() {
        super();
        layoutComponents();
        myNextPiece = Sprint1_values.nextPiece();
    }

    /**
     * Lay out the components and makes this frame visible.
     */
    private void layoutComponents() {
        setBackground(ColorSchemeManager.getCurrentSecondaryColor());
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
    }

    /**
     * helper method for drawing the rectangle center styled,
     * rather than x to width, and y to height
     * @param theX where the rectangle goes in the x coordinate.
     * @param theY where the rectangle goes in the y coordinate.
     * @return gives you you you the rectangle object in the
     * form the rectangle is drawn in the middle.
     */

    private void createCenteredRectangle(final Graphics2D graphics2D, final int theX, final int theY, final Color theBaseColor) {
        final double topLeftX = theX - (double) 28 / 2d;
        final double topLeftY = theY - (double) 28 / 2d;
        myDrawer.drawBlock(graphics2D, (int) topLeftX, (int) topLeftY,29,29, theBaseColor);
    }

    /**
     * helper method for taking a single point, and making
     * sure to center on screen like the regular cartesian coordinate
     * plane.
     * @param thePoint the point to convert to center.
     * @return the point is now converted to cartesian coordinate point.
     */
    private Point takePointToCenter(final Point thePoint) {

        final int centeredX = (this.getWidth() / 2) + thePoint.x();

        final int centeredY = (this.getHeight() / 2) + thePoint.y();

        return new Point(centeredX, centeredY);
    }

    private double[] findOffsetPoint(final Point[] thePoints) {
        double minX = thePoints[0].x();
        double maxX = minX;
        double minY = thePoints[0].y();
        double maxY = minY;
        for (int i = 1; i < thePoints.length; i++) {
            minX = Math.min(thePoints[i].x(), minX);
            maxX = Math.max(thePoints[i].x(), maxX);
            minY = Math.min(thePoints[i].y(), minY);
            maxY = Math.max(thePoints[i].y(), maxY);
        }

        return new double[] {(minX + maxX) / 2, (minY + maxY) / 2};
    }



    /**
     * Paints the puzzle piece for Tetris.
     *
     * @param theGraphics The graphics context to use for painting.
     */
    @Override
    public void paintComponent(final Graphics theGraphics) {
        super.paintComponent(theGraphics);
        final Graphics2D g2d = (Graphics2D) theGraphics;
        GraphicsHandler.enableAntiAliasing(g2d);

        drawTheNextPiece(g2d);

    }

    @Override
    public void propertyChange(final PropertyChangeEvent theEvent) {
        switch (theEvent.getPropertyName()) {
            case PROPERTY_NEXT_PIECE -> {
                myNextPiece = (IndividualPiece) theEvent.getNewValue();
                repaint();
            }
            case ColorSchemeManager.PROPERTY_COLOR_SCHEME -> {
                setBackground(ColorSchemeManager.getCurrentSecondaryColor());
                repaint();
            }
            default -> { }
        }
    }

    private void drawTheNextPiece(final Graphics theGraphics) {
        final Graphics2D g2d = (Graphics2D) theGraphics;

        final double[] pointOffset = findOffsetPoint(myNextPiece.location());

        // individual pieces have offsets to ensure they are centered
        final double xOffset = pointOffset[0];
        final double yOffset = pointOffset[1];

        for (int i = 0; i < myNextPiece.location().length; i++) {

            // point in space is not centered to screen yet, but let's
            // just get our points separated into equal spaces for our drawing.
            final int xToPlace = (int) ((myNextPiece.location()[i].x() - xOffset)
                    * RECTANGLE_WIDTH);
            final int yToPlace = (int) ((myNextPiece.location()[i].y() - yOffset)
                    * RECTANGLE_HEIGHT);

            final Point pointToPlace = new Point(xToPlace, -yToPlace);

            // finally, ensures it's centered
            final Point pointToTakeCenter = takePointToCenter(pointToPlace);

            createCenteredRectangle(g2d,pointToTakeCenter.x(), pointToTakeCenter.y(),ColorSchemeManager.getBlockColor(myNextPiece.block()));

            //g2d.setPaint(Color.BLACK);
            //g2d.setStroke(new BasicStroke(STROKE_WIDTH));
            //g2d.draw(rectangle);
            //g2d.setPaint(ColorSchemeManager.getBlockColor(myNextPiece.block()));
            //g2d.fill(rectangle);

        }
    }
}

