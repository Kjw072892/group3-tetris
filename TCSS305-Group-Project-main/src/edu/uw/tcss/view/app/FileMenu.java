package edu.uw.tcss.view.app;

import edu.uw.tcss.model.TetrisGame;
import edu.uw.tcss.view.util.AudioMusicFactory;
import edu.uw.tcss.view.util.AudioMusicManager;
import edu.uw.tcss.view.util.ColorSchemeFactory;
import edu.uw.tcss.view.util.ColorSchemeManager;
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
    private final String myVersion = "3.12.25";

    private final JMenuItem myFileMenuGameStart = new JMenuItem("Start Game With Panic Mode");
    private final JMenuItem myFileMenuGameStart2 = new JMenuItem("Start Game Without Panic Mode");
    private final JMenuItem myAbout = new JMenuItem("About");
    private final JMenuItem myReferences = new JMenuItem("References");
    private final JMenuItem myFileMenuExitGame = new JMenuItem("Exit");
    private final JMenuItem myFeatureMenuColorChooser = new JMenuItem("Choose Theme");
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
        myFileMenuGameStart2.setMnemonic('s');
        myFileMenuExitGame.setMnemonic('x');


        // Add items to the File menu
        myFileMenu.add(myFileMenuGameStart);
        myFileMenu.add(myFileMenuGameStart2);
        myFileMenu.add(myFileMenuExitGame);
    }

    private void featureMenuCreation() {
        final JMenu innerColorMenu = new JMenu("Themes");

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
        myReferences.setMnemonic('r');

        // Add items to the Help menu
        myHelpMenu.add(myAbout);
        myHelpMenu.add(myReferences);
    }

    //TODO: Please cite all of your sources in the myReference. Be mindful of spacing!
    private void addListeners(final JFrame theFrame) {
        myAbout.addActionListener(ActionEvent ->
                JOptionPane.showMessageDialog(theFrame,
                    String.format(
                            """
                            <html>
                                <p style="text-align: center">(GROUP 3)</p>
                                <p>Made by: James, Kassie, Roman, Zainab.<p>
                                <p>Current version: %s</p>
                            </html>
                            """,
                            myVersion
                    ),
                    "About",
                    JOptionPane.INFORMATION_MESSAGE
                ));

        myReferences.addActionListener(ActionEvent -> JOptionPane.showMessageDialog(theFrame,
                """
                        <html>
                            <p>Music and soundFx were found on youtube.
                            We do not own the rights to any of the music or soundFX
                            used in this game.<p>
                            <ul>
                                <li>
                                    ⭐Twinkling Shooting Star⭐(Cute chiptune/8bit music):
                                    https://www.youtube.com/watch?v=3qTAGRz-GjE
                                </li>
                                <li>
                                    Tetris Game SoundFX:
                                    https://www.sounds-resource.com
                                    /nintendo_switch/tetris99/sound/19376/
                                </li>
                                <li>
                                    Trap Tetris (Da Brozz - Tetris (Original Mix)):
                                    https://www.youtube.com/watch?v=AT7KjIOd7GQ
                                </li>
                                <li>
                                    Tension - Everybody's Warming (Extended Mix):
                                    https://www.youtube.com/watch?v=phYUedumuyE
                                </li>
                                <li>
                                    Epic Tetris:  Korobeiniki by the Red Army Choir
                                </li>
                                <li>
                                    Alternative Tetris: The Samovars by the Red Army Choir
                                </li>
                                <li>
                                    Panic Tetris: The Legend Descends - Fatalis by Capcom Sound Team
                                </li>
                        <html>
                        """,
                        "References",
                        JOptionPane.INFORMATION_MESSAGE
                ));

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
                    AudioMusicManager.getCurrentMusic()
            );

            if (music != null) {
                AudioMusicManager.setCurrentMusic((AudioMusicFactory.BackgroundMusic) music);
            }
        });

        myFileMenuGameStart.addActionListener(theEvent -> {
            myTetris.newGame();
            myTetris.setMyCouldPanic(true);
        });

        myFileMenuGameStart2.addActionListener(theEvent -> {
            myTetris.newGame();
            myTetris.setMyCouldPanic(false);
        });
    }


}