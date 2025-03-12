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
                getMusicAlt()
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

    /**
     * Record for information relating to the music.
     * 
     * @param name the formal name of the music
     * @param fileName the file name of the music
     */
    public record BackgroundMusic(String name, String fileName) {

        @Override
        public String toString() {
            return name;
        }
    }
}
