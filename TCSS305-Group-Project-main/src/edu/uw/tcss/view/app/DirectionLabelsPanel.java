package edu.uw.tcss.view.app;

import edu.uw.tcss.view.util.ColorSchemeManager;
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
public class DirectionLabelsPanel extends JPanel implements PropertyChangeListener {

    private final JPanel myLabelPanel = new JPanel();
    /**
     * Constructor for DirectionLabels class. Invokes directionLabelPanel().
     */
    public DirectionLabelsPanel() {
        super();
        directionLabelPanel();
        updateTheme();
    }

    private void directionLabelPanel() {

        final int spacer = 11;
        final int boarderThickness = 3;
        final int padding = 10;

        // TODO: might want to make these separate labels such that it's spaced automatically by swing
        final JLabel moveLeftLabel = new JLabel("Move Left:                Left or A/a key");
        moveLeftLabel.setFont(labelFontFormat());

        final JLabel moveRightLabel = new JLabel("Move Right:             Right or D/d key");
        moveRightLabel.setFont(labelFontFormat());

        final JLabel moveDownLabel = new JLabel("Move Down:             Down or S/s key");
        moveDownLabel.setFont(labelFontFormat());

        final JLabel rotateClockWise = new JLabel("Move Ccw/ Cw:        Q/q or E/e key");
        rotateClockWise.setFont(labelFontFormat());

        final JLabel dropLabel = new JLabel("Drop:                          space");
        dropLabel.setFont(labelFontFormat());

        final JLabel pauseLabel = new JLabel("Pause:                        P/p key");
        pauseLabel.setFont(labelFontFormat());

        final JLabel muteLabel = new JLabel("Quit:                           M/m key");
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
        myLabelPanel.setBackground(ColorSchemeManager.getCurrentTertiaryColor());
        setBackground(ColorSchemeManager.getCurrentTertiaryColor());
        repaint();
    }

    @Override
    public void propertyChange(final PropertyChangeEvent theEvent) {
        if (theEvent.getPropertyName().equals(ColorSchemeManager.PROPERTY_COLOR_SCHEME)) {
            updateTheme();
        }
    }
}
