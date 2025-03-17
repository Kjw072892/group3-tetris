package edu.uw.tcss.view.util;

import static edu.uw.tcss.view.util.DrawingFactory.DrawingScheme;

import edu.uw.tcss.view.util.DrawingFactory.BlockStyle;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

/**
 * Static class responsible for keeping track of how the block need to be drawn.
 *
 * @author Roman Bureacov
 * @version 2025-03-15
 */
public final class DrawingManager {

    /** property name for when the drawing object is changed. */
    public static final String PROPERTY_DRAWING_OBJECT = "I draw blocks differently now!";

    private static DrawingScheme myDrawer = DrawingFactory.getDrawingScheme(BlockStyle.GLOSSY);
    private static final Object SOURCE_BEAN = new Object();
    private static final PropertyChangeSupport PCS = new PropertyChangeSupport(SOURCE_BEAN);

    private DrawingManager() {

    }

    /**
     * Method to set the static drawing object.
     *
     * @param theDrawer the drawing object to use hereon.
     */
    public static void setDrawer(final DrawingScheme theDrawer) {
        myDrawer = theDrawer;
        PCS.firePropertyChange(PROPERTY_DRAWING_OBJECT, null, myDrawer);
    }

    /**
     * method to retrieve the static drawing object.
     *
     * @return the static drawing object.
     */
    public static DrawingScheme getDrawer() {
        return myDrawer;
    }

    /**
     * Appends a property change listener to the collection of listeners.
     */
    public static void addPropertyChangeListener(final PropertyChangeListener theListener) {
        PCS.addPropertyChangeListener(theListener);
    }

    /**
     * Removes the property change listener from the collection of listeners.
     */
    @SuppressWarnings("unused") // part of the design pattern (add/remove)
    public static void removePropertyChangeListener(final PropertyChangeListener theListener) {
        PCS.removePropertyChangeListener(theListener);
    }



}
