package edu.uw.tcss.view.app.assets;

import java.io.InputStream;

/**
 * Beta version of the assetsManager to retrieve a resource path.
 * @author Kassie Whitney
 * @version 3.22.25
 */

public final class AssetsManagerBeta {
    
    private static final String BASE_PATH = "edu/uw/tcss/resources/assets/";

    private AssetsManagerBeta() { }

    /**
     * Gets the image file from resources.
     * @param theFileName the name of the image file.
     * @return the path of the file name via input stream.
     */
    public static InputStream getImage(final String theFileName) {
        return getResourceAsStream(BASE_PATH + "images/" + theFileName);
    }

    /**
     * Gets the sound bits from the resource file.
     * @param theFileName the name of the sound bit file
     * @return the path of the file name via input stream.
     */
    public static InputStream getSound(final String theSoundType, final String theFileName) {
        final String musicType = switch (theSoundType) {
            case "music" -> "music/";
            case "sfx" -> "sfx/";
            default -> "";
        };
        return AssetsManagerBeta.class.getClassLoader().getResourceAsStream(BASE_PATH + "sounds"
                + "/" + musicType + theFileName);
    }

    private static InputStream getResourceAsStream(final String theFullPath) {
        return AssetsManagerBeta.class.getClassLoader().getResourceAsStream(theFullPath);
    }

   
    
}
