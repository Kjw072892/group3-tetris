package edu.uw.tcss.view.util;

import static edu.uw.tcss.model.GameControls.GameState;
import static edu.uw.tcss.model.PropertyChangeEnabledGameControls.PROPERTY_FROZEN_BLOCKS;
import static edu.uw.tcss.model.PropertyChangeEnabledGameControls.PROPERTY_GAME_STATE;
import static edu.uw.tcss.model.PropertyChangeEnabledGameControls.PROPERTY_ROWS_CLEARED;
import static edu.uw.tcss.view.util.AudioFXManager.Channels;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

/**
 * Class that acts as the listener for the AudioFXManager.
 *
 * @author Roman Bureacov
 * @version 2025-03-14
 */
public class AudioFXListener implements PropertyChangeListener {

    /**
     * creates a new AudioFX listener object to listen for game events
     * and play  the relevant sounds.
     */
    public AudioFXListener() {
        super();
    }

    @Override
    public void propertyChange(final PropertyChangeEvent theEvent) {
        switch (theEvent.getPropertyName()) {
            case PROPERTY_GAME_STATE -> handleGameState((GameState) theEvent.getNewValue());
            case PROPERTY_FROZEN_BLOCKS ->
                AudioFXManager.playSoundFX(AudioFXManager.Channels.LANDING_FX);
            default -> { }
        }
    }

    private void handleGameState(final GameState theGameState) {
        switch (theGameState) {
            case GameState.OVER -> AudioFXManager.playSoundFX(AudioFXManager.Channels.GAME_OVER);
            case GameState.PAUSED -> AudioFXManager.playSoundFX(AudioFXManager.Channels.PAUSE_FX);
            default -> { }
        }
    }
}
