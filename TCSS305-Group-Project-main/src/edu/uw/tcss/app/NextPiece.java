package edu.uw.tcss.app;

import edu.uw.tcss.model.GameControls.IndividualPiece;
import edu.uw.tcss.model.GameControls.Point;
import edu.uw.tcss.model.GameControls.Block;


import javax.swing.*;
import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;
import java.util.List;


public class NextPiece extends JPanel {

    /** The width of the panel. */
    private static final int WIDTH = 100;

    /** The height of the panel. */
    private static final int HEIGHT = 133;

    /** The stroke width in pixels. */
    private static final int STROKE_WIDTH = 3;

    /** The width for the rectangle. */
    private static final int RECTANGLE_WIDTH = 20;

    /** The height for the rectangle. */
    private static final int RECTANGLE_HEIGHT = 20;

    /**
     * Constructs a new ellipse panel.
     */
    public NextPiece() {
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

    private IndividualPiece nextPiece() {
        return new IndividualPiece(new Point[] {new Point(1, 2),new Point(0, 1),new Point(1, 1),new Point(2, 1)}, Block.T);
    }

    /**
     * helper method for drawing the rectangle center styled, rather than x to width, and y to height
     * @param x
     * @param y
     * @param width
     * @param height
     * @return
     */
    private Rectangle2D.Double createCenteredRectangle(double x, double y, double width, double height) {
        double topLeftX = x - width / 2.0;
        double topLeftY = y - height / 2.0;
        return new Rectangle2D.Double(topLeftX, topLeftY, width, height);
    }

    /**
     * helper method for taking a single point, and making sure to center on screen like the regular cartesian coordinate
     * plane
     * @param thePoint
     * @return
     */
    private Point takePointToCenter(final Point thePoint) {

        int centeredX = (this.getWidth() / 2) + thePoint.x();

        int centeredY = (this.getHeight() / 2) + thePoint.y();

        return new Point(centeredX,centeredY);
    }



    /**
     * Paints the tetromino.
     *
     * @param theGraphics The graphics context to use for painting.
     */
    @SuppressWarnings("CommentedOutCode") //suppressed for the demo.
    @Override
    public void paintComponent(final Graphics theGraphics) {
        super.paintComponent(theGraphics);
        final Graphics2D g2d = (Graphics2D) theGraphics;

        // for better graphics display
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);

        IndividualPiece nextPieceTest = nextPiece();

        // individual pieces have offsets to ensure they are centered
        double xOffset = 0;
        double yOffset = 0;

        if(nextPieceTest.block() == Block.T) {
            xOffset = -1;
            yOffset = -1.5;
        }

        for(int i = 0; i < nextPieceTest.location().length; i++) {


            // point in space is not centered to screen yet, but let's
            // just get our points seperated into equal spaces for our drawing.
            int xToPlace = (int) ((nextPieceTest.location()[i].x() + xOffset)*(RECTANGLE_WIDTH));
            int yToPlace = (int) ((nextPieceTest.location()[i].y() + yOffset)*(RECTANGLE_HEIGHT));

            Point pointToPlace = new Point(xToPlace,-yToPlace);

            // finally ensures it's centered
            Point pointToTakeCenter = takePointToCenter(pointToPlace);

            final Shape rectangle = createCenteredRectangle(pointToTakeCenter.x(), pointToTakeCenter.y(), RECTANGLE_WIDTH - (STROKE_WIDTH - 1), RECTANGLE_HEIGHT - (STROKE_WIDTH - 1));

            g2d.setPaint(Color.BLACK);
            g2d.setStroke(new BasicStroke(STROKE_WIDTH));
            g2d.draw(rectangle);
            g2d.setPaint(Color.MAGENTA);
            g2d.fill(rectangle);

        }

    }

}
