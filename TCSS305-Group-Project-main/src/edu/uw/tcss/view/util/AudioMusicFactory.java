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
     * Method to fetch the list of recognized background music
     * 
     * @return list of recognized background music records
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

    public static BackgroundMusic getMusicKalimba() {
        return new BackgroundMusic("Kalimba", "Kalimba.wav");
    }

    public static BackgroundMusic getMusicRetro() {
        return new BackgroundMusic("Retro Tetris", "retroTetris.wav");
    }

    public static BackgroundMusic getMusicEpic() {
        return new BackgroundMusic("Epic Tetris", "Korobeiniki.wav");
    }

    public static BackgroundMusic getMusicTrap() {
        return new BackgroundMusic("Trap Tetris", "TrapTetris.wav");
    }

    public static BackgroundMusic getMusicAlt() {
        return new BackgroundMusic("Alternative Tetris", "TheSamovars.wav");
    }

    /**
     * Record for information relating to the music
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
