package edu.uw.tcss.view.util;

import edu.uw.tcss.view.util.DrawingFactory.BlockStyle;

/**
 * Static class responsible for keeping track of how the block need to be drawn.
 *
 * @author Roman Bureacov
 * @version 2025-03-15
 */
public final class DrawingManager {

    private static final DrawingObject MY_DRAWER =
            DrawingFactory.getDrawingObject(BlockStyle.GLOSSY);

    private DrawingManager() {

    }


    /**
     * method to retrieve the static drawing object.
     *
     * @return the static drawing object.
     */
    public static DrawingObject getDrawer() {
        return MY_DRAWER;
    }

}
