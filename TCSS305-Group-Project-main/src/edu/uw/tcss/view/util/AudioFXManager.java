package edu.uw.tcss.view.util;

import edu.uw.tcss.model.GameControls;
import edu.uw.tcss.model.PropertyChangeEnabledGameControls;
import edu.uw.tcss.model.TetrisGame;
import edu.uw.tcss.view.app.assets.AssetsManager;
import java.awt.event.KeyAdapter;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.io.IOException;
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
public class AudioFXManager extends KeyAdapter implements PropertyChangeListener {

    private static final Logger LOGGER = Logger.getLogger(AudioFXManager.class.getName());

    private static final Map<Channels, Clip> SOUND_CLIPS = new HashMap<>();

    private static GameControls.GameState myLastGameState = GameControls.GameState.OVER;


    static {
        LOGGER.setLevel(Level.ALL);
        preloadSounds();
    }
    /**
     * AudioManagerFX constructor.
     */
    public AudioFXManager() {
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
                final File soundFile = AssetsManager.getFile(AssetsManager.SFX_PATH,
                        getSoundFileName(channels));

                final AudioInputStream stream = AudioSystem.getAudioInputStream(soundFile);

                final Clip clip = AudioSystem.getClip();

                clip.open(stream);

                SOUND_CLIPS.put(channels, clip);

            } catch (final UnsupportedAudioFileException | IOException
                           | LineUnavailableException e) {
                LOGGER.info(() -> Arrays.toString(e.getStackTrace()));
            }
        }
    }


    private static String getSoundFileName(final Channels theChannel) {
        return switch (theChannel) {
            case ROBLOX_DEATH_SOUND -> "Roblox Death Sound.wav";
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
        final Clip clip = SOUND_CLIPS.get(theChannel);

        if (clip != null) {

            if (clip.isRunning()) {
                clip.stop();
            }
            clip.setFramePosition(0);
            clip.start();


        }
    }


    @Override
    public void propertyChange(final PropertyChangeEvent theEvent) {

        if (theEvent.getPropertyName().equals(TetrisGame.PROPERTY_GAME_STATE)
                && theEvent.getNewValue().equals(GameControls.GameState.OVER)) {
            playSoundFX(Channels.ROBLOX_DEATH_SOUND);


        } else if (theEvent.getPropertyName().equals(TetrisGame.PROPERTY_ROWS_CLEARED)) {

            playSoundFX(Channels.LINE_CLEARED_FX);

        } else if (PropertyChangeEnabledGameControls.PROPERTY_FROZEN_BLOCKS.
                equals(theEvent.getPropertyName())
                && !GameControls.GameState.NEW.equals(myLastGameState)) {

            playSoundFX(Channels.LANDING_FX);

        } else if (PropertyChangeEnabledGameControls.PROPERTY_GAME_STATE
                .equals(theEvent.getPropertyName())) {

            myLastGameState = (GameControls.GameState) theEvent.getNewValue();

        }
    }


}
