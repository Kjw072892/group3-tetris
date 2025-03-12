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

public class AudioMusicManager implements PropertyChangeListener {

    private static Clip myMusicChannel;
    private static Clip myFX1Channel;
    private static Clip myFX2Channel;

    private static final Logger LOGGER = Logger.getLogger(AudioMusicManager.class.getName());

    static {
        LOGGER.setLevel(Level.ALL);
        try {
            myMusicChannel = AudioSystem.getClip();
            myFX1Channel = AudioSystem.getClip();
            myFX2Channel = AudioSystem.getClip();

            myMusicChannel.loop(Clip.LOOP_CONTINUOUSLY);
            setMusic(AudioMusicFactory.getMusicEpic());

        } catch (final LineUnavailableException e) {
            LOGGER.info(() -> Arrays.toString(e.getStackTrace()));
        }
    }

    // TODO: remove or move to FX manager
    @Deprecated
    public enum Channels {
        FX1,
        FX2

    }

    // TODO: might want to make another class to handle listening for property changes
    // TODO: make private based on above ^^^^
    public AudioMusicManager() {
        super();
    }

    @Deprecated
    public static void playSoundFX(final Channels theChannel, final String theSoundName) {
        try {
            final File soundFile =
                    AssetsManager.getFile(AssetsManager.SFX_PATH, theSoundName);

            final AudioInputStream stream = AudioSystem.getAudioInputStream(soundFile);

            switch (theChannel) {
                case FX1 -> startSound(myFX1Channel, stream);
                case FX2 -> startSound(myFX2Channel, stream);
                default -> { }
            }

        } catch (final UnsupportedAudioFileException | IOException e) {
            LOGGER.info(() -> Arrays.toString(e.getStackTrace()));
        }
    }

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

    // TODO: remove or move to FX manager
    @Deprecated
    public static void stopSoundFX(final Channels theChannel) {
        switch (theChannel) {
            case FX1 -> myFX1Channel.stop();
            case FX2 -> myFX2Channel.stop();
            default -> { }
        }
    }

    public static void startMusic() {
        myMusicChannel.loop(Clip.LOOP_CONTINUOUSLY);
    }

    public static void stopMusic() {
        myMusicChannel.stop();
    }

    // TODO: might want to make another class that handles all of this
    @Override
    public void propertyChange(final PropertyChangeEvent theEvent) {
        switch (theEvent.getPropertyName()) {
            case TetrisGame.PROPERTY_GAME_STATE -> {
                switch (theEvent.getNewValue()) {
                    case GameState.NEW,
                         GameState.RUNNING -> {
                        stopSoundFX(Channels.FX1);
                        startMusic();
                    }
                    case GameState.PAUSED -> stopMusic();
                    case GameState.OVER -> {
                        stopMusic();
                        playSoundFX(Channels.FX1, "Roblox Death Sound.wav");
                    }

                    default -> { }
                }
            }
        }
    }
}
