package edu.uw.tcss.app.assets;

import java.io.File;

/**
 * Class that is intended to solely hold constants for the working directory and be able to
 * work with game assets.
 *
 * @author Roman Bureacaov
 * @version 2025-03-11
 */
public final class AssetsManager {
    /** the URL to the folder with the assets. */
    public static final String PATH;
    /** the URL to the subfolder with the sounds. */
    public static final String SOUNDS_PATH;
    /** the URL to the subfolder with the music. */
    public static final String MUSIC_PATH;
    /** the URL to the subfolder with the sound effects. */
    public static final String SFX_PATH;
    /** the URL to the subfolder with the images. */
    public static final String IMAGES_PATH;

    private static final CharSequence SYSTEM_SEPARATOR = File.separator;

    static {
        PATH = String.join(SYSTEM_SEPARATOR,
                "TCSS305-Group-Project-main", "src", "edu", "uw", "tcss", "app", "assets");

        SOUNDS_PATH = String.join(SYSTEM_SEPARATOR, PATH, "sounds");
        MUSIC_PATH = String.join(SYSTEM_SEPARATOR, SOUNDS_PATH, "music");
        SFX_PATH = String.join(SYSTEM_SEPARATOR, SOUNDS_PATH, "sfx");

        IMAGES_PATH = String.join(SYSTEM_SEPARATOR, PATH, "images");
    }

    private AssetsManager() {

    }

    /**
     * Creates a file path to the target file in the directory specified.
     *
     * @param theDirectory the directory where the file may be located
     * @param theFileName the name of the file of interest
     * @return file object, or null if the file failed to construct
     */
    public static File getFile(final String theDirectory, final String theFileName) {
        return new File(String.join(SYSTEM_SEPARATOR, theDirectory, theFileName));
    }

    /**
     * Creates a file path string to the target file in the directory specified.
     *
     * @param theDirectory the directory where the file may be located
     * @param theFileName the name of the file of interest
     * @return file path
     */
    public static String getFilePath(final String theDirectory, final String theFileName) {
        return String.join(SYSTEM_SEPARATOR, theDirectory, theFileName);
    }

}
