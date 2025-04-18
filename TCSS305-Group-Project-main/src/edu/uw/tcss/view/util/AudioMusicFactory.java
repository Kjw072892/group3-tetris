package edu.uw.tcss.view.util;

/**
 * Factory class for creating the proper music records.
 * 
 * @author Roman Bureacov
 * @version 2025-03-11
 */
public final class AudioMusicFactory {
    
    private AudioMusicFactory() {
        
    }

    /**
     * Method to fetch the list of recognized background music.
     * 
     * @return array of recognized background music records
     */
    public static BackgroundMusic[] getBackgroundMusic() {
        return new BackgroundMusic[] {
                getMusicKalimba(),
                getMusicEpic(),
                getMusicRetro(),
                getMusicTrap(),
                getMusicAlt(),
                getMusicPink(),
                getMusicCute(),
                getMusicPanic(),
                getMusicEurobeat()
        };
    }

    /** The Kalimba music, default music that came with Windows 7. */
    public static BackgroundMusic getMusicKalimba() {
        return new BackgroundMusic("Kalimba", "Kalimba.wav");
    }

    /** Retro music, Game Boy version of the tetris theme. */
    public static BackgroundMusic getMusicRetro() {
        return new BackgroundMusic("Retro Tetris", "retroTetris.wav");
    }

    /** Epic music, specifically the original music the tetris theme is based on,
     * sung by the Red Army Choir.
     */
    public static BackgroundMusic getMusicEpic() {
        return new BackgroundMusic("Epic Tetris", "Korobeiniki.wav");
    }

    /** The trap version of the tetris theme. */
    public static BackgroundMusic getMusicTrap() {
        return new BackgroundMusic("Trap Tetris", "TrapTetris.wav");
    }

    /** the "alternative" tetris theme.
     * In reality, it's just "The Samovars" sung by the Red Army Choir,
     * completely unrelated to tetris.
     */
    public static BackgroundMusic getMusicAlt() {
        return new BackgroundMusic("Alternative Tetris", "TheSamovars.wav");
    }

    /** the music to use when panicking. */
    public static BackgroundMusic getMusicPanic() {
        return new BackgroundMusic("Panic Tetris", "FatalisTheme.wav");
    }

    /** The cute song, plays when the color scheme is set to the pretty scheme. */
    public static BackgroundMusic getMusicCute() {
        return new BackgroundMusic("Pastel Mode", "cuteSong.wav");
    }

    /** The pink theme, plays when the color scheme is set to the pink theme. */
    public static BackgroundMusic getMusicPink() {
        return new BackgroundMusic("Pink Mode \uD83C\uDF80✨", "pinkTheme.wav");
    }

    /** some epic eurobeat music to play. */
    public static BackgroundMusic getMusicEurobeat() {
        return new BackgroundMusic("Eurobeat Tetris",
                "Everybody's Warming (Extended Mix).wav");
    }

    /**
     * Record for information relating to the music.
     * 
     * @param name the formal, readable name of the music
     * @param fileName the file name of the music
     */
    public record BackgroundMusic(String name, String fileName) {

        @Override
        public String toString() {
            return name;
        }
    }
}
