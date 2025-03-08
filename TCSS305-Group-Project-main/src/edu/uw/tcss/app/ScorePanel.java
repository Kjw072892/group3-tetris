package edu.uw.tcss.app;

import edu.uw.tcss.model.GameControls;
import edu.uw.tcss.util.LabelTextBuilder;
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

import static edu.uw.tcss.model.PropertyChangeEnabledGameControls.PROPERTY_ROWS_CLEARED;

/**
 * ScorePanel class.
 * @author Kassie
 * @author James
 * @author Roman
 * @author Zainab
 * @version 3.2.25
 */
public class ScorePanel extends JPanel implements PropertyChangeListener  {

    private static int MY_SCORE;

    private static int MY_CURRENT_LEVEL;

    private static int MY_CURRENT_LINES;

    private static final String DIALOG_FONT_NAME = "DIALOG_INPUT";

    private static final String SERIF_FONT_NAME = "DIALOG";

    private final int myBoldFontSize = 25;

    private final int myPlainFontSize = 19;

    private final Font myBoldFont = new Font(DIALOG_FONT_NAME, Font.BOLD, myBoldFontSize);

    private final Font myPlainFont = new Font(SERIF_FONT_NAME, Font.PLAIN, myPlainFontSize);

    private final NumberFormat myFormatter = NumberFormat.getInstance(Locale.US);

    private final GameLogic myGameLogic;

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

        setMyCurrentLines(theGameLogic.getLinesCleared());

        theScorePanel();

    }

    /**
     * Sets the current score.
     * @param theScore integer of the current score.
     */
    public void setMyScore(final int theScore) {
        MY_SCORE = Math.max(theScore, 0);
        if (theScore < 0) {
            throw new IllegalArgumentException("Minimum score must be 0!");
        }
    }

    private void setMyCurrentLines(final int theCurrentLines) {
        MY_CURRENT_LINES = Math.max(theCurrentLines, 0);


        if (theCurrentLines < 0) {
            throw new IllegalArgumentException("Number of lines must be "
                    + "greater than or equal to 0!");
        }

    }

    private void setMyCurrentLevel(final int theCurrentLevel) {
        MY_CURRENT_LEVEL = Math.max(theCurrentLevel, 1);

        if (theCurrentLevel < 1) {

            System.out.println(theCurrentLevel);

            throw new IllegalArgumentException("The lowest starting level must be 1!");
        }

    }


    private JLabel currentScore() {

        final String scoreFormated = myFormatter.format(MY_SCORE);

        final JLabel boldScoreLabel = new JLabel("Score: ");
        boldScoreLabel.setFont(myBoldFont);

        final JLabel currentScoreLabel = new JLabel(scoreFormated);
        currentScoreLabel.setFont(myPlainFont);

        return new JLabel(LabelTextBuilder.htmlLabelCreator(boldScoreLabel,
                currentScoreLabel));
    }


    private JLabel currentLevel() {

        final String levelFormated = myFormatter.format(MY_CURRENT_LEVEL);

        System.out.println("Levelsformated: " + levelFormated);

        final JLabel boldLevelLabel = new JLabel("Level: ");
        boldLevelLabel.setFont(myBoldFont);

        final JLabel levelLabel = new JLabel(levelFormated);
        levelLabel.setFont(myPlainFont);

        return new JLabel(LabelTextBuilder.htmlLabelCreator(boldLevelLabel, levelLabel));
    }


    private JLabel linesCleared() {

        final String linesFormatted = myFormatter.format(MY_CURRENT_LINES);

        final JLabel boldLinesLabel = new JLabel("Lines: ");
        boldLinesLabel.setFont(myBoldFont);

        final JLabel linesLabel = new JLabel(linesFormatted);
        linesLabel.setFont(myPlainFont);

        return new JLabel(LabelTextBuilder.htmlLabelCreator(boldLinesLabel, linesLabel));
    }


    private void theScorePanel() {
        final int fontSize = 12;
        final int spacer = 20;
        final int borderThickness = 3;
        final int padding = 10;

        setLayout(new BorderLayout());
        setBorder(BorderFactory.createLineBorder(Color.BLACK, borderThickness));

        final JLabel message = new JLabel("Levels Increase Every 5 Lines");
        message.setFont(new Font(SERIF_FONT_NAME, Font.PLAIN, fontSize));
        message.setHorizontalAlignment(SwingConstants.CENTER);

        final JPanel labelPanel = new JPanel();
        labelPanel.setLayout(new BoxLayout(labelPanel, BoxLayout.Y_AXIS));
        labelPanel.setBorder(BorderFactory.createEmptyBorder(padding, 0, padding, 0));

        final JPanel messagePanel = new JPanel();
        messagePanel.setLayout(new BoxLayout(messagePanel, BoxLayout.Y_AXIS));

        System.out.println("calling current levels and data methods");
        final JLabel levelLabel = currentLevel();
        final JLabel linesLabel = linesCleared();
        final JLabel scoreLabel = currentScore();

        labelPanel.add(levelLabel);
        labelPanel.add(Box.createVerticalStrut(spacer));
        labelPanel.add(linesLabel);
        labelPanel.add(Box.createVerticalStrut(spacer));
        labelPanel.add(scoreLabel);

        messagePanel.add(message);

        add(labelPanel, BorderLayout.CENTER);
        add(message, BorderLayout.SOUTH);

        labelPanel.setBackground(Color.GREEN);
        setBackground(Color.GREEN);

        setVisible(true);
        setOpaque(true);

    }


    /**
     * @param theEvent A PropertyChangeEvent object describing the event source
     *            and the property that has changed.
     */
    @Override
    public void propertyChange(final PropertyChangeEvent theEvent) {

        final String propertyName = theEvent.getPropertyName();

        final Object newValue = theEvent.getNewValue();

        final int currentLevel = myGameLogic.getLevel();

        final int currentLinesCleared = myGameLogic.getLinesCleared();

        final int currentScore = myGameLogic.getScore();

    }
}
