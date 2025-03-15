package edu.uw.tcss.view.util;

import static edu.uw.tcss.view.util.ColorSchemeFactory.ColorScheme;

import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.prefs.BackingStoreException;
import java.util.prefs.Preferences;

/**
 * Static class respondible for managing the user preferences.
 *
 * @author Roman Bureacov
 * @version 2025-03-15
 */
public final class PreferencesManager {

    private static final Preferences NODE = Preferences.userRoot().node("/edu/uw/tcss/tetrisGame");
    private static final Logger LOGGER = Logger.getLogger("Preferences Logger");

    private static final String KEY_COLOR_SCHEME = "color scheme";

    private PreferencesManager() {

    }

    /**
     * Sets the current static preferences of this isntance.
     */
    public static void setPreferences() {
        NODE.put(KEY_COLOR_SCHEME, ColorSchemeManager.getCurrentColorScheme().name());
    }

    /**
     * Calls relevant static classes to set their parameters
     * based on the stored preferences, if any.
     */
    public static void retrievePreferences() {
        final String colorSchemeName = NODE.get(KEY_COLOR_SCHEME, null);

        setColorScheme(colorSchemeName);
    }

    /**
     * Clears all preferences stored.
     */
    public static void clearPreferences() {
        try {
            NODE.clear();
            NODE.flush();
        } catch (final BackingStoreException e) {
            LOGGER.log(Level.WARNING, () -> "Backing store unavailable");
        } catch (final IllegalStateException e) {
            LOGGER.log(Level.WARNING, () -> "Illegal state occurred:\n" + e.getMessage());
        }
    }

    private static void setColorScheme(final String theSchemeName) {
        if (theSchemeName != null) {
            for (final ColorScheme scheme : ColorSchemeFactory.getColorSchemes()) {
                if (theSchemeName.equals(scheme.name())) {
                    ColorSchemeManager.setCurrentColorScheme(scheme);
                }
            }
        }
    }
}
