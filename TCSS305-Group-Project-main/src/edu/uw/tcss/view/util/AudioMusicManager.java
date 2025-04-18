package edu.uw.tcss.view.util;

import static edu.uw.tcss.view.app.assets.AssetsManager.MUSIC_PATH;
import static edu.uw.tcss.view.util.AudioMusicFactory.BackgroundMusic;

import edu.uw.tcss.view.app.assets.AssetsManager;
import edu.uw.tcss.view.app.assets.AssetsManagerBeta;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

/**
 * static class that manages the playback of the music.
 *
 * @author Roman Bureacov
 * @version 2025-03-12
 */
public final class AudioMusicManager {

    /** name of property when the music has changed. */
    public static final String PROPERTY_MUSIC = "The music has changed!";
    /** name of property when the music has been muted or unmuted. */
    public static final String PROPERTY_MUSIC_MUTING = "the music has been muted?";

    private static final Object PROPERTY_SOURCE_BEAN = new Object();
    private static final PropertyChangeSupport PCS =
            new PropertyChangeSupport(PROPERTY_SOURCE_BEAN);

    private static Clip myMusicChannel;
    private static BackgroundMusic myCurrentMusic;
    private static boolean myIsMute;
    private static boolean myIsForcedMute = true;

    private static final Logger LOGGER = Logger.getLogger(AudioMusicManager.class.getName());

    static {
        LOGGER.setLevel(Level.ALL);
        try {
            myMusicChannel = AudioSystem.getClip();
            myMusicChannel.loop(Clip.LOOP_CONTINUOUSLY);

            PreferencesManager.retrievePreferences();
            if (myCurrentMusic == null) {
                myCurrentMusic = AudioMusicFactory.getMusicEpic();
                setCurrentMusic(myCurrentMusic);
            }

        } catch (final LineUnavailableException e) {
            LOGGER.info(() -> Arrays.toString(e.getStackTrace()));

        }
    }

    /**
     * AudioManager Constructor.
     */
    private AudioMusicManager() {

    }

    /**
     * Sets the background music file to open for access.
     * This class will not start the new music upon setting the music.
     * @param theMusic the background music clip.
     */
    public static void setCurrentMusic(final BackgroundMusic theMusic) {
        try {
            myCurrentMusic = theMusic;

            myMusicChannel.stop();
            myMusicChannel.close();

            final InputStream soundFile = AssetsManagerBeta.getSound("music",
                    myCurrentMusic.fileName());

            if (soundFile == null) {
                throw new IOException("Sound file not found: " + myCurrentMusic.fileName());
            }

            final InputStream bufferedSoundFile = new java.io.BufferedInputStream(soundFile);

            final AudioInputStream stream = AudioSystem.getAudioInputStream(bufferedSoundFile);

            myMusicChannel.open(stream);

            PCS.firePropertyChange(PROPERTY_MUSIC, null, myCurrentMusic);

        } catch (final UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            LOGGER.severe(() -> "Could not load music file: "
                    + myCurrentMusic.fileName() + "\n" + e);
        }
    }

    /**
     * Starts the music loop.
     */
    public static void startMusic() {
        if (!myIsMute && !myIsForcedMute) {
            myMusicChannel.loop(Clip.LOOP_CONTINUOUSLY);
        }
    }

    /**
     * Stops the music loop.
     */
    public static void stopMusic() {
        myMusicChannel.stop();
    }

    /**
     * Resets the music playback to start from the beginning. Does not stop or start playback.
     */
    @SuppressWarnings("unused") // part of the design pattern (start, stop, restart)
    public static void restartMusic() {
        myMusicChannel.setFramePosition(0);
    }

    /**
     * Gets the current music set.
     * @return current music
     */
    public static BackgroundMusic getCurrentMusic() {
        return myCurrentMusic;
    }

    /**
     * Adds a property change listener for when the music has changed.
     * @param theListener the object to listen for music changes
     */
    public static void addPropertyChangeListener(final PropertyChangeListener theListener) {
        PCS.addPropertyChangeListener(theListener);
    }

    /**
     * Removes the specified property change listener from the listeners.
     *
     * @param theListener the listener to remove
     */
    @SuppressWarnings("unused") // part of the design pattern (add/remove)
    public static void removePropertyChangeListener(final PropertyChangeListener theListener) {
        PCS.removePropertyChangeListener(theListener);
    }

    /**
     * Sets if the music should be mute or not.
     */
    public static void setMute(final boolean theIsMute) {
        PCS.firePropertyChange(new PropertyChangeEvent(
                PROPERTY_SOURCE_BEAN,
                PROPERTY_MUSIC_MUTING,
                myIsMute,
                theIsMute
        ));
        myIsMute = theIsMute;

        if (myIsMute) {
            stopMusic();
        } else {
            startMusic();
        }
    }

    /**
     * Gets if the music is mute.
     */
    @SuppressWarnings("unused") // part of the design pattern (getter/setter)
    public static boolean getMute() {
        return myIsMute;
    }

    /**
     * Toggles the mute of the music.
     */
    public static void toggleMute() {
        setMute(!myIsMute);
    }

    /**
     * Forces the music to be muted.
     */
    static void setForcedMute(final boolean theState) {
        myIsForcedMute = theState;
    }
}
