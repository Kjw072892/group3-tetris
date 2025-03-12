package edu.uw.tcss.view.app;

import edu.uw.tcss.model.GameControls;
import edu.uw.tcss.model.TetrisGame;
import edu.uw.tcss.view.util.AudioManager;
import java.awt.Toolkit;
import java.awt.event.WindowEvent;
import java.awt.event.WindowFocusListener;
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

        this.setVisible(true);

    }

    private void configWindow() {
        setTitle("Group 03 Tetris");

        pack();

        final Toolkit tk = Toolkit.getDefaultToolkit();
        setLocation(
                (tk.getScreenSize().width - getWidth()) / 2,
                (tk.getScreenSize().height - getHeight()) / 2
        );

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        addWindowFocusListener(new MusicWindowListener());
    }

    /**
     * Creates the frame that holds the game from
     * a static context.
     */
    public static void createFrame() {
        final JFrame gameFrame = new BaseFrame();
        System.out.println(gameFrame.getSize());
    }

    private final class MusicWindowListener implements WindowFocusListener {

        @Override
        public void windowGainedFocus(final WindowEvent theEvent) {
            myTetrisGame.unPause();

            if (GameControls.GameState.RUNNING.equals(myGameLogic.getLastGameState())) {
                AudioManager.startMusic();
            }
        }

        @Override
        public void windowLostFocus(final WindowEvent theEvent) {
            myTetrisGame.pause();
            AudioManager.stopMusic();
        }
    }
}
