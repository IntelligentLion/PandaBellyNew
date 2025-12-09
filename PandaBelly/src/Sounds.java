import javax.sound.sampled.*;
import java.io.File;

//ryan - simple sound player for button clicks and notifications

public class Sounds {
    
    private static Clip musicClip;
    
    // Play a sound file (put your .wav files in PandaBelly/src/)
    public static void play(String filename) {
        try {
            File soundFile = new File("PandaBelly/src/" + filename);
            AudioInputStream audioIn = AudioSystem.getAudioInputStream(soundFile);
            Clip clip = AudioSystem.getClip();
            clip.open(audioIn);
            clip.start();
        } catch (Exception e) {
            // try execept loop needed again to compile for some reason...
        }
    }
    
    // Play background music (loops)
    public static void playMusic(String filename) {
        stopMusic();
        try {
            File soundFile = new File("PandaBelly/src/" + filename);
            AudioInputStream audioIn = AudioSystem.getAudioInputStream(soundFile);
            musicClip = AudioSystem.getClip();
            musicClip.open(audioIn);
            musicClip.loop(Clip.LOOP_CONTINUOUSLY);
        } catch (Exception e) {
            // need this or wont compile
        }
    }
    
    // Stop background music
    public static void stopMusic() {
        if (musicClip != null && musicClip.isRunning()) {
            musicClip.stop();
            musicClip.close();
        }
    }
    
    // Shortcut methods for common sounds
    public static void playClick() {
        play("buttonclick.wav");
    }
    
    public static void playSuccess() {
        play("completed.wav");
    }
    
    public static void playError() {
        play("clickerror.wav");
    }
}
