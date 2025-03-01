package edu.uw.tcss.app;

import edu.uw.tcss.util.LabelTextBuilder;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.text.NumberFormat;
import java.util.Locale;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * ScorePanel class.
 * @author Kassie
 * @author James
 * @author Roman
 * @author Zainab
 * @version 2.28.25
 */
public class ScorePanel extends JPanel {

    private static int MY_SCORE;

    private static int MY_CURRENT_LEVEL;

    private static int MY_CURRENT_LINES;

    private static final String DIALOG_FONT_NAME = "DIALOG_INPUT";

    private static final String SERIF_FONT_NAME = "DIALOG";

    private final int myBoldFontSize = 25;

    private final int myFontSize = 19;

    private final Font myBoldFont = new Font(DIALOG_FONT_NAME, Font.BOLD, myBoldFontSize);

    private final Font myPlainFont = new Font(SERIF_FONT_NAME, Font.PLAIN, myFontSize);


    /**
     * Constructor for score panel class.
     * Stores the current score.
     * @param theScore The current score.
     */
    public ScorePanel(final int theScore, final int theCurrentLines,
                      final int theCurrentLevel) {
        super();
        setMyScore(theScore);

        setMyCurrentLevel(theCurrentLevel);

        setMyCurrentLines(theCurrentLines);

        scorePanel();

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

            throw new IllegalArgumentException("The lowest starting level must be 1!");
        }
    }


    private JLabel currentScore() {

        final NumberFormat formatter = NumberFormat.getInstance(Locale.US);

        final String scoreFormated = formatter.format(MY_SCORE);

        final JLabel boldScoreLabel = new JLabel("Score: ");
        boldScoreLabel.setFont(myBoldFont);

        final JLabel currentScoreLabel = new JLabel(scoreFormated);
        currentScoreLabel.setFont(myPlainFont);

        return new JLabel(LabelTextBuilder.htmlLabelCreator(boldScoreLabel,
                currentScoreLabel));
    }

    private JLabel currentLevel() {

        final NumberFormat formatter = NumberFormat.getInstance(Locale.US);

        final String levelFormated = formatter.format(MY_CURRENT_LEVEL);

        final JLabel boldLevelLabel = new JLabel("Level: ");
        boldLevelLabel.setFont(myBoldFont);

        final JLabel levelLabel = new JLabel(levelFormated);
        levelLabel.setFont(myPlainFont);

        return new JLabel(LabelTextBuilder.htmlLabelCreator(boldLevelLabel, levelLabel));
    }

    private JLabel currentLines() {

        final NumberFormat formatter = NumberFormat.getInstance(Locale.US);

        final String linesFormated = formatter.format(MY_CURRENT_LINES);

        final JLabel boldLinesLabel = new JLabel("Lines: ");
        boldLinesLabel.setFont(myBoldFont);

        final JLabel linesLabel = new JLabel(linesFormated);
        linesLabel.setFont(myPlainFont);

        return new JLabel(LabelTextBuilder.htmlLabelCreator(boldLinesLabel, linesLabel));
    }


    private void scorePanel() {
        final int fontSize = 12;
        final int spacer = 20;
        final int borderThickness = 3;
        final int padding = 10;

        final JLabel message = new JLabel("   Levels Increase Every 5 Lines");
        message.setFont(new Font(SERIF_FONT_NAME, Font.PLAIN, fontSize));
        message.setAlignmentY(CENTER_ALIGNMENT);

        setLayout(new BorderLayout());
        setBorder(BorderFactory.createLineBorder(Color.BLACK, borderThickness));

        final JPanel labelPanel = new JPanel();
        labelPanel.setLayout(new BoxLayout(labelPanel, BoxLayout.Y_AXIS));
        labelPanel.setBorder(BorderFactory.createEmptyBorder(padding, 0, padding, 0));

        final JLabel levelLabel = currentLevel();
        final JLabel linesLabel = currentLines();
        final JLabel scoreLabel = currentScore();

        labelPanel.add(levelLabel);
        labelPanel.add(Box.createVerticalStrut(spacer));
        labelPanel.add(linesLabel);
        labelPanel.add(Box.createVerticalStrut(spacer));
        labelPanel.add(scoreLabel);

        add(labelPanel, BorderLayout.CENTER);
        add(message, BorderLayout.SOUTH);

        labelPanel.setBackground(Color.GREEN);
        setBackground(Color.GREEN);

        setVisible(true);
        setOpaque(true);
    }


}
