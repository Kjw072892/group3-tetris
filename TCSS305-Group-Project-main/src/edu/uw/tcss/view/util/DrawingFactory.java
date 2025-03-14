package edu.uw.tcss.view.util;

import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.Graphics2D;

/**
 * Factory class that handles the creation of drawing objects, objects that specify
 * how a block should be drawn.
 * @author Zainab Stanikzy
 * @author Roman Bureacov
 * @version 2025-03-12
 */
public final class DrawingFactory {

    /** enum defining the style of the blocks. */
    public enum BlockStyle {
        /** glossy look. */
        GLOSSY,
        /** beveled edges. */
        BEVELED
    }

    private DrawingFactory() {

    }

    /**
     * Method that paints the blocks in the style specified.
     *
     * @param theStyle block style of interest.
     */
    public static DrawingObject getDrawingObject(final BlockStyle theStyle) {
        return switch (theStyle) {
            case BlockStyle.GLOSSY -> DrawingFactory::drawGlossy;
            case BlockStyle.BEVELED -> null;
        };
    }



    /**
     * Draws the blocks in a glossy style.
     *
     * @param theGraphics the drawing graphics object
     * @param theX the x position to draw from
     * @param theY the y position to draw from
     * @param theWidth the width of the block
     * @param theHeight the height of the block
     * @param theBaseColor the base color to draw with
     */
    public static void drawGlossy(final Graphics2D theGraphics,
                                  final int theX, final int theY,
                                  final int theWidth, final int theHeight,
                                  final Color theBaseColor) {
        final int offset = theWidth / 6;

        // Defining the color shades
        final Color lighterShade = theBaseColor.brighter().brighter();
        // Color mediumShade = baseColor.brighter();
        final Color darkerShade = theBaseColor.darker();
        // Color reallyDarkerShade = baseColor.darker().darker();

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
        final GradientPaint gloss = new GradientPaint(theX, theY, Color.WHITE, theX + offset, theY + offset, new Color(255, 255, 255, 50));
        theGraphics.setPaint(gloss);
        theGraphics.fillRect(theX, theY, theWidth, theHeight);


        //outline
        theGraphics.setColor(Color.BLACK);
        theGraphics.drawRect(theX, theY, theWidth, theHeight);

    }

}
