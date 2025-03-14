package edu.uw.tcss.view.app;

import static edu.uw.tcss.view.util.StyleManager.BORDER_THICKNESS;

import edu.uw.tcss.view.util.ColorSchemeManager;
import edu.uw.tcss.view.util.StyleManager;
import java.awt.BorderLayout;
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
    private final JLabel myLevelTracker = new JLabel();

    /**
     * Constructor for score panel class.
     * Stores the current score.
     * @param theGameLogic the game logic which keeps track of the scoring and game state.
     */
    public ScorePanel(final GameLogic theGameLogic) {
        super();

        myGameLogic = theGameLogic;
        setupLabels();

        setLabels();

        updateTheme();
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

    private void setupLabels() {
        final int fontSize = 12;
        final int spacer = 20;
        final int padding = 10;

        setLayout(new BorderLayout());
        setBorder(BorderFactory.createLineBorder(StyleManager.getBorderColor(), BORDER_THICKNESS));

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

        myLevelTracker.setText(getNextLevelString());
        myLevelTracker.setFont(new Font(SERIF_FONT_NAME, Font.PLAIN, fontSize));
        myLevelTracker.setHorizontalAlignment(SwingConstants.CENTER);

        final JPanel messagePanel = new JPanel();
        messagePanel.add(myLevelTracker);

        add(myLabelPanel, BorderLayout.CENTER);
        add(myLevelTracker, BorderLayout.SOUTH);

        setVisible(true);
        setOpaque(true);

    }

    private void updateTheme() {
        myLabelPanel.setBackground(ColorSchemeManager.getCurrentTertiaryColor());
        setBackground(ColorSchemeManager.getCurrentTertiaryColor());
    }

    private String getNextLevelString() {
        final String plural;
        final int linesLeft = myGameLogic.getLinesUntilNextLevel();
        if (linesLeft > 1) {
            plural = "s";
        } else {
            plural = "";
        }

        return String.format("Next level in %d line%s!", linesLeft, plural);
    }

    private void setLabels() {
        myClearedLinesLabel.setText(FORMATTER.format(myGameLogic.getLinesCleared()));
        myCurrentLevelLabel.setText(FORMATTER.format(myGameLogic.getLevel()));
        myCurrentScoreLabel.setText(FORMATTER.format(myGameLogic.getScore()));
        myLevelTracker.setText(getNextLevelString());
    }

    /**
     * @param theEvent A PropertyChangeEvent object describing the event source
     *            and the property that has changed.
     */
    @Override
    public void propertyChange(final PropertyChangeEvent theEvent) {
        setLabels();
        if (theEvent.getPropertyName().equals(ColorSchemeManager.PROPERTY_COLOR_SCHEME)) {
            updateTheme();
        }
    }
}
