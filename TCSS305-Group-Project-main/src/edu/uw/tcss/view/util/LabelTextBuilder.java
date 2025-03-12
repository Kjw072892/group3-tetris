package edu.uw.tcss.view.util;

import java.awt.Font;
import javax.swing.JLabel;

// TODO: maybe remove this since we don't need it anymore? - Roman Bureacov

/**
 * Utilities class that concatenates JLabels.
 * @author Kassie Whitney
 * @version 2.28.25
 * @deprecated
 */
public final class LabelTextBuilder {

    private LabelTextBuilder() {
    }

    /**
     * Concatenates any number of JLabel object texts into a single string while maintaining
     * the individual JLabel text format.
     * @param theLabel takes in any number of JLabel objects separated by a ','.
     * @return String of the concatenated label's text.
     */
    public static String htmlLabelCreator(final JLabel ... theLabel) {
        final JLabel[] labelsArray = theLabel.clone();
        String fontStyle;
        final StringBuilder formattedString = new StringBuilder("<html>");
        // TODO: I think the html tag is unnecessary in JLabels(?) - RB

        for (JLabel labels : labelsArray) {
            switch (labels.getFont().getStyle()) {
                case Font.PLAIN -> fontStyle = "normal";
                case Font.BOLD -> fontStyle = "bold";
                case Font.ITALIC -> fontStyle = "italic";
                default -> fontStyle = "-1"; // TODO: why not default to normal? - RB
            }

            formattedString.append("<span style='font-family:")
                    .append(labels.getFont().getFamily())
                    .append("; font-size:")
                    .append(labels.getFont().getSize())
                    .append("pt; font-weight:").append(fontStyle)
                    .append(";'>").append(labels.getText())
                    .append("</span> ");
        }
        formattedString.append("</html>");

        return formattedString.toString();
    }
}
