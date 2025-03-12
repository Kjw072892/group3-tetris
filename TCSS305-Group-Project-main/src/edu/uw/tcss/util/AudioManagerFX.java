package edu.uw.tcss.util;

import edu.uw.tcss.app.assets.AssetsManager;
import edu.uw.tcss.model.GameControls;
import edu.uw.tcss.model.TetrisGame;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
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
 * @author Kassie Whitney
 * @author Roman Bureacov
 * @version 3.11.25
 */
public class AudioManagerFX implements PropertyChangeListener {
    private static Clip myFX1Channel;

    private static final Logger LOGGER = Logger.getLogger(AudioManagerFX.class.getName());

    static {
        LOGGER.setLevel(Level.ALL);
        try {
            myFX1Channel = AudioSystem.getClip();

        } catch (final LineUnavailableException e) {
            LOGGER.info(() -> Arrays.toString(e.getStackTrace()));
        }
    }

    /**
     * AudioManagerFX constructor.
     */
    public AudioManagerFX() {
        super();
    }

    /**
     * FX channels.
     */
    public enum Channels {
        /**
         * FX sound for roblox death sound.
         */
        ROBLOX_DEATH_SOUND,
        /**
         * FX sound for changed position FX.
         */
        CHANGED_POSITION_FX,

        /**
         * FX sound for drop FX.
         */
        DROP_FX,

        /**
         * FX sound for rotateFX.
         */
        ROTATE_FX

    }

    /**
     * Plays the fx sound when invoked.
     * @param theChannel the fx sound clip.
     * @param theSoundName the name of the sound clip.
     */
    public static void playSoundFX(final Channels theChannel, final String theSoundName) {
        try {
            final File soundFile =
                    AssetsManager.getFile(AssetsManager.SFX_PATH, theSoundName);

            final AudioInputStream stream = AudioSystem.getAudioInputStream(soundFile);

            switch (theChannel) {
                case ROBLOX_DEATH_SOUND, ROTATE_FX, DROP_FX, CHANGED_POSITION_FX ->
                        startSound(myFX1Channel, stream);
                default -> { }
            }

        } catch (final UnsupportedAudioFileException | IOException e) {
            LOGGER.info(() -> Arrays.toString(e.getStackTrace()));
        }
    }



    private static void startSound(final Clip theClip, final AudioInputStream theStream) {
        try {
            if (theClip.isActive()) {
                theClip.stop();
            }

            if (theClip.isOpen()) {
                theClip.close();
            }

            theClip.open(theStream);
            theClip.start();
        } catch (final LineUnavailableException | IOException e) {
            LOGGER.info(() -> Arrays.toString(e.getStackTrace()));
        }
    }

    /**
     * Plays the soundFX when the direction of type string is called.
     * Usable strings: moved, dropped, rotated.
     * @param theMovement the tetris piece position.
     */
    public static void playFX(final String theMovement) {
        switch (theMovement) {
            case "moved" -> playSoundFX(Channels.CHANGED_POSITION_FX, "changedPositionFX.wav");
            case "dropped" -> playSoundFX(Channels.DROP_FX, "dropFX.wav");
            case "rotated" -> playSoundFX(Channels.ROTATE_FX, "rotateFX.wav");
            default -> { }
        }
    }

    @Override
    public void propertyChange(final PropertyChangeEvent theEvent) {

        if (theEvent.getPropertyName().equals(TetrisGame.PROPERTY_GAME_STATE)) {
            if (theEvent.getNewValue().equals(GameControls.GameState.OVER)) {
                playSoundFX(Channels.ROBLOX_DEATH_SOUND, "robloxDeathSound.wav");
                LOGGER.info(() -> "roblox sound activated");
            }
        }
    }



}
