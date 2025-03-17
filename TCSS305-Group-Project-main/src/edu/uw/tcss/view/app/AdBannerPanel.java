package edu.uw.tcss.view.app;

import static edu.uw.tcss.view.app.assets.AssetsManager.IMAGES_PATH;

import edu.uw.tcss.view.app.assets.AssetsManager;
import edu.uw.tcss.view.util.ColorSchemeManager;
import edu.uw.tcss.view.util.GraphicsHandler;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.JPanel;
import javax.swing.Timer;

/**
 * Ad banner class.
 * @author Kassie
 * @author Roman
 * @version 3.14.25
 */
public class AdBannerPanel extends JPanel implements PropertyChangeListener {

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

        loadAdvertisements();
        setStyle();

        final Timer timer = new Timer(5000, theEvent -> changeAdvertisement());
        timer.start();


        if (!myAdvertisements.isEmpty()) {
            myCurrentAdvertisement = myAdvertisements.getFirst();
        }

        repaint();
    }

    private void setStyle() {
        setPreferredSize(new Dimension(BANNER_WIDTH, BANNER_HEIGHT));
        setBackground(Color.MAGENTA);
        setOpaque(true);
    }

    /**
     * loads advertisements.
     */
    public void loadAdvertisements() {
        myAdvertisements.clear();
        myAdvertisementIndex = 0;

        try {
            System.out.println("IMAGES_PATH: " + IMAGES_PATH);

            if (ColorSchemeManager.getCurrentColorScheme().name().contains("Pink Mode \uD83C\uDF80✨")) {
                loadImage("cute ad.png");
                loadImage("Cool_Girls_Code_Banner.png");
                loadImage("CoolGirlsCode.png");
            } else {
                loadImage("adBanner.jpg");
                loadImage("SampleAdvertisement.png");
                loadImage("adBanner2.jpeg");
                loadImage("adBanner3.jpg");
                loadImage("dairyQueenAd.png");
            }
        } catch (final Exception e) {
            System.out.println("❌ Image loading failed: " + e.getMessage());
        }

        if (!myAdvertisements.isEmpty()) {
            myCurrentAdvertisement = myAdvertisements.getFirst();
        } else {
            System.out.println("⚠ Warning: No advertisements loaded, using fallback.");
            myCurrentAdvertisement = null; // Optional: Set a default image here.
        }

        repaint();
    }

    private void loadImage(String fileName) {
        try {
            System.out.println("🔍 Attempting to load: " + IMAGES_PATH + fileName);
            InputStream stream = AssetsManager.getResourceAsStream(IMAGES_PATH, fileName);

            if (stream == null) {
                System.out.println("❌ Resource not found: " + IMAGES_PATH + fileName);
            } else {
                myAdvertisements.add(ImageIO.read(stream));
                System.out.println("✅ Loaded successfully: " + fileName);
            }
        } catch (IOException e) {
            System.out.println("❌ Error loading image: " + fileName + " -> " + e.getMessage());
        }
    }


    private void changeAdvertisement() {
        if (!myAdvertisements.isEmpty()) {
            myAdvertisementIndex = (myAdvertisementIndex + 1) % myAdvertisements.size();
            myCurrentAdvertisement = myAdvertisements.get(myAdvertisementIndex);
            repaint();
        }
    }

    @Override
    public void paintComponent(final Graphics theGraphics) {
        super.paintComponent(theGraphics);
        final Graphics2D g2d = (Graphics2D) theGraphics;
        GraphicsHandler.enableAntiAliasing(g2d);

        g2d.drawImage(myCurrentAdvertisement, 0, 0, getWidth(), getHeight(), this);
    }

    @Override
    public void propertyChange(final PropertyChangeEvent theEvent) {
        if (ColorSchemeManager.PROPERTY_COLOR_SCHEME.equals(theEvent.getPropertyName())) {
            loadAdvertisements();
        }
    }
}