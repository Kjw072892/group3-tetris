package edu.uw.tcss.app;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 * BorderLayout base Panel.
 * @author Kassie Whitney
 * @author Zainab Stanikzy
 * @version 2.26.25
 */
public final class BaseLayout extends JPanel {

    private static final int J_FRAME_WIDTH = 510;

    private static final int J_FRAME_HEIGHT = 610;

    private static final int GAME_BOARD_WIDTH = 270;

    private static final int GAME_BOARD_HEIGHT = GAME_BOARD_WIDTH * 2;

    private static final int SEVEN = 7;

    private static final int THREE = 3;


    /**
     * Constructor for Base Layout.
     */
    public BaseLayout() {
        super();
        layoutComponents();
    }

    private void layoutComponents() {
        setLayout(new BorderLayout());

        setBorder(BorderFactory.createEmptyBorder(SEVEN, SEVEN, SEVEN, SEVEN));

        //WEST
        final JPanel gameBoard = new JPanel();
        gameBoard.setPreferredSize(new Dimension(GAME_BOARD_WIDTH, GAME_BOARD_HEIGHT));
        gameBoard.setBackground(Color.RED);

        final JPanel eastPanel = new JPanel();
        eastPanel.setLayout(new BoxLayout(eastPanel, BoxLayout.Y_AXIS));
        eastPanel.setBorder(BorderFactory.createEmptyBorder(0, 45, 0,
                0));
        eastPanel.setPreferredSize(new Dimension(250,
                GAME_BOARD_HEIGHT / 2));



        final NextPiece nextPiece = new NextPiece();
        eastPanel.add(nextPiece);
        nextPiece.setBackground(Color.BLUE);
        nextPiece.setPreferredSize(new Dimension(200,
                GAME_BOARD_HEIGHT - 310));


        eastPanel.add(filler(new Dimension(5,5)));

        final JPanel controls = new JPanel();
        controls.setPreferredSize(new Dimension(GAME_BOARD_HEIGHT / THREE,
                GAME_BOARD_HEIGHT / THREE));
        controls.setBackground(Color.GREEN);
        eastPanel.add(controls);

        eastPanel.add(filler(new Dimension(5,5)));

        final JPanel score = new JPanel();
        score.setPreferredSize(new Dimension(GAME_BOARD_HEIGHT / THREE,
                GAME_BOARD_HEIGHT / THREE));
        score.setBackground(Color.GREEN);
        eastPanel.add(score);

        add(gameBoard, BorderLayout.WEST);
        add(eastPanel, BorderLayout.EAST);

        this.setOpaque(true);

    }
    private static Box.Filler filler(final Dimension theDim) {
        return new Box.Filler(theDim, theDim, theDim);
    }
    /**
     * Creates the JFrame.
     */
    public static void createAndShowGui() {
        final BaseLayout mainPanel = new BaseLayout();

        final JFrame window = new JFrame("Group 3 Tetris");
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        final FileMenu menuBar = new FileMenu(window);


        window.setJMenuBar(menuBar);

        window.setPreferredSize(new Dimension(J_FRAME_WIDTH, J_FRAME_HEIGHT));

        window.setContentPane(mainPanel);

        window.pack();
        window.setVisible(true);
        window.setResizable(false);
    }
}
