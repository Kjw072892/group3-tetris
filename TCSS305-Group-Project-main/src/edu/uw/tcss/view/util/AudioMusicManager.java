package edu.uw.tcss.view.util;

import static edu.uw.tcss.model.GameControls.GameState;
import static edu.uw.tcss.view.util.AudioMusicFactory.BackgroundMusic;

import edu.uw.tcss.model.TetrisGame;
import edu.uw.tcss.view.app.assets.AssetsManager;
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
 * static class that manages the playback of the music.
 *
 * @author Roman Bureacov
 * @version 2025-03-12
 */
public class AudioMusicManager implements PropertyChangeListener {

    private static Clip myMusicChannel;

    private static final Logger LOGGER = Logger.getLogger(AudioMusicManager.class.getName());

    static {
        LOGGER.setLevel(Level.ALL);
        try {
            myMusicChannel = AudioSystem.getClip();
            myMusicChannel.loop(Clip.LOOP_CONTINUOUSLY);
            setMusic(AudioMusicFactory.getMusicEpic());

        } catch (final LineUnavailableException e) {
            LOGGER.info(() -> Arrays.toString(e.getStackTrace()));

        }
    }

    /**
     * AudioManager Constructor.
     */
    public AudioMusicManager() {
        super();
    }

    /**
     * Sets the background music file to open for access.
     * @param theMusic the background music clip.
     */
    public static void setMusic(final BackgroundMusic theMusic) {
        try {
            myMusicChannel.stop();
            myMusicChannel.close();

            final File soundFile =
                    AssetsManager.getFile(AssetsManager.MUSIC_PATH, theMusic.fileName());
            final AudioInputStream stream = AudioSystem.getAudioInputStream(soundFile);

            myMusicChannel.open(stream);

        } catch (final UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            LOGGER.info(() -> Arrays.toString(e.getStackTrace()));
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

    @Override
    public void propertyChange(final PropertyChangeEvent theEvent) {

        if (theEvent.getPropertyName().equals(TetrisGame.PROPERTY_GAME_STATE)) {
            switch (theEvent.getNewValue()) {
                case GameState.NEW, GameState.RUNNING, GameState.WORRY, GameState.PANIC ->
                        startMusic();
                case GameState.PAUSED, GameState.OVER -> stopMusic();
                default -> {
                }
            }
        }
    }

}
