package edu.uw.tcss.view.util;

import static edu.uw.tcss.view.util.ColorSchemeFactory.ColorScheme;
import static edu.uw.tcss.view.util.AudioMusicFactory.BackgroundMusic;
import static edu.uw.tcss.view.util.DrawingFactory.DrawingScheme;
import static edu.uw.tcss.view.util.DrawingFactory.getDrawingObjects;

import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.prefs.BackingStoreException;
import java.util.prefs.Preferences;

/**
 * Static class responsible for managing the user preferences.
 *
 * @author Roman Bureacov
 * @version 2025-03-15
 */
public final class PreferencesManager {

    private static final Preferences NODE = Preferences.userRoot().node("/edu/uw/tcss/tetrisGame");
    private static final Logger LOGGER = Logger.getLogger("Preferences Logger");

    private static final String KEY_COLOR_SCHEME = "color scheme";
    private static final String KEY_MUSIC = "music";
    private static final String KEY_BLOCK_STYLE = "the block style";

    private static final String[] KEYS = {
        KEY_COLOR_SCHEME,
        KEY_MUSIC,
        KEY_BLOCK_STYLE
    };

    private PreferencesManager() {

    }

    /**
     * Sets the current static preferences of this instance.
     */
    public static void setPreferences() {
        NODE.put(KEY_COLOR_SCHEME, ColorSchemeManager.getCurrentColorScheme().name());
        NODE.put(KEY_MUSIC, AudioMusicManager.getCurrentMusic().name());
        NODE.put(KEY_BLOCK_STYLE, DrawingManager.getDrawer().name());
    }

    /**
     * Checks to see if the current settings differ from the stored preferences.
     *
     * @return false if there is no difference between the preferences stored,
     * or there are unset preferences, and true otherwise
     */
    public static boolean preferencesDiffer() {
        boolean preferencesAreNull = false;
        for (final String key : KEYS) {
            if (NODE.get(key, null) == null) {
                preferencesAreNull = true;
            }
        }

        final boolean isEqualPreferences =
                ColorSchemeManager.getCurrentColorScheme().name()
                        .equals(NODE.get(KEY_COLOR_SCHEME, null))
                && AudioMusicManager.getCurrentMusic().name()
                        .equals(NODE.get(KEY_MUSIC, null))
                && DrawingManager.getDrawer().name().equals(NODE.get(KEY_BLOCK_STYLE, null));

        return !isEqualPreferences || preferencesAreNull;
    }

    /**
     * Calls relevant static classes to set their parameters
     * based on the stored preferences, if any.
     */
    public static void retrievePreferences() {
        final String colorSchemeName = NODE.get(KEY_COLOR_SCHEME, null);
        final String musicName = NODE.get(KEY_MUSIC, null);
        final String blockStyle = NODE.get(KEY_BLOCK_STYLE, null);

        setColorScheme(colorSchemeName);
        setMusic(musicName);
        setBlockStyle(blockStyle);
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
                    return;
                }
            }
        }
    }

    private static void setMusic(final String theMusicName) {
        if (theMusicName != null) {
            for (final BackgroundMusic music : AudioMusicFactory.getBackgroundMusic()) {
                if (theMusicName.equals(music.name())) {
                    AudioMusicManager.setCurrentMusic(music);
                    return;
                }
            }
        }
    }

    private static void setBlockStyle(final String theBlockStyleName) {
        if (theBlockStyleName != null) {
            for (final DrawingScheme scheme : getDrawingObjects()) {
                if (theBlockStyleName.equals(scheme.name())) {
                    DrawingManager.setDrawer(scheme);
                    return;
                }
            }
        }
    }
}
