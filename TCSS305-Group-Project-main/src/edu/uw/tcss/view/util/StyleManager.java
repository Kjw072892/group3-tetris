package edu.uw.tcss.view.util;

import java.awt.Color;

/**
 * static class dedicated to keeping the static constants for the style of the UI.
 *
 * @author Roman Bureacov
 * @version 2025-03-13
 */
public final class StyleManager {

    /** the major padding for components to use. */
    public static final int MAJOR_PADDING = 10;
    /** the minor padding for components to use. */
    public static final int MINOR_PADDING = 5;
    /** the border thickness components should draw with. */
    public static final int BORDER_THICKNESS = 3;

    private static Color myBorderColor = Color.BLACK;

    private StyleManager() {

    }

    /**
     * Gets the static border color.
     * @return color of the border
     */
    public static Color getBorderColor() {
        return myBorderColor;
    }

    /**
     * Sets the static border color.
     * @param theColor the color the border should be
     */
    @SuppressWarnings("unused")
    public static void setBorderColor(final Color theColor) {
        myBorderColor = theColor;
    }
}
