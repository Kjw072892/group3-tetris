package edu.uw.tcss.view.util;

import static edu.uw.tcss.view.util.ColorSchemeFactory.ColorScheme;

import edu.uw.tcss.model.GameControls;
import java.awt.Color;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Class that manages the static color scheme.
 *
 * @author Roman Bureacov
 * @version 2025-03-11
 */
public final class ColorSchemeManager {


    public static final String PROPERTY_COLOR_SCHEME = "The color scheme has changed";
    public static final Object PROPERTY_SOURCE_BEAN = new Object();

    private static final Set<PropertyChangeListener> LISTENERS = new HashSet<>();

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
        LISTENERS.add(theListener);
    }

    /**
     * Removes the property change listener from the static list of listeners.
     *
     * @param theListener the property change listener to remove.
     */
    public static void removePropertyChangeListener(final PropertyChangeListener theListener) {
        LISTENERS.remove(theListener);
    }

    private static void somePropertyChanged() {
        for (final PropertyChangeListener listener : LISTENERS) {
            listener.propertyChange(new PropertyChangeEvent(
                    PROPERTY_SOURCE_BEAN,
                    PROPERTY_COLOR_SCHEME,
                    null,
                    getCurrentColorScheme()
            ));
        }
    }

    /**
     * Sets the static color scheme that all objects should share.
     *
     * @param theScheme the color scheme
     */
    public static void setCurrentColorScheme(final ColorScheme theScheme) {
        myCurrentColorScheme = theScheme;
        somePropertyChanged();
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
    public static Color getMainColors(final ColorSchemeFactory.MainColors theMainColor) {
        return myCurrentColorScheme.mainColors().getOrDefault(theMainColor, null);
    }

    /**
     * Convenience method to get the map of blocks and colors.
     *
     * @return map of blocks and their associated colors of the current color scheme.
     */
    public static Map<GameControls.Block, Color> getBlockColors() {
        return myCurrentColorScheme.blockColors();
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
