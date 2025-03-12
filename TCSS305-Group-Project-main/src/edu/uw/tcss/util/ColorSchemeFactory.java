package edu.uw.tcss.util;
import static edu.uw.tcss.model.GameControls.Block;

import java.awt.Color;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;


/**
 * Factory class that creates a color scheme for the tetrominoes.
 * 
 * @author Roman Bureacov
 * @version 2025-03-08
 */
public final class ColorSchemeFactory {



    /**
     * property scheme constant.
     */
    public static final String PROPERTY_COLOR_SCHEME = "The color scheme has changed";

    /**
     * property source constant.
     */
    public static final Object PROPERTY_SOURCE_BEAN = new Object();


    private static final Set<PropertyChangeListener> LISTENERS = new HashSet<>();

    private static final Color PRETTY_I_BLOCK_COLOR = new Color(255, 192, 203);
    private static final Color PRETTY_J_BLOCK_COLOR = new Color(238, 130, 238);
    private static final Color PRETTY_L_BLOCK_COLOR = new Color(135, 206, 250);
    private static final Color PRETTY_O_BLOCK_COLOR = new Color(240, 128, 138);
    private static final Color PRETTY_S_BLOCK_COLOR = new Color(173, 255, 47);
    private static final Color PRETTY_T_BLOCK_COLOR = new Color(255, 153, 51);
    private static final Color PRETTY_Z_BLOCK_COLOR = new Color(245, 255, 250);
    private static final Color PRETTY_PRIMARY_COLOR = new Color(216, 191, 216);
    private static final Color PRETTY_PANEL_COLOR = new Color(250, 235, 215);


    /**
     * The keys for what are the main colors.
     */
    public enum MainColors {
        /** Primary colors. */
        Primary,
        /** Secondary colors. */
        Secondary,
        /** Tertiary colors. */
        Tertiary,
    }

    private static ColorScheme myCurrentColorScheme;

    static {
        myCurrentColorScheme = getGenericColors();
    }

    private ColorSchemeFactory() {

    }

    /**
     * Generic tetris color scheme.
     *
     * @return color scheme.
     */
    public static ColorScheme getGenericColors() {
        return new ColorScheme(
                "Generic",
                Map.of(
                    Block.I, Color.CYAN,
                    Block.J, Color.BLUE,
                    Block.L, Color.ORANGE,
                    Block.O, Color.YELLOW,
                    Block.S, Color.GREEN,
                    Block.T, Color.MAGENTA,
                    Block.Z, Color.WHITE
                ),
                Map.of(
                    MainColors.Primary, Color.RED,
                    MainColors.Secondary, Color.BLUE,
                    MainColors.Tertiary, Color.GREEN
                ));
    }

    /**
     * Theme comprising pretty colors.
     * @return a colorScheme of pretty colors.
     */



    public static ColorScheme getPrettyColors() {
        return new ColorScheme(
                "Pretty <3",
                Map.of(
                        Block.I, PRETTY_I_BLOCK_COLOR,
                        Block.J, PRETTY_J_BLOCK_COLOR,
                        Block.L, PRETTY_L_BLOCK_COLOR,
                        Block.O, PRETTY_O_BLOCK_COLOR,
                        Block.S, PRETTY_S_BLOCK_COLOR,
                        Block.T, PRETTY_T_BLOCK_COLOR,
                        Block.Z, PRETTY_Z_BLOCK_COLOR
                ),
                Map.of(
                        MainColors.Primary, PRETTY_PRIMARY_COLOR,
                        MainColors.Secondary, PRETTY_PANEL_COLOR,
                        MainColors.Tertiary, PRETTY_PANEL_COLOR
                )
        );
    }

    /**
     * Edgy color scheme, comprising reds and black.
     *
     * @return color scheme.
     */
    public static ColorScheme getEdgyColors() {
        return new ColorScheme(
                "Edgy",
                Map.of(
                    Block.I, Color.RED,
                    Block.J, Color.BLACK,
                    Block.L, Color.RED,
                    Block.O, Color.BLACK,
                    Block.S, Color.RED,
                    Block.T, Color.BLACK,
                    Block.Z, Color.RED
                ),
                Map.of(
                      MainColors.Primary, Color.RED.darker(),
                      MainColors.Secondary, Color.RED.brighter(),
                      MainColors.Tertiary, Color.RED
                ));
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
        return myCurrentColorScheme.mainColors.get(MainColors.Primary);
    }

    /**
     * Convenience method to get the secondary color of the static color scheme.
     *
     * @return secondary color of the current color scheme
     */
    public static Color getCurrentSecondaryColor() {
        return myCurrentColorScheme.mainColors.get(MainColors.Secondary);
    }

    /**
     * Convenience method to get the tertiary color of the static color scheme.
     *
     * @return tertiary color of the current color scheme
     */
    public static Color getCurrentTertiaryColor() {
        return myCurrentColorScheme.mainColors.get(MainColors.Tertiary);
    }

    /**
     * Convenience method to get the map of main color of the static color scheme.
     *
     * @return primary color of the current color scheme.
     * returns null if the color for the specified main color has not been specified.
     */
    public static Color getMainColors(final MainColors theMainColor) {
        return myCurrentColorScheme.mainColors().getOrDefault(theMainColor, null);
    }

    /**
     * Convenience method to get the map of blocks and colors.
     *
     * @return map of blocks and their associated colors of the current color scheme.
     */
    public static Map<Block, Color> getBlockColors() {
        return myCurrentColorScheme.blockColors;
    }

    /**
     * Convenience method to get the color of the specified block.
     *
     * @param theBlock the block to get the color or
     * @return the color of the block specified by the current color scheme.
     * returns null if the color for the specified block has not been specified.
     */
    public static Color getBlockColor(final Block theBlock) {
        return myCurrentColorScheme.blockColors.getOrDefault(theBlock, null);
    }

    /**
     * Method to get all the possible color schemes.
     *
     * @return all color schemes recognized by the factory.
     */
    public static ColorScheme[] getColorSchemes() {
        return new ColorScheme[] {
                getGenericColors(),
                getEdgyColors(),
                getPrettyColors()
        };
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
     * @param theListener the property changes listener to remove.
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
     * Record containing the information on the color scheme.
     *
     * @param name the name of the color scheme.
     * @param blockColors the color scheme of the tetrominoes.
     * @param mainColors the color scheme of primary, secondary, and tertiary colors.
     */
    public record ColorScheme(String name,
                              Map<Block, Color> blockColors,
                              Map<MainColors, Color> mainColors) {

        @Override
        public String toString() {
            return name;
        }
    }
}
