package edu.uw.tcss.view.app;

import java.awt.Color;
import java.awt.Dimension;
import javax.swing.JPanel;

public class AdBannerPanel extends JPanel {

    public AdBannerPanel() {
        super();

        setPreferredSize(new Dimension(0, 100));
        setBackground(Color.MAGENTA);
        setOpaque(true);
    }
}
