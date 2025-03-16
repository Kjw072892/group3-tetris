package edu.uw.tcss.view.app;

import static javax.swing.SwingUtilities.invokeLater;

import com.formdev.flatlaf.intellijthemes.*;
import java.awt.Color;
import java.awt.Font;
import java.util.Comparator;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javax.swing.UIManager;


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

        // TODO: might need to remove this logger for production
        /*
        Logger.getAnonymousLogger().log(Level.OFF, () ->
            UIManager.getDefaults().entrySet().stream()
                .sorted(Map.Entry.comparingByKey(Comparator.comparing(Object::toString)))
                .map(entry ->
                    String.format("%-50s - %s\n",
                            entry.getKey(), entry.getValue().getClass().getSimpleName()))
                    .collect(Collectors.joining())
        );
         */

        invokeLater(BaseFrame::createFrame);

    }
}
