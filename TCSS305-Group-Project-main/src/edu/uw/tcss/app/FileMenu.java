package edu.uw.tcss.app;

import javax.swing.*;

public class FileMenu extends JMenuBar {

    private boolean myGameShouldStart;
    private final JMenuItem myGameStart;

    public FileMenu(final JFrame theFrame) {
        super();
        final JMenu fileMenu = new JMenu("Options");

        // Create menu items
        myGameStart = new JMenuItem("Start Game");
        final JMenuItem about = new JMenuItem("About");
        final JMenuItem exitItem = new JMenuItem("Exit");

        // Add action listeners
        myGameStart.addActionListener(e -> setGameShouldStart(theFrame));
        about.addActionListener(e -> JOptionPane.showMessageDialog(theFrame, "Made by Zainab, James, Roma, and Kassie."));
        exitItem.addActionListener(e -> System.exit(0));

        // Add items to File menu
        fileMenu.add(myGameStart);
        fileMenu.add(about);
        fileMenu.add(exitItem);

        // Add File menu to menu bar
        this.add(fileMenu);
    }

    private void setGameShouldStart(final JFrame theFrame) {
        JOptionPane.showMessageDialog(theFrame, "The game will start upon exit of window.");
        System.out.println("Stopped running Infinite Loop");
        myGameShouldStart = true;
    }

    public boolean GetGameShouldStart() {
        return myGameShouldStart;
    }

}