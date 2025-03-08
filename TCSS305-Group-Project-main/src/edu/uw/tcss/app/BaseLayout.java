package edu.uw.tcss.app;

import edu.uw.tcss.app.KeyMapper.GameAction;
import edu.uw.tcss.app.KeyMapper.KeyMapper;
import edu.uw.tcss.app.KeyMapper.TetrominoAction;
import edu.uw.tcss.model.PropertyChangeEnabledGameControls;
import edu.uw.tcss.model.TetrisGame;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.KeyStroke;


/**
 * BorderLayout base Panel.
 *
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

    private final TetrisGame myTetrisGame;
    private final KeyMapper myKeyMapper;

    /**
     * Constructor for Base Layout.
     */
    public BaseLayout(final TetrisGame theGame) {
        super();
        myTetrisGame = theGame;

        layoutComponents();

        myKeyMapper = new KeyMapper(this, myTetrisGame);

        setupKeys();

    }

    private void layoutComponents() {
        setLayout(new BorderLayout());

        setBorder(BorderFactory.createEmptyBorder(
                MAJOR_PADDING, MAJOR_PADDING, MAJOR_PADDING, MAJOR_PADDING));

        // game board lives on the west
        final JPanel westPanel = new JPanel();
        final GameBoardPanel gameBoard = new GameBoardPanel();

        westPanel.add(gameBoard, BorderLayout.WEST);

        // information, such as the next piece, controls, and score, live on the east
        final JPanel eastPanel = new JPanel();
        eastPanel.setLayout(new BoxLayout(eastPanel, BoxLayout.Y_AXIS));
        eastPanel.setPreferredSize(new Dimension(EAST_PANEL_WIDTH, GAME_BOARD_HEIGHT));

        eastPanel.setBorder(BorderFactory.createEmptyBorder(0, MAJOR_PADDING, 0, 0));

        final NextPiecePanel nextPiecePanel = new NextPiecePanel();
        nextPiecePanel.setPreferredSize(new Dimension(EAST_PANEL_WIDTH, EAST_PANEL_COMP_HEIGHT));
        nextPiecePanel.setBackground(Color.BLUE);
        eastPanel.add(nextPiecePanel);

        eastPanel.add(Box.createVerticalStrut(MINOR_PADDING));

        final JPanel controlsInfoPanel = new DirectionLabels();
        controlsInfoPanel.setPreferredSize(new Dimension(EAST_PANEL_WIDTH, EAST_PANEL_COMP_HEIGHT));

        eastPanel.add(controlsInfoPanel);
        eastPanel.add(Box.createVerticalStrut(MINOR_PADDING));

        final GameLogic gameLogicHandler = new GameLogic(myTetrisGame);
        final ScorePanel scoreInfoPanel = new ScorePanel(gameLogicHandler);

        scoreInfoPanel.setPreferredSize(new Dimension(EAST_PANEL_WIDTH, EAST_PANEL_COMP_HEIGHT));

        eastPanel.add(scoreInfoPanel);

        add(westPanel, BorderLayout.WEST);
        add(eastPanel, BorderLayout.EAST);


        myTetrisGame.addPropertyChangeListener(
                PropertyChangeEnabledGameControls.PROPERTY_ROWS_CLEARED, gameLogicHandler);

        myTetrisGame.addPropertyChangeListener(
                PropertyChangeEnabledGameControls.PROPERTY_ROWS_CLEARED, scoreInfoPanel);

        myTetrisGame.addPropertyChangeListener(gameBoard);

        myTetrisGame.addPropertyChangeListener(
                PropertyChangeEnabledGameControls.PROPERTY_NEXT_PIECE, nextPiecePanel);


    }

    private void setupKeys() {
        myKeyMapper.mapTetrominoAction(KeyStroke.getKeyStroke('a'),
                TetrominoAction.Controls.LEFT);

        myKeyMapper.mapTetrominoAction(KeyStroke.getKeyStroke('s'),
                TetrominoAction.Controls.DOWN);

        myKeyMapper.mapTetrominoAction(KeyStroke.getKeyStroke('d'),
                TetrominoAction.Controls.RIGHT);

        myKeyMapper.mapTetrominoAction(KeyStroke.getKeyStroke(' '),
                TetrominoAction.Controls.DROP);

        myKeyMapper.mapTetrominoAction(KeyStroke.getKeyStroke('q'),
                TetrominoAction.Controls.ROTATE_CW);

        myKeyMapper.mapTetrominoAction(KeyStroke.getKeyStroke('e'),
                TetrominoAction.Controls.ROTATE_CCW);

        myKeyMapper.mapGameAction(KeyStroke.getKeyStroke('p'),
                GameAction.Controls.TOGGLE_PAUSE);
    }

    /**
     * Creates the JFrame.
     */
    public static void createAndShowGui() {
        final TetrisGame tetris = new TetrisGame();

        final BaseLayout mainPanel = new BaseLayout(tetris);

        final JFrame window = new JFrame("Group 3 Tetris");
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setResizable(false);

        final FileMenu menuBar = new FileMenu(window, tetris);

        window.setJMenuBar(menuBar);

        window.setContentPane(mainPanel);

        window.pack();

        window.setVisible(true);
    }

}
