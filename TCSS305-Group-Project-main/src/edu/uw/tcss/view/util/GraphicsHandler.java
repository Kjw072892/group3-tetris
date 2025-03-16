package edu.uw.tcss.view.util;

import edu.uw.tcss.view.util.DrawingFactory.BlockStyle;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

/**
 * Convenience class for modifying the graphics objects of JComponents.
 *
 * @author Roman Bureacov
 * @version 2025-03-11
 */
public final class GraphicsHandler {
    private GraphicsHandler() {

    }

    /**
     * Enables the antialiasing on a graphics object.
     *
     * @param theGraphics the graphics object to enable antialiasing on
     */
    public static void enableAntiAliasing(final Graphics2D theGraphics) {
        theGraphics.setRenderingHint(
                RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
    }

    /**
     * Convenience method for enabling antialiasing and returning the cast Graphics2D object.
     * @param theGraphics the graphics object to modify and return cast.
     * @return the cast graphics object with antialiasing enabled.
     */
    public static Graphics2D enableAntiAliasingAndReturn(final Graphics theGraphics) {
        final Graphics2D g2d = (Graphics2D) theGraphics;
        enableAntiAliasing(g2d);
        return g2d;
    }
}
