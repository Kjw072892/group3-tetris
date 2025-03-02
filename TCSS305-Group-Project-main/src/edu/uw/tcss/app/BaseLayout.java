package edu.uw.tcss.app;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;


/**
 * BorderLayout base Panel.
 * @author Roman Bureacov
 * @author Zainab Stanikzy
 * @author Kassie Whitney
 * @version 2.26.25
 */
public final class BaseLayout extends JPanel {

    private static final int J_FRAME_WIDTH = 500;

    // the amount the west panel should take up on the screen
    private static final double WEST_PANEL_PERCENTAGE = 3.0 / 5;

    private static final int WEST_PANEL_WIDTH = (int) (J_FRAME_WIDTH * WEST_PANEL_PERCENTAGE);

    // aspect ratio for the game board should be 2:1
    private static final int GAME_BOARD_HEIGHT = WEST_PANEL_WIDTH * 2;

    private static final int MINOR_PADDING = 5;
    private static final int MAJOR_PADDING = 10;

    private static final int EAST_PANEL_WIDTH = J_FRAME_WIDTH - WEST_PANEL_WIDTH;
    private static final int EAST_PANEL_COMP_HEIGHT = GAME_BOARD_HEIGHT / 3;

    private static final int CURRENT_SCORE = 0;     // these could probably be set by the
    private static final int CURRENT_LINE = 0;      // game board when we pass the score panel in
    private static final int CURRENT_LEVEL = 1;     // - Roman

    /**
     * Constructor for Base Layout.
     */
    public BaseLayout() {
        super();
        layoutComponents();
    }

    private void layoutComponents() {
        setLayout(new BorderLayout());

        setBorder(BorderFactory.createEmptyBorder(MAJOR_PADDING, MAJOR_PADDING, MAJOR_PADDING,
                MAJOR_PADDING));

        // game board lives on the west
        final JPanel westPanel = new JPanel();
        westPanel.setPreferredSize(new Dimension(WEST_PANEL_WIDTH, GAME_BOARD_HEIGHT));
        westPanel.setBackground(Color.RED);

        westPanel.add(new BoardPanel(), BorderLayout.WEST);

        // information, such as the next piece, controls, and score, live on the east
        final JPanel eastPanel = new JPanel();
        eastPanel.setLayout(new BoxLayout(eastPanel, BoxLayout.Y_AXIS));
        eastPanel.setPreferredSize(new Dimension(EAST_PANEL_WIDTH, GAME_BOARD_HEIGHT));

        eastPanel.setBorder(BorderFactory.createEmptyBorder(0, MAJOR_PADDING, 0,
                0));


        final JPanel nextPiecePanel = new NextPiece();
        nextPiecePanel.setPreferredSize(new Dimension(EAST_PANEL_WIDTH, EAST_PANEL_COMP_HEIGHT));
        nextPiecePanel.setBackground(Color.BLUE);
        eastPanel.add(nextPiecePanel);

        eastPanel.add(Box.createVerticalStrut(MINOR_PADDING));

        final JPanel controlsPanel = new DirectionLabels();
        controlsPanel.setPreferredSize(new Dimension(EAST_PANEL_WIDTH, EAST_PANEL_COMP_HEIGHT));

        eastPanel.add(controlsPanel);
        eastPanel.add(Box.createVerticalStrut(MINOR_PADDING));

        final JPanel scorePanel = new ScorePanel(CURRENT_SCORE, CURRENT_LINE, CURRENT_LEVEL);
                                                    // see note above on these constants

        scorePanel.setPreferredSize(new Dimension(EAST_PANEL_WIDTH, EAST_PANEL_COMP_HEIGHT));

        eastPanel.add(scorePanel);

        add(westPanel, BorderLayout.WEST);
        add(eastPanel, BorderLayout.EAST);
    }

    /**
     * Enables antialiasing on a component.
     *
     * @param theComp the component to enable antialiasing on
     */
    private static void enableAntiAliasing(final Component theComp) {
        final Graphics2D g = (Graphics2D) (theComp.getGraphics());
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
    }

    /**
     * Recursive function that enables the antialiasing on components, containers,
     * and components within containers.
     *
     * @param theCont the container component to recursively go through
     */
    private static void setAllAntiAliasing(final Container theCont) {
        for (final Component comp : theCont.getComponents()) {
            if (comp instanceof Container) {
                setAllAntiAliasing((Container) comp);
            }
            enableAntiAliasing(comp);
        }
    }

    /**
     * Creates the JFrame.
     */
    public static void createAndShowGui() {
        final BaseLayout mainPanel = new BaseLayout();

        final JFrame window = new JFrame("Group 3 Tetris");
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setResizable(false);

        final FileMenu menuBar = new FileMenu(window);

        window.setJMenuBar(menuBar);

        window.setContentPane(mainPanel);

        window.pack();

        setAllAntiAliasing(window.getContentPane());

        window.setVisible(true);
    }
}
