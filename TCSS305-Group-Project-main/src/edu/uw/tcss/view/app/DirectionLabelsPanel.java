package edu.uw.tcss.view.app;

import edu.uw.tcss.view.util.ColorSchemeManager;
import edu.uw.tcss.view.util.StyleManager;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * Direction Label class to display user control instructions.
 * @author Zainab
 * @author James
 * @author Kassie
 * @author Roman
 * @version 03.1.25
 */
public class DirectionLabelsPanel extends JPanel implements PropertyChangeListener {

    // Panel to hold and organize direction labels.
    private final JPanel myLabelPanel = new JPanel();

    /**
     * Constructor for DirectionLabels class. Invokes directionLabelPanel().
     */
    public DirectionLabelsPanel() {
        super();
        directionLabelPanel();
        updateTheme();
    }

    /**
     * Initializing and setting up the panel containing all direction labels.
     */
    private void directionLabelPanel() {

        // Using existing constants from style manager.
        final int boarderThickness = StyleManager.BORDER_THICKNESS;
        final int padding = StyleManager.MAJOR_PADDING;

        // Switched to GridLayout to automatically align the labels clearly in two columns.
        myLabelPanel.setLayout(new GridLayout(0, 2, padding, padding));


        // Clearly seperated labels for better readability and automatic spacing.
        addLabelPair("Move Left:", "Left or A/a key");
        addLabelPair("Move Right:", "Right or D/d key");
        addLabelPair("Move Down:", "Down or S/s key");
        addLabelPair("Move Ccw/ Cw:", "Q/q or E/e key");
        addLabelPair("Drop:", "space");
        addLabelPair("Pause:", "P/p key");
        addLabelPair("Quit:", "K/k key");

        setLayout(new BorderLayout()); // Changed to GridLayout now.
        setBorder(BorderFactory.createLineBorder(Color.BLACK, boarderThickness));

        // Adjusting padding around the panel to improve the spacing consistency.
        myLabelPanel.setBorder(BorderFactory.createEmptyBorder(padding, padding, padding, padding));
        add(myLabelPanel, BorderLayout.CENTER);

        setVisible(true);
        setOpaque(true);
    }


    /* Helper method to create and add pairs of labels to the panel neatly.
     *
     * @param action the description of the action.
     * @param keys the keys connected to the action.
     */
    private void addLabelPair(final String theActionText, final String theKeyLabelText) {
        final JLabel actionLabel = new JLabel(theActionText);
        actionLabel.setFont(labelFontFormat());

        final JLabel keyLabel = new JLabel(theKeyLabelText);
        keyLabel.setFont(labelFontFormat());

        myLabelPanel.add(actionLabel);
        myLabelPanel.add(keyLabel);

    }

    /**
     * Defines and returns a standard font for all labels.
     *
     * @return formatted font object.
     */
    private Font labelFontFormat() {
        final int fontSize = 10;
        return new Font("DIALOG", Font.BOLD, fontSize);
    }

    /*
     * Updates the color of the panel according to current color scheme.
     */
    private void updateTheme() {
        myLabelPanel.setBackground(ColorSchemeManager.getCurrentTertiaryColor());
        setBackground(ColorSchemeManager.getCurrentTertiaryColor());
        repaint();
    }

    /**
     * Listens for color scheme property changes, updates to refresh UI theme.
     * @param theEvent the property change event to respond to.
     */

    @Override
    public void propertyChange(final PropertyChangeEvent theEvent) {
        if (theEvent.getPropertyName().equals(ColorSchemeManager.PROPERTY_COLOR_SCHEME)) {
            updateTheme();
        }
    }
}
