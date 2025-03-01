package edu.uw.tcss.app;

import javax.swing.JColorChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

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

//    private boolean myGameShouldStart;
     // Create menu items
    private final JMenuItem myGameStart = new JMenuItem("Start Game");
    private final JMenuItem myAbout = new JMenuItem("About");
    private final JMenuItem myExitGame = new JMenuItem("Exit");
    private final JMenuItem myColorChooser = new JMenuItem("Choose Color");

    /**
     * Constructor for file menu class.
     * @param theFrame the main JFrame object.
     */
    public FileMenu(final JFrame theFrame) {
        super();
        //adds file j-menu
        final JMenu fileMenu = new JMenu("File");
        fileMenuCreation(fileMenu);
        //adds feature j-menu
        final JMenu featureMenu = new JMenu("Feature");
        featureMenuCreation(featureMenu);

        final JMenu helpMenuItem = new JMenu("Help");
        helpMenuCreation(helpMenuItem);

        //invokes the listeners
        addListeners(theFrame);

        // Add a File menu to menu bar
        this.add(fileMenu);
        this.add(featureMenu);
        this.add(helpMenuItem);
    }

    private void fileMenuCreation(final JMenu theJMenu) {
        // Add items to the File menu
        theJMenu.add(myGameStart);
        theJMenu.add(myExitGame);
    }

    private void featureMenuCreation(final JMenu theJmenu) {
        // Add items to the Feature Menu
        final JMenu innerColorMenu = new JMenu("Color");
        innerColorMenu.add(myColorChooser);
        theJmenu.add(innerColorMenu);
    }

    private void helpMenuCreation(final JMenu theJmenu) {
        // Add items to the Help menu

        theJmenu.add(myAbout);
    }

    private void addListeners(final JFrame theFrame) {
         // Add action listeners
        myGameStart.addActionListener(e -> setGameShouldStart(theFrame));

        myAbout.addActionListener(e -> JOptionPane.showMessageDialog(theFrame,
                "Made by James, Kassie, Roman, Zainab. \nCurrent version: 2.28.25"));

        myExitGame.addActionListener(e -> System.exit(0));
        myColorChooser.addActionListener(e ->
                JOptionPane.showMessageDialog(theFrame,
                "This option is currently unavailable in this version!"));

       //myGameStart.addActionListener(e->setGameShouldStart(theFrame));
    }

    //  This method currently does nothing, will use later.
    private void setGameShouldStart(final JFrame theFrame) {

      // myGameShouldStart = true;
    }

}