package edu.uw.tcss.view.util;

import edu.uw.tcss.view.util.DrawingFactory.BlockStyle;

/**
 * Static class responsible for keeping track of how the block need to be drawn.
 *
 * @author Roman Bureacov
 * @version 2025-03-15
 */
public final class DrawingManager {

    private static DrawingObject myDrawer = DrawingFactory.getDrawingObject(BlockStyle.GLOSSY);

    private DrawingManager() {

    }

    /**
     * Method to set the static drawing object.
     *
     * @param theDrawer the drawing object to use hereon.
     */
    @SuppressWarnings("unused")
    public static void setDrawer(final DrawingObject theDrawer) {
        myDrawer = theDrawer;
    }

    /**
     * method to retrieve the static drawing object.
     *
     * @return the static drawing object.
     */
    public static DrawingObject getDrawer() {
        return myDrawer;
    }

}
