package edu.uw.tcss.util;

import java.awt.Graphics2D;
import java.awt.RenderingHints;

/**
 * Convenience class for modifying the graphics objects of JComponents.
 *
 * @author Roman Bureacov
 * @version 2025-03-11
 */
public final class GraphicsModifier {
    private GraphicsModifier() {

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
}
