package edu.uw.tcss.view.app;

import edu.uw.tcss.model.TetrisGame;
import edu.uw.tcss.view.util.AudioMusicManager;

import edu.uw.tcss.view.util.AudioMusicFactory;
import edu.uw.tcss.view.util.ColorSchemeFactory;
import edu.uw.tcss.view.util.ColorSchemeManager;
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
    private final JMenuItem myFeatureBackGroundMusic = new JMenuItem("Background Music");
    private final JMenu myFileMenu = new JMenu("File");
    private final JMenu myFeatureMenu = new JMenu("Features");
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
        myFeatureBackGroundMusic.setMnemonic('b');

        // Add items to the Feature Menu
        innerColorMenu.add(myFeatureMenuColorChooser);
        myFeatureMenu.add(innerColorMenu);
        myFeatureMenu.add(myFeatureBackGroundMusic);
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

        myFeatureMenuColorChooser.addActionListener(ActionEvent -> {
            final Object scheme = JOptionPane.showInputDialog(
                    this,
                    "Choose a color scheme",
                    "Choose Scheme",
                    JOptionPane.QUESTION_MESSAGE,
                    null,
                    ColorSchemeFactory.getColorSchemes(),
                    ColorSchemeManager.getCurrentColorScheme()
            );

            if (scheme != null) {
                ColorSchemeManager.setCurrentColorScheme((ColorSchemeFactory.ColorScheme) scheme);
            }
        });

        myFeatureBackGroundMusic.addActionListener(theEvent -> {
            final Object music = JOptionPane.showInputDialog(
                    this,
                    "Choose a song",
                    "Songs",
                    JOptionPane.QUESTION_MESSAGE,
                    null,
                    AudioMusicFactory.getBackgroundMusic(),
                    AudioMusicFactory.getMusicEpic()

            );

            if (music != null) {
                AudioMusicManager.setMusic((AudioMusicFactory.BackgroundMusic) music);
            }
        });

        myFileMenuGameStart.addActionListener(theEvent -> myTetris.newGame());

        //TODO: Create helper method to work with audio manager
        //myFeatureBackGroundMusic.addActionListener(theEvent ->);
    }


}