package edu.uw.tcss.util;

import static edu.uw.tcss.model.GameControls.GameState;

import edu.uw.tcss.app.assets.DirectoryManager;
import edu.uw.tcss.model.TetrisGame;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;

public class AudioManager implements PropertyChangeListener {

    private static Clip myMusicChannel;
    private static Clip myFX1Channel;
    private static Clip myFX2Channel;

    static {
        try {
            myMusicChannel = AudioSystem.getClip();
            myFX1Channel = AudioSystem.getClip();
            myFX2Channel = AudioSystem.getClip();

            myMusicChannel.loop(Clip.LOOP_CONTINUOUSLY);
            setMusic(getMusicEpic());

        } catch (LineUnavailableException e) {
            // TODO: use logger
            e.printStackTrace();
        }
    }

    public enum Channels {
        FX1,
        FX2

    }

    public AudioManager() {
        super();
    }

    public static void playSoundFX(final Channels theChannel, final String theSoundName) {
        try {
            final File soundFile =
                    DirectoryManager.getFile(DirectoryManager.MUSIC_PATH, theSoundName);

            final AudioInputStream stream = AudioSystem.getAudioInputStream(soundFile);

            switch (theChannel) {
                case FX1 -> startSound(myFX1Channel, stream);
                case FX2 -> startSound(myFX2Channel, stream);
                default -> { }
            }

        } catch (Exception e) {
            // TODO: catch statement
            e.printStackTrace();
        }
    }

    public static void setMusic(final BackgroundMusic theMusic) {
        try {
            myMusicChannel.stop();
            myMusicChannel.close();

            final File soundFile =
                    DirectoryManager.getFile(DirectoryManager.MUSIC_PATH, theMusic.fileName);
            final AudioInputStream stream = AudioSystem.getAudioInputStream(soundFile);

            myMusicChannel.open(stream);

        } catch (Exception e) {
            // TODO: ?????
            e.printStackTrace();
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
        } catch (Exception e) {
            // TODO: ????
            e.printStackTrace();
        }
    }

    public static void stopSoundFX(final Channels theChannel) {
        switch (theChannel) {
            case FX1 -> myFX1Channel.stop();
            case FX2 -> myFX2Channel.stop();
            default -> { }
        }
    }

    public static void startMusic() {
        myMusicChannel.start();
    }

    public static void stopMusic() {
        myMusicChannel.stop();
    }

    public static BackgroundMusic[] getBackgroundMusic() {
        return new BackgroundMusic[] {
                getMusicKalimba(),
                getMusicRetro(),
                getMusicTrap(),
                getMusicAlt()
        };
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

    private void playDeathSound() {
        stopMusic();
        playSoundFX(Channels.FX1, "Roblox Death Sound.wav");
    }

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

                    case GameState.OVER -> playDeathSound();

                    default -> { }
                }
            }
        }
    }

    public record BackgroundMusic(String name, String fileName) {

        @Override
        public String toString() {
            return name;
        }
    }
}
