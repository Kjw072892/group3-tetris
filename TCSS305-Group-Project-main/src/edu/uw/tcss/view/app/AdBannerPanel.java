package edu.uw.tcss.view.app;

import java.awt.Color;
import java.awt.Dimension;
import java.util.ArrayList;
import java.util.List;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.Timer;

public class AdBannerPanel extends JPanel {
    private static final int BANNER_WIDTH = 500;
    private static final int BANNER_HEIGHT = 100;

    private final Timer myTimer = new Timer(5000, theEvent -> changeAd());
    private final List<ImageIcon> myAdvertisements = new ArrayList<>();
    private int myCurrentAdvertisementIndex = 0;

    /**
     * Creates a new ad banner panel, which displays advertisement over an interval.
     */
    public AdBannerPanel() {
        super();
        loadAdvertisements();

        setPreferredSize(new Dimension(BANNER_WIDTH, BANNER_HEIGHT));
        setBackground(Color.MAGENTA);
        setOpaque(true);
    }

    private void changeAd() {
        // TODO: change ads on a timer
        myCurrentAdvertisementIndex = (myCurrentAdvertisementIndex + 1) % myAdvertisements.size();

    }

    private void loadAdvertisements() {
        // TODO: add advertisements
    }
}
