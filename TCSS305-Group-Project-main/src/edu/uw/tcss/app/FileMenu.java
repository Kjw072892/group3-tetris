package edu.uw.tcss.app;

import edu.uw.tcss.model.TetrisGame;

import javax.swing.*;

/**
 * FileMenu creation class.
 *
 * @author James
 * @author Kassie
 * @author Roman
 * @author Zainab
 * @version 2.28.25
 */
public class FileMenu extends JMenuBar {
    private static final int DEFAULT_DELAY = 1000;
    private final String myVersion = "3.8.25";

    private final JMenuItem myGameStart = new JMenuItem("Start Game");
    private final JMenuItem myAbout = new JMenuItem("About");
    private final JMenuItem myExitGame = new JMenuItem("Exit");
    private final JMenuItem myColorChooser = new JMenuItem("Choose Color");
    private final JMenu myFileMenu = new JMenu("File");
    private final JMenu myFeatureMenu = new JMenu("Feature");
    private final JMenu myHelpMenu = new JMenu("Help");
    private final TetrisGame myTetris;



    /**
     * Constructor for file menu class.
     * @param theFrame the main JFrame object.
     */
    public FileMenu(final JFrame theFrame, final TetrisGame theGame) {
        super();
        myTetris = theGame;
        // Adds the file menu items on the bar
        fileMenuCreation();
        featureMenuCreation();
        helpMenuCreation();

        //invokes the listeners
        addListeners(theFrame);

        // Add a File menu to menu bar
        add(myFileMenu);
        add(myFeatureMenu);
        add(myHelpMenu);
    }

    private void fileMenuCreation() {
        // Add items to the File menu
        myFileMenu.add(myGameStart);
        myFileMenu.add(myExitGame);
    }

    private void featureMenuCreation() {
        // Add items to the Feature Menu
        final JMenu innerColorMenu = new JMenu("Color");
        innerColorMenu.add(myColorChooser);
        myFeatureMenu.add(innerColorMenu);
    }

    private void helpMenuCreation() {
        // Add items to the Help menu

        myHelpMenu.add(myAbout);
    }

    private void addListeners(final JFrame theFrame) {


        myAbout.addActionListener(ActionEvent ->
                JOptionPane.showMessageDialog(theFrame,
                "Made by James, Kassie, Roman, Zainab. \nCurrent version: "
                        + myVersion));

        myExitGame.addActionListener(ActionEvent -> System.exit(0));

        myColorChooser.addActionListener(ActionEvent ->
                JOptionPane.showMessageDialog(theFrame,
                "This option is currently unavailable in this version!"));

        myGameStart.addActionListener(theEvent -> myTetris.newGame());
        myGameStart.addActionListener(theEvent -> startTimer());

    }

    private void startTimer() {
        final TetrisGame tetrisGame = new TetrisGame();
        final Timer timer;
        timer = new Timer(DEFAULT_DELAY, theEvent -> tetrisGame.step());
        timer.start();

    }


}