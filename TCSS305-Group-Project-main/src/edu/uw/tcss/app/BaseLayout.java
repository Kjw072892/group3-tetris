package edu.uw.tcss.app;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Insets;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.BorderFactory;
import javax.swing.border.Border;


/**
 * BorderLayout base Panel.
 * @author Kassie Whitney
 * @author Zainab Stanikzy
 * @version 2.26.25
 */
public final class BaseLayout extends JPanel {

    private static final int J_FRAME_WIDTH = 500;

    private static final int J_FRAME_HEIGHT = 700;

    private static final int GAME_BOARD_WIDTH = 200;

    private static final int GAME_BOARD_HEIGHT = GAME_BOARD_WIDTH * 2;


    private static final int SIDE_PANEL_WIDTH = 100;

    private static final int SIDE_PANEL_HEIGHT = 200;
    






    /**
     * Constructor for Base Layout.
     */
    public BaseLayout() {
        super();
        layoutComponents();
    }

    private void layoutComponents() {
        setLayout(new BorderLayout());

        setBorder(BorderFactory.createEmptyBorder(7,7,7,7));

        //WEST
        final JPanel gameBoard = new JPanel();
        gameBoard.setPreferredSize(new Dimension(GAME_BOARD_WIDTH, GAME_BOARD_HEIGHT));
        gameBoard.setBackground(Color.RED);





        final JPanel eastPanel = new JPanel();
        eastPanel.setLayout(new BoxLayout(eastPanel, BoxLayout.Y_AXIS));
        eastPanel.setPreferredSize(new Dimension(133, GAME_BOARD_HEIGHT));

        eastPanel.setBorder(BorderFactory.createEmptyBorder(0,7,0,0));

        //final JPanel nextPiece = new JPanel();
        //nextPiece.setPreferredSize(new Dimension(100, GAME_BOARD_HEIGHT/3));
        //nextPiece.setBackground(Color.BLUE);

        // now just calls our next class... -James
        eastPanel.add(new NextPiece());

        eastPanel.add(filler(new Dimension(5,5)));

        final JPanel controls = new JPanel();
        controls.setPreferredSize(new Dimension(100, GAME_BOARD_HEIGHT/3));
        controls.setBackground(Color.GREEN);
        eastPanel.add(controls);


        
        eastPanel.add(filler(new Dimension(5,5)));


        final JPanel score = new JPanel();
        score.setPreferredSize(new Dimension(100, GAME_BOARD_HEIGHT/3));
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

        window.setSize(J_FRAME_WIDTH, J_FRAME_HEIGHT);

        window.setContentPane(mainPanel);

        window.pack();
        window.setVisible(true);
    }
}
