package edu.uw.tcss.app;

import edu.uw.tcss.model.TetrisGame;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
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

    // the amount the west panel should take up on the screen
    private static final double WEST_PANEL_PERCENTAGE = 3d / 5d;

    private static final int WEST_PANEL_WIDTH = (int) (J_FRAME_WIDTH * WEST_PANEL_PERCENTAGE);

    // aspect ratio for the game board should be 2:1
    private static final int GAME_BOARD_HEIGHT = WEST_PANEL_WIDTH * 2;

    private static final int MINOR_PADDING = 5;
    private static final int MAJOR_PADDING = 10;

    private static final int EAST_PANEL_WIDTH = J_FRAME_WIDTH - WEST_PANEL_WIDTH;
    private static final int EAST_PANEL_COMP_HEIGHT = GAME_BOARD_HEIGHT / 3;

    private static final TetrisGame game = new TetrisGame();

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
                MAJOR_PADDING, MAJOR_PADDING, MAJOR_PADDING, MAJOR_PADDING));

        // game board lives on the west
        final JPanel westPanel = new JPanel();

        westPanel.add(new BoardPanel(), BorderLayout.WEST);

        // information, such as the next piece, controls, and score, live on the east
        final JPanel eastPanel = new JPanel();
        eastPanel.setLayout(new BoxLayout(eastPanel, BoxLayout.Y_AXIS));
        eastPanel.setPreferredSize(new Dimension(EAST_PANEL_WIDTH, GAME_BOARD_HEIGHT));

        eastPanel.setBorder(BorderFactory.createEmptyBorder(0, MAJOR_PADDING, 0, 0));

        final JPanel nextPiecePanel = new NextPiecePanel();
        nextPiecePanel.setPreferredSize(new Dimension(EAST_PANEL_WIDTH, EAST_PANEL_COMP_HEIGHT));
        nextPiecePanel.setBackground(Color.BLUE);
        eastPanel.add(nextPiecePanel);

        eastPanel.add(Box.createVerticalStrut(MINOR_PADDING));

        final JPanel controlsInfoPanel = new DirectionLabels();
        controlsInfoPanel.setPreferredSize(new Dimension(EAST_PANEL_WIDTH, EAST_PANEL_COMP_HEIGHT));

        eastPanel.add(controlsInfoPanel);
        eastPanel.add(Box.createVerticalStrut(MINOR_PADDING));

        final JPanel scoreInfoPanel = new ScorePanel(0, 0, 1);
                                                    // see note above on these constants

        scoreInfoPanel.setPreferredSize(new Dimension(EAST_PANEL_WIDTH, EAST_PANEL_COMP_HEIGHT));

        eastPanel.add(scoreInfoPanel);

        add(westPanel, BorderLayout.WEST);
        add(eastPanel, BorderLayout.EAST);

        final String leftKey = "left";
        final InputMap imap = westPanel.getInputMap();
        imap.put(KeyStroke.getKeyStroke('a'), 'l');
        final ActionMap amap = westPanel.getActionMap();
        amap.put('l', new GameControlsAction(GameControlsAction.Controls.LEFT));

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

        window.setVisible(true);
    }

    // key listeners, created using abstract action
    private static class GameControlsAction extends AbstractAction {
        enum Controls {
            LEFT,
            RIGHT,
            DOWN,
            DROP,
            ROTATE_CW,
            ROTATE_CCW,
        }

        GameControlsAction(final Controls theControlBind) {
            putValue(Action.NAME, "Control Bind");
            putValue("bind", theControlBind);
        }

        @Override
        public void actionPerformed(final ActionEvent theEvent) {
            final Controls control = (Controls) getValue("bind");
            switch (control) {
                case Controls.LEFT -> game.left();
                case Controls.RIGHT -> game.right();
                case Controls.DOWN -> game.down();
                case Controls.DROP -> game.drop();
                case Controls.ROTATE_CW -> game.rotateCW();
                case Controls.ROTATE_CCW -> game.rotateCCW();
                default -> throw new EnumConstantNotPresentException(Controls.class, control.name());
            }
            System.out.println("Action: " + control);
        }
    }

    private static class GameActions extends AbstractAction {
        enum Controls {
            END_GAME,
            NEW_GAME,
            PAUSE,
            UNPAUSE,
            TOGGLE_PAUSE,
        }

        GameActions(final int theControlBind) {
            putValue(Action.NAME, "Control Bind");
            putValue("bind", theControlBind);
        }

        @Override
        public void actionPerformed(final ActionEvent theEvent) {
            switch (getValue("bind")) {
                case Controls.END_GAME -> game.endGame();
                case Controls.NEW_GAME -> game.newGame();
                case Controls.PAUSE -> game.pause();
                case Controls.UNPAUSE -> game.unPause();
                default -> game.togglePause();
            }
        }
    }
}
