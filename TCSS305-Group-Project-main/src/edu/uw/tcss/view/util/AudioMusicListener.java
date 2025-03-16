package edu.uw.tcss.view.util;

import static edu.uw.tcss.model.PropertyChangeEnabledGameControls.PROPERTY_GAME_STATE;
import static edu.uw.tcss.model.GameControls.GameState;
import static edu.uw.tcss.view.util.AudioMusicManager.PROPERTY_MUSIC;
import static edu.uw.tcss.view.util.ColorSchemeManager.PROPERTY_COLOR_SCHEME;
import static edu.uw.tcss.view.util.ColorSchemeFactory.ColorScheme;
import static edu.uw.tcss.view.util.AudioMusicFactory.BackgroundMusic;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

/**
 * Class that acts as a property change listener for the AudioMusicManager class.
 *
 * @author Roman Bureacov
 * @version 2025-03-14
 * */
public class AudioMusicListener implements PropertyChangeListener {

    private GameState myLastGameState;
    private BackgroundMusic myLastMusic = AudioMusicManager.getCurrentMusic();
    private boolean myIsPanic;

    @Override
    public void propertyChange(final PropertyChangeEvent theEvent) {
        switch (theEvent.getPropertyName()) {
            case PROPERTY_GAME_STATE -> {
                final GameState newGameState = (GameState) theEvent.getNewValue();
                handleGameState(newGameState);
                myLastGameState = newGameState;
            }
            case PROPERTY_COLOR_SCHEME -> handleColorSchemeState((ColorScheme)
                    theEvent.getNewValue());
            case PROPERTY_MUSIC -> {
                if (!myIsPanic) {
                    myLastMusic = (BackgroundMusic) theEvent.getNewValue();
                }
            }
            default -> { }
        }
    }

    private void handleColorSchemeState(final ColorScheme theColorScheme) {
        if (ColorSchemeFactory.getPrettyColors().equals(theColorScheme)) {
            AudioMusicManager.setCurrentMusic(AudioMusicFactory.getMusicCute());
        } else if (ColorSchemeFactory.getPinkModeColors().equals(theColorScheme)) {
            AudioMusicManager.setCurrentMusic(AudioMusicFactory.getMusicPink());
        }
    }


    private void handleGameState(final GameState theGameState) {
        myIsPanic = false;
        if (!GameState.OVER.equals(theGameState)
                && !GameState.PAUSED.equals(theGameState)) {
            AudioMusicManager.setForcedMute(false);
        }
        switch (theGameState) {
            case GameState.NEW -> {
                AudioMusicManager.setCurrentMusic(myLastMusic);
                AudioMusicManager.startMusic();
            }
            case GameState.PAUSED,
                 GameState.OVER -> {
                AudioMusicManager.stopMusic();
                AudioMusicManager.setForcedMute(true);
            }
            case GameState.RUNNING,
                 GameState.WORRY -> {
                if (GameState.PANIC.equals(myLastGameState)) {
                    AudioMusicManager.setCurrentMusic(myLastMusic);
                    AudioMusicManager.startMusic();
                } else if (GameState.PAUSED.equals(myLastGameState)) {
                    myLastMusic = AudioMusicManager.getCurrentMusic();
                    AudioMusicManager.startMusic();
                }
            }
            case GameState.PANIC -> {
                myIsPanic = true;
                myLastMusic = AudioMusicManager.getCurrentMusic();
                AudioMusicManager.setCurrentMusic(AudioMusicFactory.getMusicPanic());
                AudioMusicManager.startMusic();
            }
            default -> throw
                    new EnumConstantNotPresentException(
                            GameState.class, theGameState.toString());
        }
    }
}
