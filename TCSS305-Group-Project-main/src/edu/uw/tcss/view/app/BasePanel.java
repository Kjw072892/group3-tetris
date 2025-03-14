package edu.uw.tcss.view.app;

import static edu.uw.tcss.model.PropertyChangeEnabledGameControls.PROPERTY_FROZEN_BLOCKS;
import static edu.uw.tcss.model.PropertyChangeEnabledGameControls.PROPERTY_GAME_STATE;
import static edu.uw.tcss.model.PropertyChangeEnabledGameControls.PROPERTY_ROWS_CLEARED;
import static edu.uw.tcss.view.util.StyleManager.MAJOR_PADDING;
import static edu.uw.tcss.view.util.StyleManager.MINOR_PADDING;
import static javax.swing.KeyStroke.getKeyStroke;

import edu.uw.tcss.model.TetrisGame;
import edu.uw.tcss.view.app.keymaps.GameAction;
import edu.uw.tcss.view.app.keymaps.KeyMapper;
import edu.uw.tcss.view.app.keymaps.TetrominoAction;
import edu.uw.tcss.view.util.AudioFXListener;
import edu.uw.tcss.view.util.AudioFXManager;
import edu.uw.tcss.view.util.AudioMusicListener;
import edu.uw.tcss.view.util.AudioMusicManager;
import edu.uw.tcss.view.util.ColorSchemeManager;
import java.awt.BorderLayout;
import java.awt.Dimension;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JPanel;

/**
 * BorderLayout base Panel.
 *
 * @author Roman Bureacov
 * @author Zainab Stanikzy
 * @author Kassie Whitney
 * @version 2.26.25
 */
public final class BasePanel extends JPanel {

    private static final int J_FRAME_WIDTH = 500;

    // the amount the west panel should take up on the screen
    private static final double WEST_PANEL_PERCENTAGE = 3d / 5d;

    private static final int WEST_PANEL_WIDTH = (int) (J_FRAME_WIDTH * WEST_PANEL_PERCENTAGE);

    // aspect ratio for the game board should be 2:1
    private static final int GAME_BOARD_HEIGHT = WEST_PANEL_WIDTH * 2;

    private static final int EAST_PANEL_WIDTH = J_FRAME_WIDTH - WEST_PANEL_WIDTH;
    private static final int EAST_PANEL_COMP_HEIGHT = GAME_BOARD_HEIGHT / 3;

    private static final int BORDER_BOTTOM_PADDING = -2;

    private final TetrisGame myTetrisGame;
    private final KeyMapper myKeyMapper;
    private final GameLogic myGameLogic;

    /**
     * Constructor for Base Layout.
     */
    public BasePanel(final TetrisGame theGame, final GameLogic theLogic) {
        super();

        myTetrisGame = theGame;
        myGameLogic = theLogic;

        layoutComponents();

        myKeyMapper = new KeyMapper(this, theGame);
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

        eastPanel.setBorder(BorderFactory.createEmptyBorder(MINOR_PADDING, MAJOR_PADDING,
                BORDER_BOTTOM_PADDING,  0));

        final NextPiecePanel nextPiecePanel = new NextPiecePanel();
        nextPiecePanel.setPreferredSize(new Dimension(EAST_PANEL_WIDTH, EAST_PANEL_COMP_HEIGHT));
        eastPanel.add(nextPiecePanel);

        eastPanel.add(Box.createVerticalStrut(MINOR_PADDING));

        final DirectionLabelsPanel controlsInfoPanel = new DirectionLabelsPanel();
        controlsInfoPanel.setPreferredSize(new Dimension(EAST_PANEL_WIDTH, EAST_PANEL_COMP_HEIGHT));

        eastPanel.add(controlsInfoPanel);
        eastPanel.add(Box.createVerticalStrut(MINOR_PADDING));

        final ScorePanel scoreInfoPanel = new ScorePanel(myGameLogic);

        scoreInfoPanel.setPreferredSize(new Dimension(EAST_PANEL_WIDTH, EAST_PANEL_COMP_HEIGHT));

        eastPanel.add(scoreInfoPanel);
        eastPanel.add(Box.createVerticalStrut(MINOR_PADDING));

        add(westPanel, BorderLayout.WEST);
        add(eastPanel, BorderLayout.EAST);

        final AdBannerPanel banner = new AdBannerPanel();
        add(banner, BorderLayout.SOUTH);

        // add property change listeners
        final AudioMusicListener musicListener = new AudioMusicListener();

        myTetrisGame.addPropertyChangeListener(PROPERTY_ROWS_CLEARED, myGameLogic);
        myTetrisGame.addPropertyChangeListener(PROPERTY_GAME_STATE, myGameLogic);
        myTetrisGame.addPropertyChangeListener(PROPERTY_FROZEN_BLOCKS, myGameLogic);
        myTetrisGame.addPropertyChangeListener(PROPERTY_ROWS_CLEARED, scoreInfoPanel);
        myTetrisGame.addPropertyChangeListener(PROPERTY_GAME_STATE, scoreInfoPanel);
        myTetrisGame.addPropertyChangeListener(PROPERTY_FROZEN_BLOCKS, scoreInfoPanel);
        myTetrisGame.addPropertyChangeListener(gameBoard);
        myTetrisGame.addPropertyChangeListener(nextPiecePanel);
        myTetrisGame.addPropertyChangeListener(musicListener);
        myTetrisGame.addPropertyChangeListener(new AudioFXListener());
        ColorSchemeManager.addPropertyChangeListener(gameBoard);
        ColorSchemeManager.addPropertyChangeListener(nextPiecePanel);
        ColorSchemeManager.addPropertyChangeListener(scoreInfoPanel);
        ColorSchemeManager.addPropertyChangeListener(controlsInfoPanel);
        ColorSchemeManager.addPropertyChangeListener(musicListener);
        AudioMusicManager.addPropertyChangeListener(musicListener);
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
                getKeyStroke("pressed Q"), TetrominoAction.Controls.ROTATE_CCW);
        myKeyMapper.mapTetrominoAction(
                getKeyStroke("pressed E"), TetrominoAction.Controls.ROTATE_CW);

        myKeyMapper.mapGameAction(
                getKeyStroke("pressed P"), GameAction.Controls.TOGGLE_PAUSE);
        myKeyMapper.mapGameAction(
                getKeyStroke("pressed N"), GameAction.Controls.NEW_GAME);
        myKeyMapper.mapGameAction(
                getKeyStroke("pressed K"), GameAction.Controls.END_GAME);

    }
}
