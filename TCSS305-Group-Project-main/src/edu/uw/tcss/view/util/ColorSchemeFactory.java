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
     * Theme comprising pretty colors.
     * @return a colorScheme of pretty colors.
     */
    public static ColorScheme getPrettyColors() {
        final Color PRETTY_I_BLOCK_COLOR = new Color(255, 192, 203);
        final Color PRETTY_J_BLOCK_COLOR = new Color(238, 130, 238);
        final Color PRETTY_L_BLOCK_COLOR = new Color(135, 206, 250);
        final Color PRETTY_O_BLOCK_COLOR = new Color(240, 128, 138);
        final Color PRETTY_S_BLOCK_COLOR = new Color(173, 255, 47);
        final Color PRETTY_T_BLOCK_COLOR = new Color(255, 153, 51);
        final Color PRETTY_Z_BLOCK_COLOR = new Color(245, 255, 250);
        final Color PRETTY_PRIMARY_COLOR = new Color(216, 191, 216);
        final Color PRETTY_PANEL_COLOR = new Color(250, 235, 215);
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
                getEdgyColors(),
                getPrettyColors()
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
