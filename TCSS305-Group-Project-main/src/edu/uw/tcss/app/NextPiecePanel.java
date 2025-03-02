package edu.uw.tcss.app;

import edu.uw.tcss.model.GameControls.IndividualPiece;
import edu.uw.tcss.model.GameControls.Point;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.geom.Rectangle2D;
import javax.swing.JPanel;

/**
 * The Panel that draws the next tetris piece upcoming.
 *
 * @author James Parker Strand
 * @version 1
 */
public class NextPiecePanel extends JPanel {

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

    /**
     * Constructs a new ellipse panel.
     */
    public NextPiecePanel() {
        super();
        layoutComponents();
    }

    /**
     * Lay out the components and makes this frame visible.
     */
    private void layoutComponents() {
        setBackground(Color.BLUE);
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
    }

    /**
     * helper method for drawing the rectangle center styled,
     * rather than x to width, and y to height
     * @param theX where the rectangle goes in the x coordinate.
     * @param theY where the rectangle goes in the y coordinate.
     * @param theWidth width rectangle has
     * @param theHeight height the rectangle has
     * @return gives you you you the rectangle object in the
     * form the rectangle is drawn in the middle.
     */
    private Rectangle2D.Double createCenteredRectangle(final double theX, final double theY,
                                                       final double theWidth,
                                                       final double theHeight) {
        final double topLeftX = theX - theWidth / 2d;
        final double topLeftY = theY - theHeight / 2d;
        return new Rectangle2D.Double(topLeftX, topLeftY, theWidth, theHeight);
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

        System.out.println("Before: " + g2d.getRenderingHints());

        // for better graphics display
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);

        System.out.println("After: " + g2d.getRenderingHints());


        final IndividualPiece nextPieceTest = Sprint1_values.nextPiece();

        final double[] pointOffset = findOffsetPoint(nextPieceTest.location());

        // individual pieces have offsets to ensure they are centered
        final double xOffset = pointOffset[0];
        final double yOffset = pointOffset[1];

        for (int i = 0; i < nextPieceTest.location().length; i++) {

            // point in space is not centered to screen yet, but let's
            // just get our points seperated into equal spaces for our drawing.
            final int xToPlace = (int) ((nextPieceTest.location()[i].x() - xOffset)
                    * RECTANGLE_WIDTH);
            final int yToPlace = (int) ((nextPieceTest.location()[i].y() - yOffset)
                    * RECTANGLE_HEIGHT);

            final Point pointToPlace = new Point(xToPlace, -yToPlace);

            // finally, ensures it's centered
            final Point pointToTakeCenter = takePointToCenter(pointToPlace);

            final Shape rectangle = createCenteredRectangle(pointToTakeCenter.x(),
                    pointToTakeCenter.y(),
                    RECTANGLE_WIDTH - (STROKE_WIDTH - 1), RECTANGLE_HEIGHT
                            - (STROKE_WIDTH - 1));

            g2d.setPaint(Color.BLACK);
            g2d.setStroke(new BasicStroke(STROKE_WIDTH));
            g2d.draw(rectangle);
            // TODO: we'll need a way to later set up how to get the piece color automatically
            g2d.setPaint(Color.MAGENTA);
            g2d.fill(rectangle);

        }

    }

}
