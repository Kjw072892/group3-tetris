package edu.uw.tcss.view.app;

import static edu.uw.tcss.view.app.assets.AssetsManager.IMAGES_PATH;
import edu.uw.tcss.view.app.assets.AssetsManager;

import edu.uw.tcss.view.util.GraphicsModifier;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.util.ArrayList;
import java.util.List;
import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class AdBannerPanel extends JPanel {

    private static final int BANNER_WIDTH = 500;
    private static final int BANNER_HEIGHT = 100;

    private final List<BufferedImage> myAdvertisements = new ArrayList<>();
    private final Timer myTimer = new Timer(5000, theEvent -> changeAdvertisement());
    private int myAdvertisementIndex;

    private BufferedImage myCurrentAdvertisement;

    /**
     * Creates a fixed-size banner panel that displays advertisements.
     *
     * @throws IOException should the image files contained not be found.
     */
    public AdBannerPanel() throws IOException {
        super();
        loadAdvertisements();
        setPreferredSize(new Dimension(BANNER_WIDTH, BANNER_HEIGHT));
        setBackground(Color.MAGENTA);
        setOpaque(true);

        myTimer.start();

        myCurrentAdvertisement = myAdvertisements.getFirst();

        repaint();
    }

    private void loadAdvertisements() throws IOException {
        myAdvertisements.add(ImageIO.read(AssetsManager.getFile(IMAGES_PATH, "adBanner.jpg")));
        myAdvertisements.add(ImageIO.read(AssetsManager.getFile(IMAGES_PATH, "SampleAdvertisement.png")));
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
        GraphicsModifier.enableAntiAliasing(g2d);

        g2d.drawImage(myCurrentAdvertisement, 0, 0, getWidth(), getHeight(), this);
    }
}