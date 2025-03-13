package edu.uw.tcss.view.app;

import edu.uw.tcss.view.app.assets.AssetsManager;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JPanel;


/**
 * @author Kassie Whitney
 * @version 3.12.25
 */
public class AdBannerPanel extends JPanel {

    private static final int WIDTH_PADDING = 35;

    private static final int WIDTH_SECOND_PADDING = 150;

    private static final int HEIGHT = 100;

    private BufferedImage myImage;

    /**
     * Constructor for AdBannerPanel.
     */
    public AdBannerPanel() {
        super();
        setPreferredSize(new Dimension(0, HEIGHT));


        try {
            myImage =
                    ImageIO.read(new File(AssetsManager.getFilePath(AssetsManager.IMAGES_PATH,
                            "adBanner.jpg.gif")));
        } catch (final IOException exception) {
            //            exception.printStackTrace(); // Handle errors gracefully
        }
    }



    @Override
    protected void paintComponent(final Graphics theGraphics) {
        super.paintComponent(theGraphics);
        if (myImage != null) {
            theGraphics.drawImage(myImage, getWidth() - myImage.getWidth() + WIDTH_PADDING, 0,
                    getWidth() - WIDTH_SECOND_PADDING,
                    getHeight(),
                    this);
        } else {
            theGraphics.setColor(Color.MAGENTA); // Fallback if image doesn't load
            theGraphics.fillRect(0, 0, getWidth(), getHeight());
        }
    }
}