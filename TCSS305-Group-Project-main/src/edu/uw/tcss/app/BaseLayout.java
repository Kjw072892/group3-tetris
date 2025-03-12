package edu.uw.tcss.app;

import static javax.swing.KeyStroke.getKeyStroke;
import static edu.uw.tcss.model.PropertyChangeEnabledGameControls.PROPERTY_FROZEN_BLOCKS;
import static edu.uw.tcss.model.PropertyChangeEnabledGameControls.PROPERTY_GAME_STATE;
import static edu.uw.tcss.model.PropertyChangeEnabledGameControls.PROPERTY_ROWS_CLEARED;

import edu.uw.tcss.app.keymaps.GameAction;
import edu.uw.tcss.app.keymaps.KeyMapper;
import edu.uw.tcss.app.keymaps.TetrominoAction;
import edu.uw.tcss.model.GameControls;
import edu.uw.tcss.model.TetrisGame;
import edu.uw.tcss.util.AudioManager;
import edu.uw.tcss.util.AudioManagerFX;
import edu.uw.tcss.util.ColorSchemeFactory;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.WindowEvent;
import java.awt.event.WindowFocusListener;
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
    private static final TetrisGame MY_TETRIS_GAME;
    private static final int MINOR_PADDING = 5;
    private static final int MAJOR_PADDING = 10;
    private static final int EAST_PANEL_WIDTH = J_FRAME_WIDTH - WEST_PANEL_WIDTH;
    private static final int EAST_PANEL_COMP_HEIGHT = GAME_BOARD_HEIGHT / 3;

    private static final GameLogic MY_GAME_LOGIC;

    private final KeyMapper myKeyMapper;

    static {
        MY_TETRIS_GAME = new TetrisGame();
        MY_GAME_LOGIC = new GameLogic(MY_TETRIS_GAME);
    }

    /**
     * Constructor for Base Layout.
     */
    public BaseLayout() {
        super();

        layoutComponents();

        myKeyMapper = new KeyMapper(this, MY_TETRIS_GAME);

        setupKeys();

    }

    private void layoutComponents() {
        setLayout(new BorderLayout());

        setBorder(BorderFactory.createEmptyBorder(
                MAJOR_PADDING, MAJOR_PADDING, MAJOR_PADDING, MAJOR_PADDING));

        // game board lives on the west
        final JPanel westPanel = new JPanel();
        final GameBoardPanel gameBoard = new GameBoardPanel(WEST_PANEL_WIDTH, GAME_BOARD_HEIGHT);

        westPanel.add(gameBoard, BorderLayout.WEST);

        // information, such as the next piece, controls, and score, live on the east
        final JPanel eastPanel = new JPanel();
        eastPanel.setLayout(new BoxLayout(eastPanel, BoxLayout.Y_AXIS));
        eastPanel.setPreferredSize(new Dimension(EAST_PANEL_WIDTH, GAME_BOARD_HEIGHT));

        eastPanel.setBorder(BorderFactory.createEmptyBorder(0, MAJOR_PADDING, 0, 0));

        final NextPiecePanel nextPiecePanel = new NextPiecePanel();
        nextPiecePanel.setPreferredSize(new Dimension(EAST_PANEL_WIDTH, EAST_PANEL_COMP_HEIGHT));
        eastPanel.add(nextPiecePanel);

        eastPanel.add(Box.createVerticalStrut(MINOR_PADDING));

        final JPanel controlsInfoPanel = new DirectionLabels();
        controlsInfoPanel.setPreferredSize(new Dimension(EAST_PANEL_WIDTH, EAST_PANEL_COMP_HEIGHT));

        eastPanel.add(controlsInfoPanel);
        eastPanel.add(Box.createVerticalStrut(MINOR_PADDING));

        final ScorePanel scoreInfoPanel = new ScorePanel(MY_GAME_LOGIC);

        scoreInfoPanel.setPreferredSize(new Dimension(EAST_PANEL_WIDTH, EAST_PANEL_COMP_HEIGHT));

        eastPanel.add(scoreInfoPanel);

        add(westPanel, BorderLayout.WEST);
        add(eastPanel, BorderLayout.EAST);

        // add property change listeners
        MY_TETRIS_GAME.addPropertyChangeListener(PROPERTY_ROWS_CLEARED, MY_GAME_LOGIC);
        MY_TETRIS_GAME.addPropertyChangeListener(PROPERTY_GAME_STATE, MY_GAME_LOGIC);
        MY_TETRIS_GAME.addPropertyChangeListener(PROPERTY_FROZEN_BLOCKS, MY_GAME_LOGIC);
        MY_TETRIS_GAME.addPropertyChangeListener(PROPERTY_ROWS_CLEARED, scoreInfoPanel);
        MY_TETRIS_GAME.addPropertyChangeListener(PROPERTY_GAME_STATE, scoreInfoPanel);
        MY_TETRIS_GAME.addPropertyChangeListener(PROPERTY_FROZEN_BLOCKS, scoreInfoPanel);
        MY_TETRIS_GAME.addPropertyChangeListener(gameBoard);
        MY_TETRIS_GAME.addPropertyChangeListener(nextPiecePanel);
        MY_TETRIS_GAME.addPropertyChangeListener(new AudioManager());
        MY_TETRIS_GAME.addPropertyChangeListener(new AudioManagerFX());
        ColorSchemeFactory.addPropertyChangeListener(gameBoard);
        ColorSchemeFactory.addPropertyChangeListener(nextPiecePanel);

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
        final BaseLayout mainPanel = new BaseLayout();

        final JFrame window = new JFrame("Group 3 Tetris");
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setResizable(false);

        final FileMenu menuBar = new FileMenu(window, MY_TETRIS_GAME);
        window.setJMenuBar(menuBar);

        window.setContentPane(mainPanel);
        window.pack();

        final Toolkit tk = Toolkit.getDefaultToolkit();
        window.setLocation(
                (tk.getScreenSize().width - window.getWidth()) / 2,
                (tk.getScreenSize().height - window.getHeight()) / 2
        );

        window.addWindowFocusListener(new MusicWindowListener());

        window.setVisible(true);
    }

    private static final class MusicWindowListener implements WindowFocusListener {

        @Override
        public void windowGainedFocus(final WindowEvent theEvent) {
            MY_TETRIS_GAME.unPause();

            if (GameControls.GameState.RUNNING.equals(MY_GAME_LOGIC.getLastGameState())) {
                AudioManager.startMusic();
            }
        }

        @Override
        public void windowLostFocus(final WindowEvent theEvent) {
            MY_TETRIS_GAME.pause();
            AudioManager.stopMusic();
        }
    }
}
