package edu.uw.tcss.util;

import edu.uw.tcss.model.GameControls;
import edu.uw.tcss.model.TetrisGame;

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
public final class ColorScheme {

    private static Map<Block, Color> myCurrentColorScheme = getGenericColors();

    private ColorScheme() {

    }

    /**
     * Generic tetris color scheme.
     *
     * @return color scheme.
     */
    public static Map<Block, Color> getGenericColors() {
        return Map.of(
                Block.I, Color.CYAN,
                Block.J, Color.BLUE,
                Block.L, Color.ORANGE,
                Block.O, Color.YELLOW,
                Block.S, Color.GREEN,
                Block.T, Color.MAGENTA,
                Block.Z, Color.WHITE
        );
    }

    /**
     * Edgy color scheme, comprised of reds and black.
     *
     * @return color scheme.
     */
    public static Map<Block, Color> getEdgyColors() {
        return Map.of(
                Block.I, Color.RED,
                Block.J, Color.BLACK,
                Block.L, Color.RED,
                Block.O, Color.BLACK,
                Block.S, Color.RED,
                Block.T, Color.BLACK,
                Block.Z, Color.RED
        );
    }


    /**
     * Custom color scheme made by the parameters specified.
     *
     * @param theIColor
     * @param theJColor
     * @param theLColor
     * @param theOColor
     * @param theSColor
     * @param theTColor
     * @param theZColor
     */
    public static void customColors() {

    }

    /**
     * Sets the static color scheme that all objects should share.
     *
     * @param theScheme the color scheme
     */
    public static void setColorScheme(final Map<Block, Color> theScheme) {
        myCurrentColorScheme = theScheme;
    }

    /**
     * Gets the static color scheme.
     *
     * @return the current color scheme.
     */
    public static Map<Block, Color> getColorScheme() {
        return myCurrentColorScheme;
    }
}
