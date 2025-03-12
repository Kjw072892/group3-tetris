package edu.uw.tcss.util;

import static edu.uw.tcss.model.GameControls.GameState;

import edu.uw.tcss.app.assets.AssetsManager;
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
public class AudioManager  implements PropertyChangeListener {

    private static Clip myMusicChannel;

    private static final Logger LOGGER = Logger.getLogger(AudioManager.class.getName());

    static {
        LOGGER.setLevel(Level.ALL);
        try {
            myMusicChannel = AudioSystem.getClip();
            myMusicChannel.loop(Clip.LOOP_CONTINUOUSLY);
            setMusic(getMusicEpic());

        } catch (final LineUnavailableException e) {
            LOGGER.info(() -> Arrays.toString(e.getStackTrace()));

        }
    }


    /**
     * AudioManager Constructor.
     */
    public AudioManager() {
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
                    AssetsManager.getFile(AssetsManager.MUSIC_PATH, theMusic.fileName);
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
     * Retrieves the backgroundMusic from an array.
     * @return An array BackgroundMusic (of a record type).
     */
    public static BackgroundMusic[] getBackgroundMusic() {
        return new BackgroundMusic[] {
                getMusicKalimba(),
                getMusicEpic(),
                getMusicRetro(),
                getMusicTrap(),
                getMusicAlt()
        };
    }

    @Override
    public void propertyChange(final PropertyChangeEvent theEvent) {

        if (theEvent.getPropertyName().equals(TetrisGame.PROPERTY_GAME_STATE)) {
            switch (theEvent.getNewValue()) {
                case GameState.NEW, GameState.RUNNING -> startMusic();
                case GameState.PAUSED -> stopMusic();
                case GameState.OVER -> AudioManagerFX.playFX("end");
                default -> {
                }
            }
        }
    }


    /**
     * Gets the kalimba music.
     * @return the kalimba music file.
     */
    public static BackgroundMusic getMusicKalimba() {
        return new BackgroundMusic("Kalimba", "Kalimba.wav");
    }

    /**
     * Gets the retro tetris.
     * @return the retro music file.
     */
    public static BackgroundMusic getMusicRetro() {
        return new BackgroundMusic("Retro Tetris", "retroTetris.wav");
    }

    /**
     * Gets the epic tetris music.
     * @return the retro music file.
     */
    public static BackgroundMusic getMusicEpic() {
        return new BackgroundMusic("Epic Tetris", "Korobeiniki.wav");
    }

    /**
     * Gets the trap tetris music.
     * @return the trap tetris music file.
     */
    public static BackgroundMusic getMusicTrap() {
        return new BackgroundMusic("Trap Tetris", "TrapTetris.wav");
    }

    /**
     * Gets the alt tetris music.
     * @return the alt tetris music file.
     */
    public static BackgroundMusic getMusicAlt() {
        return new BackgroundMusic("Alternative Tetris", "TheSamovars.wav");
    }


    /**
     * Stores the background music file name and song name in record.
     * @param name the name of the background music.
     * @param fileName the name of the background music wav file.
     */
    public record BackgroundMusic(String name, String fileName) {

        @Override
        public String toString() {
            return name;
        }
    }
}
