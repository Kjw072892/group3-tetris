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

    private final JMenuItem myFileMenuGameStart = new JMenuItem("Start Game");
    private final JMenuItem myAbout = new JMenuItem("About");
    private final JMenuItem myFileMenuExitGame = new JMenuItem("Exit");
    private final JMenuItem myFeatureMenuColorChooser = new JMenuItem("Choose Color");
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

        setMainMnemonics();

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

    private void setMainMnemonics() {
        myFileMenu.setMnemonic('f');
        myFeatureMenu.setMnemonic('e');
        myHelpMenu.setMnemonic('h');
    }

    private void fileMenuCreation() {
        // Add mnemonics
        myFileMenuGameStart.setMnemonic('s');
        myFileMenuExitGame.setMnemonic('x');

        // Add items to the File menu
        myFileMenu.add(myFileMenuGameStart);
        myFileMenu.add(myFileMenuExitGame);
    }

    private void featureMenuCreation() {
        final JMenu innerColorMenu = new JMenu("Color");

        // Add mnemonics
        innerColorMenu.setMnemonic('c');
        myFeatureMenuColorChooser.setMnemonic('c');

        // Add items to the Feature Menu
        innerColorMenu.add(myFeatureMenuColorChooser);
        myFeatureMenu.add(innerColorMenu);
    }

    private void helpMenuCreation() {
        // Add mnemonics
        myAbout.setMnemonic('a');

        // Add items to the Help menu
        myHelpMenu.add(myAbout);
    }

    private void addListeners(final JFrame theFrame) {
        myAbout.addActionListener(ActionEvent ->
                JOptionPane.showMessageDialog(theFrame,
                "Made by James, Kassie, Roman, Zainab. \nCurrent version: "
                        + myVersion));

        myFileMenuExitGame.addActionListener(ActionEvent -> System.exit(0));

        myFeatureMenuColorChooser.addActionListener(ActionEvent ->
                JOptionPane.showMessageDialog(theFrame,
                "This option is currently unavailable in this version!"));

        myFileMenuGameStart.addActionListener(theEvent -> myTetris.newGame());
    }
}