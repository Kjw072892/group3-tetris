package edu.uw.tcss.view.app;

import edu.uw.tcss.view.app.assets.AssetsManager;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class AdBannerPanel extends JPanel {
    private BufferedImage myImage;

    public AdBannerPanel() throws IOException {
        super();
        setPreferredSize(new Dimension(0, 100));

        try {
            myImage =
                    ImageIO.read(new File(AssetsManager.getFilePath(AssetsManager.IMAGES_PATH,
                            "adBanner.jpg")));
        } catch (final IOException exception) {
//            exception.printStackTrace(); // Handle errors gracefully
        }
    }

    @Override
    protected void paintComponent(final Graphics theGraphics) {
        super.paintComponent(theGraphics);
        if (myImage != null) {
            theGraphics.drawImage(myImage, 0, 0, getWidth(), getHeight(), this);
        } else {
            theGraphics.setColor(Color.MAGENTA); // Fallback if image doesn't load
            theGraphics.fillRect(0, 0, getWidth(), getHeight());
        }
    }
}