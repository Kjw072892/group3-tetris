package edu.uw.tcss.app;

import java.awt.*;
import javax.swing.*;


/**
 * BorderLayout base Panel.
 * @author Roman Bureacov
 * @author Zainab Stanikzy
 * @author Kassie Whitney
 * @version 2.26.25
 */
public final class BaseLayout extends JPanel {

    private static final int J_FRAME_WIDTH = 500;

    // private static final int J_FRAME_HEIGHT = 700;

    // the amount the west panel should take up on the screen
    private static final double WEST_BOARD_PERCENTAGE = 3.0 / 5;

    private static final int WEST_BOARD_WIDTH = (int) (J_FRAME_WIDTH * WEST_BOARD_PERCENTAGE);

    // aspect ratio for the game board should be 2:1
    private static final int GAME_BOARD_HEIGHT = WEST_BOARD_WIDTH * 2;

    private static final int MINOR_PADDING = 5;
    private static final int MAJOR_PADDING = 10;

    private static final int EAST_PANEL_WIDTH = J_FRAME_WIDTH - WEST_BOARD_WIDTH;
    private static final int EAST_PANEL_COMP_HEIGHT = GAME_BOARD_HEIGHT / 3;

    /**
     * Constructor for Base Layout.
     */
    public BaseLayout() {
        super();
        layoutComponents();
    }

    private void layoutComponents() {
        setLayout(new BorderLayout());

        setBorder(BorderFactory.createEmptyBorder(
                MAJOR_PADDING, MAJOR_PADDING,
                MAJOR_PADDING, MAJOR_PADDING));

        // game board lives on the west
        final JPanel westBoard = new JPanel();
        westBoard.setPreferredSize(new Dimension(WEST_BOARD_WIDTH, GAME_BOARD_HEIGHT));
        westBoard.setBackground(Color.RED);

        // information, such as the next piece, controls, and score, live on the east
        final JPanel eastPanel = new JPanel();
        eastPanel.setLayout(new BoxLayout(eastPanel, BoxLayout.Y_AXIS));
        eastPanel.setPreferredSize(new Dimension(EAST_PANEL_WIDTH, GAME_BOARD_HEIGHT));

        eastPanel.setBorder(BorderFactory.createEmptyBorder(0, MAJOR_PADDING, 0, 0));



        final JPanel nextPiecePanel = new NextPiece();
        nextPiecePanel.setPreferredSize(new Dimension(EAST_PANEL_WIDTH, EAST_PANEL_COMP_HEIGHT));
        nextPiecePanel.setBackground(Color.BLUE);
        eastPanel.add(nextPiecePanel);

        eastPanel.add(Box.createVerticalStrut(MINOR_PADDING));

        final JPanel controlsPanel = new JPanel();
        controlsPanel.setPreferredSize(new Dimension(EAST_PANEL_WIDTH, EAST_PANEL_COMP_HEIGHT));
        controlsPanel.setBackground(Color.GREEN);

        eastPanel.add(controlsPanel);
        eastPanel.add(Box.createVerticalStrut(MINOR_PADDING));

        final JPanel scorePanel = new JPanel();
        scorePanel.setPreferredSize(new Dimension(EAST_PANEL_WIDTH, EAST_PANEL_COMP_HEIGHT));
        scorePanel.setBackground(Color.GREEN);

        eastPanel.add(scorePanel);

        add(westBoard, BorderLayout.WEST);
        add(eastPanel, BorderLayout.EAST);

        this.setOpaque(true);
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

        // window.setSize(J_FRAME_WIDTH, J_FRAME_HEIGHT);

        window.setContentPane(mainPanel);

        window.pack();

        setAllAntiAliasing(window.getContentPane());

        window.setVisible(true);
    }
}
