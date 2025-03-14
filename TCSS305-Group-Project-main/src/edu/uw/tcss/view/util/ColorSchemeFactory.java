package edu.uw.tcss.view.util;

import static edu.uw.tcss.model.GameControls.Block;

import edu.uw.tcss.model.GameControls;
import java.awt.Color;
import java.util.Map;


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
        final Color iBlock = new Color(255, 192, 203);
        final Color jBlock = new Color(238, 130, 238);
        final Color lBlock = new Color(135, 206, 250);
        final Color oBlock = new Color(240, 128, 138);
        final Color sBlock = new Color(173, 255, 47);
        final Color tBlock = new Color(255, 153, 51);
        final Color zBlock = new Color(245, 255, 250);
        final Color primary = new Color(216, 191, 216);
        final Color panel = new Color(250, 235, 215);
        return new ColorScheme(
                "Pretty ❤️",
                Map.of(
                        Block.I, iBlock,
                        Block.J, jBlock,
                        Block.L, lBlock,
                        Block.O, oBlock,
                        Block.S, sBlock,
                        Block.T, tBlock,
                        Block.Z, zBlock
                ),
                Map.of(
                        MainColors.Primary, primary,
                        MainColors.Secondary, panel,
                        MainColors.Tertiary, panel
                )
        );
    }

public static ColorScheme getPinkModeColors() {
    return new ColorScheme(
            "Pink Mode \uD83C\uDF80✨",

            Map.of(
                    Block.I, new Color(255,182, 193),
                    Block.J, new Color(238, 105, 180),
                    Block.L, new Color(255, 192, 203),
                    Block.O, new Color(255,215,0),
                    Block.S, new Color(255, 140, 105),
                    Block.T, new Color(218, 112, 214),
                    Block.Z, new Color(255, 250, 250)
            ),
            Map.of(
                    MainColors.Primary, new Color(255,192, 203),
                    MainColors.Secondary, new Color(250,128,114),
                    MainColors.Tertiary, new Color(255, 182, 193)
            )
    );

}


/**
     * Edgy color scheme, comprising reds and black.
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
                getPrettyColors(),
                getPinkModeColors()
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
