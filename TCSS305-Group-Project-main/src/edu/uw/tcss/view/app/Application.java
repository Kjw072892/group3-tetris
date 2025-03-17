package edu.uw.tcss.view.app;

import static javax.swing.SwingUtilities.invokeLater;

import com.formdev.flatlaf.intellijthemes.FlatSolarizedLightIJTheme;


/**
 * Group Project 3 Tetris.
 *
 * @author Kassie Whitney
 * @author James Strand
 * @author Roman Bureacov
 * @author Zainab Stanikzy
 * @version 2.26.25
 */
public final class Application {

    private Application() {

    }

    /**
     * The main method initialized the game, takes user input in a loop.
     * Updates the game state. The loop continues until the user Quits the game.
     *
     * @param theArgs (unused)
     */
    public static void main(final String[] theArgs) {

        FlatSolarizedLightIJTheme.setup();

        invokeLater(BaseFrame::createFrame);

    }
}
