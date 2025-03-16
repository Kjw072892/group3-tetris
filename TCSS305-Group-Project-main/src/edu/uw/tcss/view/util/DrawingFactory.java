package edu.uw.tcss.view.util;

import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.geom.Rectangle2D;

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
        BEVELED,
    }

    private DrawingFactory() {

    }

    /**
     * Method that paints the blocks in the style specified.
     *
     * @param theStyle block style of interest.
     */
    public static DrawingScheme getDrawingScheme(final BlockStyle theStyle) {
        return switch (theStyle) {
            case BlockStyle.GLOSSY -> getGlossyDrawer();
            case BlockStyle.BEVELED -> getBeveledDrawer();
        };
    }

    /**
     * gets the collection of drawing objects.
     * @return a collection of records of drawing objects.
     */
    public static DrawingScheme[] getDrawingObjects() {
        return new DrawingScheme[] {
                getGlossyDrawer(),
                getBeveledDrawer()
        };
    }

    /**
     * Draws the blocks in a glossy style.
     */
    public static DrawingScheme getGlossyDrawer() {
        return new DrawingScheme("Glossy Blocks", DrawingFactory::drawGlossy);
    }

    private static void drawGlossy(final Graphics2D theGraphics,
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
                theX, theY, lighterShade, theX + theWidth,
                theY + theHeight, theBaseColor);

        final GradientPaint bottomRight = new GradientPaint(
                theX, theY, theBaseColor, theX + theWidth,
                theY + theHeight, darkerShade);

        //draw base block
        theGraphics.setPaint(topLeft);
        theGraphics.fillRect(theX, theY, theWidth, theHeight);

        theGraphics.setPaint(bottomRight);
        theGraphics.fillRect(theX + offset, theY + offset,
                theWidth - offset, theHeight - offset);


        //glossy effect
        final GradientPaint gloss = new GradientPaint(theX, theY, Color.WHITE,
                theX + offset, theY + offset, new Color(255, 255, 255, 50));

        theGraphics.setPaint(gloss);
        theGraphics.fillRect(theX, theY, theWidth, theHeight);


        //outline
        theGraphics.setColor(Color.BLACK);
        theGraphics.drawRect(theX, theY, theWidth, theHeight);



        if (ColorSchemeFactory.getPinkModeColors()
                .equals(ColorSchemeManager.getCurrentColorScheme())) {
            DrawingFactory.drawSparkles(theGraphics,
                    theX, theY,
                    theWidth, theHeight);
        }

    }

    /**
     *
     */
    public static DrawingScheme getBeveledDrawer() {
        return new DrawingScheme("Beveled Blocks", DrawingFactory::drawBeveled);
    }

    private static void drawBeveled(final Graphics2D theGraphics,
                                    final int theX, final int theY,
                                    final int theWidth, final int theHeight,
                                    final Color theBaseColor) {

        theGraphics.setColor(theBaseColor.brighter());

        theGraphics.fillRect(theX, theY, theWidth, theHeight);

        theGraphics.setColor(theBaseColor.darker());

        final Polygon triangle = new Polygon();
        triangle.addPoint(theX + theWidth, theY);
        triangle.addPoint(theX + theWidth, theY + theHeight);
        triangle.addPoint(theX, theY + theHeight);

        theGraphics.fill(triangle);

        theGraphics.setColor(theBaseColor);

        final int offsetX = theWidth / 4;
        final int offsetY = theHeight / 4;
        final Rectangle2D innerRect = new Rectangle2D.Double(
                theX + offsetX,
                theY + offsetY,
                theWidth - offsetX * 2,
                theHeight - offsetY * 2
                );

        theGraphics.fill(innerRect);

        theGraphics.setColor(Color.BLACK);
        theGraphics.drawRect(theX, theY, theWidth, theHeight);
    }

    /**
     * Draws sparkles on the blocks.
     *
     * @param theGraphics the drawing graphics object
     * @param theX the x position to draw from
     * @param theY the y position to draw from
     * @param theWidth the width of the block
     * @param theHeight the height of the block
     */
    private static void drawSparkles(final Graphics2D theGraphics,
                                    final int theX, final int theY,
                                    final int theWidth, final int theHeight) {
        theGraphics.setColor(Color.WHITE);
        final int sparkleCount = 4;
        for (int i = 0; i < sparkleCount; i++) {
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
     * Stores the color scheme.
     * @param name the name of color scheme.
     * @param drawingObject the drawing object.
     */
    public record DrawingScheme(String name, DrawingObject drawingObject) {
        @Override
        public String toString() {
            return name;
        }
    }

}

