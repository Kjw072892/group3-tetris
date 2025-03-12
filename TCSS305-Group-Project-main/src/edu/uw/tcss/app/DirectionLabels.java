package edu.uw.tcss.app;

import edu.uw.tcss.util.ColorSchemeFactory;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * Direction Label class.
 * @author James
 * @author Kassie
 * @author Roman
 * @author Zainab
 * @version 03.1.25
 */
public class DirectionLabels extends JPanel implements PropertyChangeListener {

    private final JPanel myLabelPanel = new JPanel();
    /**
     * Constructor for DirectionLabels class. Invokes directionLabelPanel().
     */
    public DirectionLabels() {
        super();
        directionLabelPanel();
        updateTheme();
    }

    private void directionLabelPanel() {

        final int spacer = 11;
        final int boarderThickness = 3;
        final int padding = 10;

        final JLabel moveLeftLabel = new JLabel("Move Left:     left or A/a key");
        moveLeftLabel.setFont(labelFontFormat());

        final JLabel moveRightLabel = new JLabel("Move Right:   right or D/d key");
        moveRightLabel.setFont(labelFontFormat());

        final JLabel moveDownLabel = new JLabel("Move Down:  down or S/s key");
        moveDownLabel.setFont(labelFontFormat());

        final JLabel rotateClockWise = new JLabel("Move CW:      up or W/w key");
        rotateClockWise.setFont(labelFontFormat());

        final JLabel dropLabel = new JLabel("Drop:               space");
        dropLabel.setFont(labelFontFormat());

        final JLabel pauseLabel = new JLabel("Pause:             P/p key");
        pauseLabel.setFont(labelFontFormat());

        final JLabel muteLabel = new JLabel("Quite:               M/m key");
        muteLabel.setFont(labelFontFormat());

        setLayout(new BorderLayout());
        setBorder(BorderFactory.createLineBorder(Color.BLACK, boarderThickness));


        myLabelPanel.setLayout(new BoxLayout(myLabelPanel, BoxLayout.Y_AXIS));
        myLabelPanel.setBorder(BorderFactory.createEmptyBorder(padding, 0, padding, 0));

        myLabelPanel.add(moveLeftLabel);
        myLabelPanel.add(Box.createVerticalStrut(spacer));
        myLabelPanel.add(moveRightLabel);
        myLabelPanel.add(Box.createVerticalStrut(spacer));
        myLabelPanel.add(moveDownLabel);
        myLabelPanel.add(Box.createVerticalStrut(spacer));
        myLabelPanel.add(rotateClockWise);
        myLabelPanel.add(Box.createVerticalStrut(spacer));
        myLabelPanel.add(dropLabel);
        myLabelPanel.add(Box.createVerticalStrut(spacer));
        myLabelPanel.add(pauseLabel);
        myLabelPanel.add(Box.createVerticalStrut(spacer));
        myLabelPanel.add(muteLabel);

        add(myLabelPanel, BorderLayout.CENTER);


        setVisible(true);
        setOpaque(true);

    }

    private Font labelFontFormat() {
        final int fontSize = 10;
        return new Font("DIALOG", Font.BOLD, fontSize);
    }

    private void updateTheme() {
        myLabelPanel.setBackground(ColorSchemeFactory.getCurrentTertiaryColor());
        setBackground(ColorSchemeFactory.getCurrentTertiaryColor());
        repaint();
    }

    @Override
    public void propertyChange(final PropertyChangeEvent theEvent) {
        if (theEvent.getPropertyName().equals(ColorSchemeFactory.PROPERTY_COLOR_SCHEME)) {
            updateTheme();
        }
    }
}
