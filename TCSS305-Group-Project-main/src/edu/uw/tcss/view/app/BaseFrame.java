package edu.uw.tcss.view.app;

import static edu.uw.tcss.view.app.assets.AssetsManager.IMAGES_PATH;

import edu.uw.tcss.model.GameControls;
import edu.uw.tcss.model.TetrisGame;
import edu.uw.tcss.view.app.assets.AssetsManager;
import edu.uw.tcss.view.util.AudioMusicManager;
import edu.uw.tcss.view.util.PreferencesManager;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.Window;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 * The class that is responsible for the frame.
 *
 * @author Roman Bureacov
 * @version 2025-03-11
 */
public class BaseFrame extends JFrame {
    private final TetrisGame myTetrisGame;
    private final GameLogic myGameLogic;

    /**
     * Constructs the frame that holds the game.
     */
    public BaseFrame() {
        super();
        myTetrisGame = new TetrisGame();
        myGameLogic = new GameLogic(myTetrisGame);

        final FileMenu menuBar = new FileMenu(this, myTetrisGame);
        this.setJMenuBar(menuBar);

        final JPanel mainPanel = new BasePanel(myTetrisGame, myGameLogic);
        this.setContentPane(mainPanel);

        configWindow();

        PreferencesManager.retrievePreferences();

        this.setVisible(true);
    }

    private void configWindow() {
        setTitle("Group 03 Tetris");
        final ImageIcon tetrisIcon = new ImageIcon(
                AssetsManager.getFilePath(IMAGES_PATH, "tetrominoIcon.png"));

        final Image image = tetrisIcon.getImage();

        setIconImage(image);

        pack();

        final Toolkit tk = Toolkit.getDefaultToolkit();
        setLocation(
                (tk.getScreenSize().width - getWidth()) / 2,
                (tk.getScreenSize().height - getHeight()) / 2
        );

        setResizable(false);

        final BaseFrameWindowListener listener = new BaseFrameWindowListener();
        addWindowFocusListener(listener);
        addWindowListener(listener);
    }

    /**
     * Creates the frame that holds the game from
     * a static context.
     */
    public static void createFrame() {
        new BaseFrame();

    }

    private final class BaseFrameWindowListener extends WindowAdapter {

        @Override
        public void windowGainedFocus(final WindowEvent theEvent) {
            myTetrisGame.unPause();

            if (GameControls.GameState.RUNNING.equals(myGameLogic.getLastGameState())) {
                AudioMusicManager.startMusic();
            }
        }

        @Override
        public void windowLostFocus(final WindowEvent theEvent) {
            myTetrisGame.pause();
            AudioMusicManager.stopMusic();
        }

        @Override
        public void windowClosing(final WindowEvent theEvent) {
            PreferencesManager.setPreferences();
            System.exit(0);
        }

    }
}
