package edu.uw.tcss.view.app;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
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
public class DirectionLabelsPanel extends JPanel {

    /**
     * Constructor for DirectionLabels class. Invokes directionLabelPanel().
     */
    public DirectionLabelsPanel() {
        super();
        directionLabelPanel();
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

        final JLabel muteLabel = new JLabel("Mute:               M/m key");
        muteLabel.setFont(labelFontFormat());

        setLayout(new BorderLayout());
        setBorder(BorderFactory.createLineBorder(Color.BLACK, boarderThickness));

        final JPanel labelPanel = new JPanel();
        labelPanel.setLayout(new BoxLayout(labelPanel, BoxLayout.Y_AXIS));
        labelPanel.setBorder(BorderFactory.createEmptyBorder(padding, 0, padding, 0));

        labelPanel.add(moveLeftLabel);
        labelPanel.add(Box.createVerticalStrut(spacer));
        labelPanel.add(moveRightLabel);
        labelPanel.add(Box.createVerticalStrut(spacer));
        labelPanel.add(moveDownLabel);
        labelPanel.add(Box.createVerticalStrut(spacer));
        labelPanel.add(rotateClockWise);
        labelPanel.add(Box.createVerticalStrut(spacer));
        labelPanel.add(dropLabel);
        labelPanel.add(Box.createVerticalStrut(spacer));
        labelPanel.add(pauseLabel);
        labelPanel.add(Box.createVerticalStrut(spacer));
        labelPanel.add(muteLabel);

        add(labelPanel, BorderLayout.CENTER);

        labelPanel.setBackground(Color.GREEN);
        setBackground(Color.GREEN);

        setVisible(true);
        setOpaque(true);

    }

    private Font labelFontFormat() {
        final int fontSize = 10;
        return new Font("DIALOG", Font.BOLD, fontSize);
    }

}
