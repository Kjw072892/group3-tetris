package edu.uw.tcss.app;

import edu.uw.tcss.util.LabelTextBuilder;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
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

    private static final String SERIF_FONT_NAME = "SERIF";

    private final int myBoldFontSize = 25;

    private final int myFontSize = 19;


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
        MY_SCORE = theScore;
    }

    private void setMyCurrentLines(final int theCurrentLines) {
        MY_CURRENT_LINES = theCurrentLines;
    }


    private void setMyCurrentLevel(final int theCurrentLevel) {
        if (theCurrentLevel <= 0) {
            MY_CURRENT_LEVEL = 1;
        } else {
            MY_CURRENT_LEVEL = theCurrentLevel;
        }
    }


    private JLabel currentScore() {

        final NumberFormat formatter = NumberFormat.getInstance(Locale.US);

        final String scoreFormated = formatter.format(MY_SCORE);

        final JLabel boldScoreLabel = new JLabel("Score: ");
        boldScoreLabel.setFont(new Font(DIALOG_FONT_NAME, Font.BOLD, myBoldFontSize));

        final JLabel currentScoreLabel = new JLabel(scoreFormated);
        currentScoreLabel.setFont(new Font(SERIF_FONT_NAME, Font.PLAIN, myFontSize));

         // Use HTML to maintain both fonts
        final String htmlText = LabelTextBuilder.htmlLabelCreator(boldScoreLabel,
                currentScoreLabel);

        return new JLabel(htmlText);
    }

    private JLabel currentLevel() {

        final NumberFormat formatter = NumberFormat.getInstance(Locale.US);

        final String levelFormated = formatter.format(MY_CURRENT_LEVEL);

        final JLabel boldLevelLabel = new JLabel("Level: ");
        boldLevelLabel.setFont(new Font(DIALOG_FONT_NAME, Font.BOLD, myBoldFontSize));

        final JLabel levelLabel = new JLabel(levelFormated);
        levelLabel.setFont(new Font(SERIF_FONT_NAME, Font.PLAIN, myFontSize));

        final String htmlText = LabelTextBuilder.htmlLabelCreator(boldLevelLabel, levelLabel);

        return new JLabel(htmlText);
    }

    private JLabel currentLines() {

        final NumberFormat formatter = NumberFormat.getInstance(Locale.US);

        final String linesFormated = formatter.format(MY_CURRENT_LINES);

        final JLabel boldLinesLabel = new JLabel("Lines: ");
        boldLinesLabel.setFont(new Font(DIALOG_FONT_NAME, Font.BOLD, myBoldFontSize));

        final JLabel linesLabel = new JLabel(linesFormated);
        linesLabel.setFont(new Font(SERIF_FONT_NAME, Font.PLAIN, myFontSize));

        final String htmlText = LabelTextBuilder.htmlLabelCreator(boldLinesLabel, linesLabel);

        return new JLabel(htmlText);
    }

    private void scorePanel() {
        final int fontSize = 15;
        final int spacer = 20;
        final int borderThickness = 3;
        final int padding = 10;


        final JLabel message = new JLabel("Levels increase every 5 lines");
        message.setFont(new Font(SERIF_FONT_NAME, Font.BOLD, fontSize));
        message.setAlignmentX(Component.LEFT_ALIGNMENT);


        setLayout(new BorderLayout());
        setBorder(BorderFactory.createLineBorder(Color.BLACK, borderThickness));


        final JPanel labelPanel = new JPanel();
        labelPanel.setLayout(new BoxLayout(labelPanel, BoxLayout.Y_AXIS));
        labelPanel.setBorder(BorderFactory.createEmptyBorder(padding, 0, padding, 0));


        final JLabel levelLabel = currentLevel();
        final JLabel linesLabel = currentLines();
        final JLabel scoreLabel = currentScore();

        levelLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        linesLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        scoreLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        message.setAlignmentX(Component.LEFT_ALIGNMENT);

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
