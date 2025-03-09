package edu.uw.tcss.app;

import static edu.uw.tcss.model.PropertyChangeEnabledGameControls.PROPERTY_GAME_STATE;
import static edu.uw.tcss.model.PropertyChangeEnabledGameControls.PROPERTY_NEXT_PIECE;
import static edu.uw.tcss.model.PropertyChangeEnabledGameControls.PROPERTY_ROWS_CLEARED;
import static javax.swing.KeyStroke.getKeyStroke;

import edu.uw.tcss.app.keymaps.GameAction;
import edu.uw.tcss.app.keymaps.KeyMapper;
import edu.uw.tcss.app.keymaps.TetrominoAction;
import edu.uw.tcss.model.TetrisGame;

import java.awt.*;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;


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

        // add property change listeners
        myTetrisGame.addPropertyChangeListener(PROPERTY_ROWS_CLEARED, gameLogicHandler);
        myTetrisGame.addPropertyChangeListener(PROPERTY_GAME_STATE, gameLogicHandler);
        myTetrisGame.addPropertyChangeListener(PROPERTY_ROWS_CLEARED, scoreInfoPanel);
        myTetrisGame.addPropertyChangeListener(gameBoard);
        myTetrisGame.addPropertyChangeListener(PROPERTY_NEXT_PIECE, nextPiecePanel);
        
    }

    private void setupKeys() {
        // getKeyStroke is method KeyStroke.getKeyStroke, statically imported (see imports)
        // NOTE: get keystroke is a method that treats the key code as VK_[keycode]
        // getKeyStroke("pressed LEFT") parses and the "VK_" is appended to form "VK_LEFT"
        // see the list of Java virtual keys with regard to forming keystrokes
        myKeyMapper.mapTetrominoAction(
                getKeyStroke("pressed A"), TetrominoAction.Controls.LEFT);
        myKeyMapper.mapTetrominoAction(
                getKeyStroke("pressed S"), TetrominoAction.Controls.DOWN);
        myKeyMapper.mapTetrominoAction(
                getKeyStroke("pressed D"), TetrominoAction.Controls.RIGHT);
        myKeyMapper.mapTetrominoAction(
                getKeyStroke("pressed SPACE"), TetrominoAction.Controls.DROP);

        myKeyMapper.mapTetrominoAction(
                getKeyStroke("pressed LEFT"), TetrominoAction.Controls.LEFT);
        myKeyMapper.mapTetrominoAction(
                getKeyStroke("pressed DOWN"), TetrominoAction.Controls.DOWN);
        myKeyMapper.mapTetrominoAction(
                getKeyStroke("pressed RIGHT"), TetrominoAction.Controls.RIGHT);

        myKeyMapper.mapTetrominoAction(
                getKeyStroke("pressed Q"), TetrominoAction.Controls.ROTATE_CW);
        myKeyMapper.mapTetrominoAction(
                getKeyStroke("pressed E"), TetrominoAction.Controls.ROTATE_CCW);

        myKeyMapper.mapGameAction(
                getKeyStroke("pressed P"), GameAction.Controls.TOGGLE_PAUSE);
        myKeyMapper.mapGameAction(
                getKeyStroke("pressed N"), GameAction.Controls.NEW_GAME);
        myKeyMapper.mapGameAction(
                getKeyStroke("pressed M"), GameAction.Controls.END_GAME
        );
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

        final Toolkit tk = Toolkit.getDefaultToolkit();
        window.setLocation(
                (tk.getScreenSize().width - window.getWidth()) / 2,
                (tk.getScreenSize().height - window.getHeight()) / 2
        );

        window.setVisible(true);
    }

}
