package edu.uw.tcss.view.util;

import edu.uw.tcss.model.GameControls;
import java.beans.PropertyChangeEvent;
import java.util.HashSet;
import java.awt.*;
import java.beans.PropertyChangeListener;
import java.util.Map;
import java.util.Set;

import static edu.uw.tcss.model.GameControls.Block;

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
        final Color black = Color.DARK_GRAY.darker();
        final Color red = new Color(240, 0, 0);

        return new ColorScheme(
                "Edgy",
                Map.of(
                    Block.I, red,
                    Block.J, black,
                    Block.L, red,
                    Block.O, black,
                    Block.S, red,
                    Block.T, black,
                    Block.Z, red
                ),
                Map.of(
                      MainColors.Primary, Color.RED.darker().darker(),
                      MainColors.Secondary, Color.RED.brighter(),
                      MainColors.Tertiary, Color.RED
                ));
    }

    /**
     * Method to get all the possible color schemes.
     *
     * @return all color schemes recognized by the factory.
     */
    public static ColorScheme[] getColorSchemes() {
        return new ColorScheme[] {
                getGenericColors(),
                getEdgyColors()
        };
    }

    /**
     * Record containing the information on the color scheme.
     *
     * @param name the name of the color scheme.
     * @param blockColors the color scheme of the tetrominoes.
     * @param mainColors the color scheme of primary, secondary, and tertiary colors.
     */
    public record ColorScheme(String name,
                              Map<GameControls.Block, Color> blockColors,
                              Map<ColorSchemeFactory.MainColors, Color> mainColors) {

        @Override
        public String toString() {
            return name;
        }
    }
}
