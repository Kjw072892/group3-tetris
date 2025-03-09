package edu.uw.tcss.util;

import edu.uw.tcss.model.GameControls;
import edu.uw.tcss.model.TetrisGame;

import java.awt.*;
import java.util.Map;

import static edu.uw.tcss.model.GameControls.Block.I;
import static edu.uw.tcss.model.GameControls.Block.J;
import static edu.uw.tcss.model.GameControls.Block.L;
import static edu.uw.tcss.model.GameControls.Block.O;
import static edu.uw.tcss.model.GameControls.Block.S;
import static edu.uw.tcss.model.GameControls.Block.T;
import static edu.uw.tcss.model.GameControls.Block.Z;
import static edu.uw.tcss.model.GameControls.Block;


/**
 * Factory class that creates a color scheme for the tetrominoes.
 * 
 * @author Roman Bureacov
 * @version 2025-03-08
 */
public final class ColorScheme {

    private ColorScheme() {

    }

    /**
     * Generic tetris color scheme.
     *
     * @return color scheme.
     */
    public static Map<Block, Color> getGenericColors() {
        return Map.of(
                I, Color.CYAN,
                J, Color.BLUE,
                L, Color.ORANGE,
                O, Color.YELLOW,
                S, Color.GREEN,
                T, Color.MAGENTA,
                Z, Color.WHITE
        );
    }

    /**
     * Edgy color scheme, comprised of reds and black.
     *
     * @return color scheme.
     */
    public static Map<Block, Color> getEdgyColors() {
        return Map.of(
                I, Color.RED,
                J, Color.BLACK,
                L, Color.RED,
                O, Color.BLACK,
                S, Color.RED,
                T, Color.BLACK,
                Z, Color.RED
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


}
