package edu.uw.tcss.app;

import edu.uw.tcss.util.ColorSchemeFactory;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.text.NumberFormat;
import java.util.Locale;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;


/**
 * ScorePanel class.
 * @author Kassie
 * @author James
 * @author Roman
 * @author Zainab
 * @version 3.2.25
 */
public class ScorePanel extends JPanel implements PropertyChangeListener {
    private static final NumberFormat FORMATTER = NumberFormat.getInstance(Locale.US);
    private static final String DIALOG_FONT_NAME = "DIALOG_INPUT";
    private static final String SERIF_FONT_NAME = "DIALOG";

    private static final int BOLD_FONT_SIZE = 22;
    private static final int PLAIN_FONT_SIZE = 20;

    private static final Font BOLD_FONT = new Font(DIALOG_FONT_NAME, Font.BOLD, BOLD_FONT_SIZE);

    private static final Font PLAIN_FONT = new Font(SERIF_FONT_NAME, Font.PLAIN, PLAIN_FONT_SIZE);

    private final JPanel myLabelPanel = new JPanel();

    private final GameLogic myGameLogic;

    private final JLabel myCurrentScoreLabel = new JLabel();
    private final JLabel myClearedLinesLabel = new JLabel();
    private final JLabel myCurrentLevelLabel = new JLabel();


    /**
     * Constructor for score panel class.
     * Stores the current score.
     * @param theGameLogic the game logic which keeps track of the scoring and game state.
     */
    public ScorePanel(final GameLogic theGameLogic) {
        super();

        myGameLogic = theGameLogic;

        setMyScore(theGameLogic.getScore());

        setMyCurrentLevel(theGameLogic.getLevel());

        setMyClearedLines(theGameLogic.getLinesCleared());

        theScorePanel();

        updateTheme();

    }

    /**
     * Sets the current score.
     * @param theScore integer of the current score.
     */
    public void setMyScore(final int theScore) {
        if (theScore < 0) {
            throw new IllegalArgumentException("Minimum score must be 0!");
        }

        myCurrentScoreLabel.setText(FORMATTER.format(theScore));

    }

    private void setMyClearedLines(final int theCurrentLines) {
        if (theCurrentLines < 0) {
            throw new IllegalArgumentException(
                    "Number of lines cleared must be greater than or equal to 0!");
        }

        myClearedLinesLabel.setText(FORMATTER.format(theCurrentLines));



    }

    private void setMyCurrentLevel(final int theCurrentLevel) {
        if (theCurrentLevel < 1) {
            throw new IllegalArgumentException("The lowest starting level must be 1!");
        }

        myCurrentLevelLabel.setText(FORMATTER.format(theCurrentLevel));

    }


    /**
     * Creates a formatted panel that a line will sit on.
     * Helper method to create a "label" of sorts.
     *
     * @param thePrefixString string that the line starts with
     * @param thePostfix the label attached that will be mutable
     * @return a JPanel that combines the two onto one line
     */
    private JPanel formattedLine(final String thePrefixString, final JLabel thePostfix) {
        final JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));
        panel.setAlignmentX(0);
        final int arbitraryLeftPadding = 5;
        panel.setBorder(BorderFactory.createEmptyBorder(0, arbitraryLeftPadding, 0, 0));
        panel.setOpaque(false);

        final JLabel prefixLabel = new JLabel(thePrefixString);
        prefixLabel.setFont(BOLD_FONT);
        thePostfix.setFont(PLAIN_FONT);

        panel.add(prefixLabel);
        panel.add(thePostfix);

        return panel;
    }

    private void theScorePanel() {
        final int fontSize = 12;
        final int spacer = 20;
        final int borderThickness = 3;
        final int padding = 10;

        setLayout(new BorderLayout());
        setBorder(BorderFactory.createLineBorder(Color.BLACK, borderThickness));

        myLabelPanel.setLayout(new BoxLayout(myLabelPanel, BoxLayout.Y_AXIS));
        myLabelPanel.setBorder(BorderFactory.createEmptyBorder(padding, 0, padding, 0));

        final JPanel scorePanel = formattedLine("Score: ", myCurrentScoreLabel);
        myLabelPanel.add(scorePanel);

        myLabelPanel.add(Box.createVerticalStrut(spacer));

        final JPanel linesClearedPanel = formattedLine("Lines: ", myClearedLinesLabel);
        myLabelPanel.add(linesClearedPanel);

        myLabelPanel.add(Box.createVerticalStrut(spacer));

        final JPanel currentLevelPanel = formattedLine("Level: ", myCurrentLevelLabel);
        myLabelPanel.add(currentLevelPanel);

        final JLabel message = new JLabel("Levels Increase Every 5 Lines!");
        message.setFont(new Font(SERIF_FONT_NAME, Font.PLAIN, fontSize));
        message.setHorizontalAlignment(SwingConstants.CENTER);

        final JPanel messagePanel = new JPanel();
        messagePanel.add(message);

        add(myLabelPanel, BorderLayout.CENTER);
        add(message, BorderLayout.SOUTH);

        setVisible(true);
        setOpaque(true);

    }

    private void updateTheme() {
        myLabelPanel.setBackground(ColorSchemeFactory.getCurrentTertiaryColor());
        setBackground(ColorSchemeFactory.getCurrentTertiaryColor());
    }


    /**
     * @param theEvent A PropertyChangeEvent object describing the event source
     *            and the property that has changed.
     */
    @Override
    public void propertyChange(final PropertyChangeEvent theEvent) {
        setMyClearedLines(myGameLogic.getLinesCleared());
        setMyCurrentLevel(myGameLogic.getLevel());
        setMyScore(myGameLogic.getScore());

        if (theEvent.getPropertyName().equals(ColorSchemeFactory.PROPERTY_COLOR_SCHEME)) {
            updateTheme();
        }


    }
}
