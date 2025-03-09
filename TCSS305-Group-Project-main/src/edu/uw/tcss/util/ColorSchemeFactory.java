package edu.uw.tcss.util;

import java.awt.*;
import java.util.Map;

import static edu.uw.tcss.model.GameControls.Block;

// TODO: what if we return a record, where one color scheme is for the tetrominos and the other is for the backgrounds?

/**
 * Factory class that creates a color scheme for the tetrominoes.
 * 
 * @author Roman Bureacov
 * @version 2025-03-08
 */
public final class ColorSchemeFactory {

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
     * Edgy color scheme, comprised of reds and black.
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
                      MainColors.Primary, Color.RED,
                      MainColors.Secondary, Color.RED,
                      MainColors.Tertiary, Color.RED
                ));
    }

    /**
     * Sets the static color scheme that all objects should share.
     *
     * @param theScheme the color scheme
     */
    public static void setColorScheme(final ColorScheme theScheme) {
        myCurrentColorScheme = theScheme;
    }

    /**
     * Gets the static color scheme.
     *
     * @return the current color scheme.
     */
    public static ColorScheme getColorScheme() {
        return myCurrentColorScheme;
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
