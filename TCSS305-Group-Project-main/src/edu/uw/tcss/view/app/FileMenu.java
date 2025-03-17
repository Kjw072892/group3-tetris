package edu.uw.tcss.view.app;

import edu.uw.tcss.model.TetrisGame;
import edu.uw.tcss.view.util.AudioFXManager;
import edu.uw.tcss.view.util.AudioMusicFactory;
import edu.uw.tcss.view.util.AudioMusicManager;
import edu.uw.tcss.view.util.ColorSchemeFactory;
import edu.uw.tcss.view.util.ColorSchemeManager;
import edu.uw.tcss.view.util.DrawingFactory;
import edu.uw.tcss.view.util.DrawingManager;
import edu.uw.tcss.view.util.PreferencesManager;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import javax.swing.JCheckBoxMenuItem;
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
public class FileMenu extends JMenuBar implements PropertyChangeListener {
    private final String myVersion = "3.12.25";

    private final JCheckBoxMenuItem myFileMenuPanicMode =
            new JCheckBoxMenuItem("Game starts with Panic Mode");

    private final String myReferenceStr = "References";
    private final String myControlStr = "Controls layout";
    private final String myAboutStr = "About";

    private final JMenu myHelpMenu = new JMenu("Help");
    private final JMenuItem myHelpMenuAbout = new JMenuItem(myAboutStr);
    private final JMenuItem myHelpMenuReferences = new JMenuItem(myReferenceStr);
    private final JMenuItem myHelpControls = new JMenuItem(myControlStr);

    private final JMenu myFileMenu = new JMenu("File");
    private final JMenuItem myFileMenuStartGame = new JMenuItem("Start Game");
    private final JMenuItem myFileMenuExitGame = new JMenuItem("Exit");

    private final JMenu myFeatureMenu = new JMenu("Features");
    private final JMenuItem myFeatureMenuBackGroundMusic = new JMenuItem("Background Music");
    private final JMenuItem myFeatureMenuColorChooser = new JMenuItem("Choose Theme");
    private final JMenuItem myFeatureMenuBlockDrawing = new JMenuItem("Choose Block Look");
    private final JMenuItem myFeatureMenuClearPreferences = new JMenuItem("Clear Preferences");
    private final JCheckBoxMenuItem myFeatureMenuMuteMusic = new JCheckBoxMenuItem("Mute Music");
    private final JCheckBoxMenuItem myFeatureMenuMuteSFX = new JCheckBoxMenuItem("Mute SFX");

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
        myFileMenuExitGame.setMnemonic('x');
        myFileMenuPanicMode.setMnemonic('p');
        myFileMenuStartGame.setMnemonic('s');


        // Add items to the File menu
        myFileMenu.add(myFileMenuPanicMode);
        myFileMenu.add(myFileMenuStartGame);
        myFileMenu.add(myFileMenuExitGame);

        myFileMenuPanicMode.setState(true);
        myTetris.setPanicMode(true);
    }

    private void featureMenuCreation() {
        final JMenu innerColorMenu = new JMenu("Themes");

        // Add mnemonics
        innerColorMenu.setMnemonic('c');
        myFeatureMenuColorChooser.setMnemonic('c');
        myFeatureMenuBackGroundMusic.setMnemonic('b');
        myFeatureMenuClearPreferences.setMnemonic('l');

        // Add items to the Feature Menu
        innerColorMenu.add(myFeatureMenuColorChooser);
        innerColorMenu.add(myFeatureMenuBlockDrawing);
        myFeatureMenu.add(innerColorMenu);
        myFeatureMenu.add(myFeatureMenuBackGroundMusic);
        myFeatureMenu.add(myFeatureMenuMuteMusic);
        myFeatureMenu.add(myFeatureMenuMuteSFX);
        myFeatureMenu.add(myFeatureMenuClearPreferences);
    }

    private void helpMenuCreation() {
        // Add mnemonics
        myHelpMenuAbout.setMnemonic('a');
        myHelpMenuReferences.setMnemonic('r');


        // Add items to the Help menu
        myHelpMenu.add(myHelpMenuAbout);
        myHelpMenu.add(myHelpMenuReferences);
        myHelpMenu.add(myHelpControls);
    }

    private void addListeners(final JFrame theFrame) {
        myHelpMenuAbout.addActionListener(ActionEvent ->
                JOptionPane.showMessageDialog(theFrame,
                        String.format("""
                                <html>
                                    <p style="text-align: center">(Group 3)<p>
                                    <p>Made by: James, Kassie, Roman, Zainab<p>
                                    <p>CurrentVersion: %s</p>
                                </html>
                                """, myVersion),
                        myAboutStr,
                        JOptionPane.PLAIN_MESSAGE));

        myHelpMenuReferences.addActionListener(ActionEvent ->
                JOptionPane.showMessageDialog(theFrame,
                        htmlReference(),
                        myReferenceStr,
                        JOptionPane.INFORMATION_MESSAGE
                ));
        myHelpControls.addActionListener(ActionEvent -> JOptionPane.showMessageDialog(theFrame,
                htmlControl(), "Controls", JOptionPane.PLAIN_MESSAGE
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

        myFeatureMenuBackGroundMusic.addActionListener(theEvent -> {
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

        myFileMenuStartGame.addActionListener(theEvent -> {
            myTetris.setPanicMode(myFileMenuPanicMode.getState());
            myTetris.newGame();
        });

        myFeatureMenuClearPreferences.addActionListener(theEvent ->
            PreferencesManager.clearPreferences());

        myFeatureMenuMuteMusic.addActionListener(theEvent -> AudioMusicManager.toggleMute());
        myFeatureMenuMuteSFX.addActionListener(theEvent -> AudioFXManager.toggleMute());

        myFeatureMenuBlockDrawing.addActionListener(theEvent -> {
            final Object drawingScheme = JOptionPane.showInputDialog(
                    this,
                    "Choose a block look",
                    "Block looks",
                    JOptionPane.QUESTION_MESSAGE,
                    null,
                    DrawingFactory.getDrawingObjects(),
                    DrawingManager.getDrawer()
            );

            if (drawingScheme != null) {
                DrawingManager.setDrawer((DrawingFactory.DrawingScheme) drawingScheme);
            }
        });
    }

    @Override
    public void propertyChange(final PropertyChangeEvent theEvent) {
        switch (theEvent.getPropertyName()) {
            case AudioMusicManager.PROPERTY_MUSIC_MUTING ->
                    myFeatureMenuMuteMusic.setState((Boolean) theEvent.getNewValue());
            case AudioFXManager.PROPERTY_AUDIO_MUTING ->
                    myFeatureMenuMuteSFX.setState((Boolean) theEvent.getNewValue());
            default -> { }
        }
    }

    private String htmlControl() {
        return """
                <html>
                    <style>
                        td.right {text-align: right}
                    </style>
                     <table style="width: 180px; border-spacing:10px 5px;">
                           <tr><td><b>New Game:</b></td> <td class="right">N/n</td></tr>
                           <tr><td><b>Move Left:</b></td>  <td class="right">Left or A/a</td></tr>
                           <tr><td><b>Move Right:</b></td> <td class="right">Right or D/d</td></tr>
                           <tr><td><b>Move Down:</b></td>  <td class="right">Down or S/s</td></tr>
                           <tr><td><b>Move CCW:</b></td>   <td class="right">Q/q</td></tr>
                           <tr><td><b>Move CW:</b></td>   <td class="right">E/e</td></tr>
                           <tr><td><b>Drop:</b></td>  <td class="right">Spacebar</td></tr>
                           <tr><td><b>Pause:</b></td>  <td class="right">P/p</td></tr>
                           <tr><td><b>Quit:</b></td>   <td class="right">K/k</td></tr>
                           <tr><td><b>Mute:</b></td>  <td class="right">M/m</td></tr>
                     </table>
                <html>
                """;
    }

    private String htmlReference() {
        return """
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
                """;
    }


}