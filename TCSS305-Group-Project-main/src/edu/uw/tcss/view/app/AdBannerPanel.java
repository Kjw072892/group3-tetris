package edu.uw.tcss.view.app;

import static edu.uw.tcss.view.app.assets.AssetsManager.IMAGES_PATH;

import edu.uw.tcss.view.app.assets.AssetsManager;
import edu.uw.tcss.view.util.GraphicsHandler;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.JPanel;
import javax.swing.Timer;

public class AdBannerPanel extends JPanel {

    private static final int BANNER_WIDTH = 500;
    private static final int BANNER_HEIGHT = 100;

    private final List<BufferedImage> myAdvertisements = new ArrayList<>();
    private int myAdvertisementIndex;

    private BufferedImage myCurrentAdvertisement;

    /**
     * Creates a fixed-size banner panel that displays advertisements.
     */
    public AdBannerPanel() {
        super();
        try {
            loadAdvertisements();
        } catch (final IOException e) {
            Logger.getAnonymousLogger().log(Level.WARNING, "File not found", e.getStackTrace());
        }
        setPreferredSize(new Dimension(BANNER_WIDTH, BANNER_HEIGHT));
        setBackground(Color.MAGENTA);
        setOpaque(true);

        final Timer timer = new Timer(5000, theEvent -> changeAdvertisement());
        timer.start();

        myCurrentAdvertisement = myAdvertisements.getFirst();

        repaint();
    }

    private void loadAdvertisements() throws IOException {
        myAdvertisements.add(ImageIO.read(AssetsManager.getFile(IMAGES_PATH,
                "adBanner.jpg")));
        myAdvertisements.add(ImageIO.read(AssetsManager.getFile(IMAGES_PATH,
                "SampleAdvertisement.png")));
        myAdvertisements.add(ImageIO.read(AssetsManager.getFile(IMAGES_PATH,
                "adBanner2.jpeg")));
        myAdvertisements.add(ImageIO.read(AssetsManager.getFile(IMAGES_PATH,
                "adBanner3.jpg")));
        myAdvertisements.add(ImageIO.read(AssetsManager.getFile(IMAGES_PATH,
                "dairyQueenAd.png")));

    }

    private void changeAdvertisement() {
        myAdvertisementIndex = (myAdvertisementIndex + 1) % myAdvertisements.size();
        myCurrentAdvertisement = myAdvertisements.get(myAdvertisementIndex);
        repaint();
    }

    @Override
    protected void paintComponent(final Graphics theGraphics) {
        super.paintComponent(theGraphics);
        final Graphics2D g2d = (Graphics2D) theGraphics;
        GraphicsHandler.enableAntiAliasing(g2d);

        g2d.drawImage(myCurrentAdvertisement, 0, 0, getWidth(), getHeight(), this);
    }
}