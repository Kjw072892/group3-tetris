package edu.uw.tcss.view.util;

import static edu.uw.tcss.model.GameControls.GameState;
import static edu.uw.tcss.view.app.assets.AssetsManager.MUSIC_PATH;
import static edu.uw.tcss.view.util.AudioMusicFactory.BackgroundMusic;

import edu.uw.tcss.view.app.assets.AssetsManager;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.File;
import java.io.IOException;
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
    private static final Object PROPERTY_SOURCE_BEAN = new Object();
    private static final PropertyChangeSupport PCS = new PropertyChangeSupport(PROPERTY_SOURCE_BEAN);

    private static Clip myMusicChannel;
    private static BackgroundMusic myCurrentMusic;

    private static final Logger LOGGER = Logger.getLogger(AudioMusicManager.class.getName());

    static {
        LOGGER.setLevel(Level.ALL);
        try {
            myMusicChannel = AudioSystem.getClip();
            myMusicChannel.loop(Clip.LOOP_CONTINUOUSLY);
            myCurrentMusic = AudioMusicFactory.getMusicEpic();
            setCurrentMusic(myCurrentMusic);

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

            final File soundFile = AssetsManager.getFile(MUSIC_PATH, myCurrentMusic.fileName());
            final AudioInputStream stream = AudioSystem.getAudioInputStream(soundFile);

            myMusicChannel.open(stream);
            PCS.firePropertyChange(PROPERTY_MUSIC, null, myCurrentMusic);

        } catch (final UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            LOGGER.info(() -> "Could not find or open music asset: " + myCurrentMusic.fileName());
        }
    }

    /**
     * Starts the music loop.
     */
    public static void startMusic() {
        myMusicChannel.loop(Clip.LOOP_CONTINUOUSLY);
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
    public static void removePropertyChangeListener(final PropertyChangeListener theListener) {
        PCS.removePropertyChangeListener(theListener);
    }
}
