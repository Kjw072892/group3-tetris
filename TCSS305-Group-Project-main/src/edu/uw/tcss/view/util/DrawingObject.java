package edu.uw.tcss.view.util;

import java.awt.Color;
import java.awt.Graphics2D;

/**
 * Class that draws a tetromino block.
 *
 * @author Roman Bureacov
 * @version 2025-03-12
 */
@FunctionalInterface
public interface DrawingObject {
    /**
     * Drawing method of this object.
     *
     * @param theGraphics the drawing graphics object
     * @param theX the x component to draw from
     * @param theY the y component to draw from
     * @param theWidth the width to draw to
     * @param theHeight the height to draw to
     */
    void drawBlock(Graphics2D theGraphics,
              int theX, int theY,
              int theWidth, int theHeight,
              Color theColor);
}
