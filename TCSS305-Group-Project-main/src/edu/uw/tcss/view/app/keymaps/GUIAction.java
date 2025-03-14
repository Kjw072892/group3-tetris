package edu.uw.tcss.view.app.keymaps;

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
        /** control to mute the music. */
        MUTE_MUSIC,
        /** control to mute sound effects and music. */
        MUTE_ALL,
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
            case MUTE_FX -> { }
            case MUTE_MUSIC -> { }
            case MUTE_ALL -> { }
            default -> throw new EnumConstantNotPresentException(
                    Controls.class, myBind.toString()
            );
        }
    }
}
