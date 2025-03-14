package edu.uw.tcss.view.app.keymaps;

import edu.uw.tcss.view.util.AudioFXManager;
import edu.uw.tcss.view.util.AudioMusicManager;
import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;

/**
 *  Class responsible for mapping a key bind to an action.
 *
 * @author Roman Bureavob
 * @version 2025-03-13
 */
public class GUIAction extends AbstractAction {

    private final Controls myBind;

    /**
     * The possible GUI controls.
     */
    public enum Controls {
        /** control to mute the sound effects. */
        MUTE_FX,
        /** control to unmute the sound effects. */
        UNMUTE_FX,
        /** control to mute the music. */
        MUTE_MUSIC,
        /** control to unmute the music. */
        UNMUTE_MUSIC,
        /** control to mute sound effects and music. */
        MUTE_ALL,
        /** control to unmute sound effects and the music. */
        UNMUTE_ALL
    }

    /**
     * Instantiates the action using the bind provided.
     *
     * @param theBind the bind to
     */
    public GUIAction(final Controls theBind) {
        myBind = theBind;
    }

    // TODO: stub
    @Override
    public void actionPerformed(final ActionEvent theEvent) {
        switch (myBind) {
            case MUTE_FX -> AudioFXManager.setMute(true);
            case UNMUTE_FX -> AudioFXManager.setMute(false);
            case MUTE_MUSIC -> AudioMusicManager.setMute(true);
            case UNMUTE_MUSIC -> AudioMusicManager.setMute(false);
            case MUTE_ALL -> {
                AudioFXManager.setMute(true);
                AudioMusicManager.setMute(true);
            }
            case UNMUTE_ALL -> {
                AudioFXManager.setMute(false);
                AudioMusicManager.setMute(false);
            }
            default -> throw new EnumConstantNotPresentException(
                    Controls.class, myBind.toString()
            );
        }
    }
}
