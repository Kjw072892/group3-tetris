package edu.uw.tcss.view.app;

import static edu.uw.tcss.view.app.assets.AssetsManager.IMAGES_PATH;
import edu.uw.tcss.view.app.assets.AssetsManager;
import edu.uw.tcss.view.util.GraphicsModifier;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.Timer;

public class AdBannerPanel extends JPanel {
    private static final int BANNER_WIDTH = 500;
    private static final int BANNER_HEIGHT = 100;

    private final Timer myTimer = new Timer(5000, theEvent -> changeAd());
    private final List<ImageIcon> myAdvertisements = new ArrayList<>();
    private int myCurrentAdvertisementIndex = 0;
    private JLabel myImageDisplay = new JLabel();

    /**
     * Creates a new ad banner panel, which displays advertisement over an interval.
     */
    public AdBannerPanel() {
        super();
        loadAdvertisements();
        myImageDisplay.setIcon(myAdvertisements.getFirst());
        myTimer.start();

        setPreferredSize(new Dimension(BANNER_WIDTH, BANNER_HEIGHT));
        add(myImageDisplay);
        setBackground(Color.MAGENTA);
        setBorder(BorderFactory.createEmptyBorder(0, 0, 10, 0));
        setOpaque(true);
    }

    private void changeAd() {
        // TODO: change ads on a timer
        myCurrentAdvertisementIndex = (myCurrentAdvertisementIndex + 1) % myAdvertisements.size();
        repaint();
    }

    @Override
    public void paintComponent(final Graphics theGraphics) {
        super.paintComponent(theGraphics);
        final Graphics2D g2d = (Graphics2D) theGraphics;
        GraphicsModifier.enableAntiAliasing(g2d);

        final ImageIcon workingAd = myAdvertisements.get(myCurrentAdvertisementIndex);
        // g2d.drawImage(workingAd.getImage(), 0, 0, null);
    }

    private void loadAdvertisements() {
        myAdvertisements.add(new ImageIcon(
                AssetsManager.getFilePath(IMAGES_PATH, "SampleAdvertisement.png")));
    }
}
