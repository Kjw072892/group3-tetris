package edu.uw.tcss.view.util;

import edu.uw.tcss.view.app.assets.AssetsManager;
import edu.uw.tcss.view.app.assets.AssetsManagerBeta;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;


/**
 * @author Kassie Whitney
 * @author Roman Bureacov
 * @version 3.11.25
 */
public final class AudioFXManager {

    /** property name for when the audio FX has been muted or unmuted. */
    public static final String PROPERTY_AUDIO_MUTING = "audio muting has changed";

    private static final Logger LOGGER = Logger.getLogger(AudioFXManager.class.getName());

    private static final Map<Channels, Clip> SOUND_CLIPS = new HashMap<>();
    private static final Object SOURCE_BEAN = new Object();
    private static final PropertyChangeSupport PCS = new PropertyChangeSupport(SOURCE_BEAN);
    private static boolean myIsMute;
    private static boolean myIsForcedMute = true;

    static {
        LOGGER.setLevel(Level.ALL);
        preloadSounds();
    }
    /**
     * AudioManagerFX constructor.
     */
    private AudioFXManager() {

    }

    /**
     * FX channels.
     */
    public enum Channels {
        /**
         * FX sound for roblox death sound.
         */
        GAME_OVER,

        /**
         * FX sound for changed position FX.
         */
        CHANGED_POSITION_FX,

        /**
         * FX sound for rotateFX.
         */
        ROTATE_FX,

        /**
         * FX sound for line Cleared.
         */
        LINE_CLEARED_FX,

        /**
         * FX sound for soft Drop.
         */
        SOFT_DROP_FX,

        /**
         * FX sound for a piece landing.
         */
        LANDING_FX,

        /**
         * FX sound for when paused.
         */
        PAUSE_FX,

        /**
         * FX sound when three lines gets cleared.
         */
        THREE_LINES_FX,

        /**
         * FX sound when four lines gets cleared.
         */
        FOUR_LINES_FX,

        /**
         * FX sound when a new level is reached.
         */
        NEW_LEVEL_FX

    }

    private static void preloadSounds() {
        for (Channels channels : Channels.values()) {
            try {
                final InputStream soundFile = AssetsManagerBeta.getSound("sfx",
                        getSoundFileName(channels));

                if (soundFile == null) {
                    throw new IOException("sound file not found: " + getSoundFileName(channels));
                }

                final InputStream bufferedSoundFile = new java.io.BufferedInputStream(soundFile);

                final AudioInputStream stream = AudioSystem.getAudioInputStream(bufferedSoundFile);

                final Clip clip = AudioSystem.getClip();

                clip.open(stream);

                SOUND_CLIPS.put(channels, clip);

            } catch (final UnsupportedAudioFileException | IOException
                           | LineUnavailableException e) {
                LOGGER.info(() -> "Failed to preload sound clip for: "
                        + channels + " → " + getSoundFileName(channels));
            }
        }
    }


    private static String getSoundFileName(final Channels theChannel) {
        return switch (theChannel) {
            case GAME_OVER -> "Roblox Death Sound.wav";
            case LINE_CLEARED_FX -> "lineClearedFX.wav";
            case CHANGED_POSITION_FX -> "moveFX.wav";
            case ROTATE_FX -> "rotateFX.wav";
            default -> getSoundFileNameHelper(theChannel);
        };
    }

    private static String getSoundFileNameHelper(final Channels theChannel) {
        return switch (theChannel) {
            case SOFT_DROP_FX -> "softDropFX.wav";
            case PAUSE_FX -> "pauseFX.wav";
            case LANDING_FX -> "landingFX.wav";
            case THREE_LINES_FX -> "threeLineFX.wav";
            case FOUR_LINES_FX -> "fourLineFX.wav";
            case NEW_LEVEL_FX -> "newLevelFX.wav";
            default -> "-1";
        };
    }

    /**
     * Plays the fx sound when invoked.
     * @param theChannel the fx sound clip.
     */
    public static void playSoundFX(final Channels theChannel) {
        if (!myIsMute && !myIsForcedMute) {
            final Clip clip = SOUND_CLIPS.get(theChannel);

            if (clip != null) {
                clip.setFramePosition(0);
                clip.start();
            }
        }
    }


    /**
     * Sets if the audio FX should be mute or not.
     */
    public static void setMute(final boolean theIsMute) {

        PCS.firePropertyChange(new PropertyChangeEvent(
                SOURCE_BEAN,
                PROPERTY_AUDIO_MUTING,
                myIsMute,
                theIsMute
                ));

        myIsMute = theIsMute;

        if (myIsMute || myIsForcedMute) {
            for (Clip clip : SOUND_CLIPS.values()) {
                clip.stop();
            }
        }
    }

    /**
     * Gets if the audio FX are mute.
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
     * Appends a property change listener.
     */
    public static void addPropertyChangeListener(final PropertyChangeListener theListener) {
        PCS.addPropertyChangeListener(theListener);
    }

    /**
     * removes the specified listener from the listeners.
     */
    @SuppressWarnings("unused") // part of the design pattern (add/remove)
    public static void removePropertyChangeListener(final PropertyChangeListener theListener) {
        PCS.removePropertyChangeListener(theListener);
    }

    /**
     * Forces the audio FX to stay muted.
     */
    static void setForcedMute(final boolean theState) {
        myIsForcedMute = theState;
    }

}
