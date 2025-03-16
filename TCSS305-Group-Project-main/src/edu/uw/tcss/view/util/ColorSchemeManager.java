package edu.uw.tcss.view.util;

import static edu.uw.tcss.view.util.ColorSchemeFactory.ColorScheme;

import edu.uw.tcss.model.GameControls;
import edu.uw.tcss.view.app.AdBannerPanel;
import java.awt.Color;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.IOException;

/**
 * Class that manages the static color scheme.
 *
 * @author Roman Bureacov
 * @version 2025-03-11
 */
public final class ColorSchemeManager {


    /**
     * name of the property for when the color scheme has changed.
     */
    public static final String PROPERTY_COLOR_SCHEME = "The color scheme has changed";
    private static final Object PROPERTY_SOURCE_BEAN = new Object();

    private static final PropertyChangeSupport PCS =
            new PropertyChangeSupport(PROPERTY_SOURCE_BEAN);

    private static ColorScheme myCurrentColorScheme;

    static {
        myCurrentColorScheme = ColorSchemeFactory.getGenericColors();
    }

    private ColorSchemeManager() {

    }


    /**
     * Appends the property change listener to the static list of listeners.
     *
     * @param theListener the property change listener to append.
     */
    public static void addPropertyChangeListener(final PropertyChangeListener theListener) {
        PCS.addPropertyChangeListener(theListener);
    }

    /**
     * Removes the property change listener from the static list of listeners.
     *
     * @param theListener the property change listener to remove.
     */
    @SuppressWarnings("unused")
    public static void removePropertyChangeListener(final PropertyChangeListener theListener) {
        PCS.removePropertyChangeListener(theListener);
    }

    /**
     * Sets the static color scheme that all objects should share.
     *
     * @param theScheme the color scheme
     */
    public static void setCurrentColorScheme(final ColorScheme theScheme) {
        myCurrentColorScheme = theScheme;
        PCS.firePropertyChange(PROPERTY_COLOR_SCHEME, null, myCurrentColorScheme);
        // TODO: this is hard coding what should be just a property change listener
        final AdBannerPanel adPanel = AdBannerPanel.getInstance();
        if (adPanel != null) {
            try {
                adPanel.loadAdvertisements();
                adPanel.repaint();
            } catch (final IOException e) {
                e.printStackTrace();
            }
        }
    }


    /**
     * Gets the static color scheme.
     *
     * @return the current color scheme.
     */
    public static ColorScheme getCurrentColorScheme() {
        return myCurrentColorScheme;
    }

    /**
     * Convenience method to get the primary color of the static color scheme.
     *
     * @return primary color of the current color scheme
     */
    public static Color getCurrentPrimaryColor() {
        return myCurrentColorScheme.mainColors().get(ColorSchemeFactory.MainColors.Primary);
    }

    /**
     * Convenience method to get the secondary color of the static color scheme.
     *
     * @return secondary color of the current color scheme
     */
    public static Color getCurrentSecondaryColor() {
        return myCurrentColorScheme.mainColors().get(ColorSchemeFactory.MainColors.Secondary);
    }

    /**
     * Convenience method to get the tertiary color of the static color scheme.
     *
     * @return tertiary color of the current color scheme
     */
    public static Color getCurrentTertiaryColor() {
        return myCurrentColorScheme.mainColors().get(ColorSchemeFactory.MainColors.Tertiary);
    }

    /**
     * Convenience method to get the map of main color of the static color scheme.
     *
     * @return primary color of the current color scheme.
     * returns null if the color for the specified main color has not been specified.
     */
    @SuppressWarnings("unused")
    public static Color getCurrentMainColor(final ColorSchemeFactory.MainColors theMainColor) {
        return myCurrentColorScheme.mainColors().getOrDefault(theMainColor, null);
    }

    /**
     * Convenience method to get the color of the specified block.
     *
     * @param theBlock the block to get the color or
     * @return the color of the block specified by the current color scheme.
     * returns null if the color for the specified block has not been specified.
     */
    public static Color getBlockColor(final GameControls.Block theBlock) {
        return myCurrentColorScheme.blockColors().getOrDefault(theBlock, null);
    }
}
