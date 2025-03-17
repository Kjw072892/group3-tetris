package edu.uw.tcss.model;

import java.beans.PropertyChangeListener;

/**
 * Allows implementing classes to leverage the <code>PropertyChange</code>
 * framework of the Observer Design Pattern to inform interested parties
 * of updates to the Tetris game state.
 *
 * <p>Defines behaviors allowing PropertyChangeListeners to be added or removed from a
 * <code>GameControls</code> object. Objects from implementing classes should inform added
 * <code>PropertyChangeListeners</code> when methods defined in <code>GameControls</code> mutate
 * the state of the object.
 * </p>
 * <p>
 * Defines a set of Properties that may be listened too. Implementing class may further define
 * more Properties.
 * </p>
 *
 * @author Charles Bryan
 * @version Winter 2025
 *
 */
public interface PropertyChangeEnabledGameControls extends GameControls {

    /**
     * A property name for Game State changes in the game.
     * Used when the Game State changes
     * Expected type for <code>newValue()</code>: <code>GameControls.GameState</code> -
     * the new game state.
     */
    String PROPERTY_GAME_STATE = "The Game State has updated to something new!";

    /**
     * A property name for when the next movable piece changes. This should
     * occur when the current movable piece freezes into place.
     * Expected type for <code>newValue()</code>: <code>IndividualPiece</code> -
     * the new next movable piece.
     */
    String PROPERTY_NEXT_PIECE = "This is the new next piece!";

    /**
     * A property name for when the movable piece changes state. This could
     * occur when the current movable piece moves, rotates, or changes after a freeze.
     * Expected type for <code>newValue()</code>: <code>IndividualPiece</code> -
     * the current movable piece.
     */
    String PROPERTY_CURRENT_PIECE = "This is the current piece!";

    /**
     * A property name for when the game's frozen blocks change state. This occurs
     * when the current movable piece freezes into place.
     * Expected type for <code>newValue()</code>: <code>FrozenBlocks</code>
     * Note: Implementing classes my use <code>Block.EMPTY</code> or <code>null</code> for
     * Empty spaces in the collection. Please refer to that documentation for implementation
     * specific details.
     */
    String PROPERTY_FROZEN_BLOCKS = "These are the frozen blocks!";

    /**
     * A property name for when the game's frozen blocks change state and one or more
     * lines clear from the frozen blocks. This can occur when the current movable
     * piece freezes into place.
     * Expected type for <code>newValue()</code>: <code>int</code>
     */
    String PROPERTY_ROWS_CLEARED = "Some rows were cleared!";

    /**
     * Add a PropertyChangeListener to the listener list. The listener is registered for
     * all properties. The same listener object may be added more than once, and will be
     * called as many times as it is added. If listener is null, no exception is thrown and
     * no action is taken.
     *
     * @param theListener The PropertyChangeListener to be added
     */
    void addPropertyChangeListener(PropertyChangeListener theListener);


    /**
     * Add a PropertyChangeListener for a specific property. The listener will be invoked only
     * when a call on firePropertyChange names that specific property. The same listener object
     * may be added more than once. For each property, the listener will be invoked the number
     * of times it was added for that property. If propertyName or listener is null, no
     * exception is thrown and no action is taken.
     *
     * @param thePropertyName The name of the property to listen on.
     * @param theListener The PropertyChangeListener to be added
     */
    void addPropertyChangeListener(String thePropertyName, PropertyChangeListener theListener);

    /**
     * Remove a PropertyChangeListener from the listener list. This removes a
     * PropertyChangeListener that was registered for all properties. If listener was added
     * more than once to the same event source, it will be notified one less time after being
     * removed. If listener is null, or was never added, no exception is thrown and no action
     * is taken.
     *
     * @param theListener The PropertyChangeListener to be removed
     */
    void removePropertyChangeListener(PropertyChangeListener theListener);

    /**
     * Remove a PropertyChangeListener for a specific property. If listener was added more than
     * once to the same event source for the specified property, it will be notified one less
     * time after being removed. If propertyName is null, no exception is thrown and no action
     * is taken. If listener is null, or was never added for the specified property, no
     * exception is thrown and take no action.
     *
     * @param thePropertyName The name of the property that was listened on.
     * @param theListener The PropertyChangeListener to be removed
     */
    void removePropertyChangeListener(String thePropertyName,
                                      PropertyChangeListener theListener);


}
