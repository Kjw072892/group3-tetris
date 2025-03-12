package edu.uw.tcss.util;

import static edu.uw.tcss.model.GameControls.GameState;

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





    public static void startMusic() {
        myMusicChannel.loop(Clip.LOOP_CONTINUOUSLY);
    }

    public static void stopMusic() {
        myMusicChannel.stop();
    }

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
                case GameState.PAUSED, GameState.OVER -> stopMusic();
                default -> {
                }
            }
        }
    }


    public static BackgroundMusic getMusicKalimba() {
        return new BackgroundMusic("Kalimba", "Kalimba.wav");
    }

    public static BackgroundMusic getMusicRetro() {
        return new BackgroundMusic("Retro Tetris", "retroTetris.wav");
    }

    public static BackgroundMusic getMusicEpic() {
        return new BackgroundMusic("Epic Tetris", "Korobeiniki.wav");
    }

    public static BackgroundMusic getMusicTrap() {
        return new BackgroundMusic("Trap Tetris", "TrapTetris.wav");
    }

    public static BackgroundMusic getMusicAlt() {
        return new BackgroundMusic("Alternative Tetris", "TheSamovars.wav");
    }



    public record BackgroundMusic(String name, String fileName) {

        @Override
        public String toString() {
            return name;
        }
    }
}
