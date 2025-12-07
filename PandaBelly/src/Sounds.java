import javax.sound.sampled.*;
import java.io.File;

//ryan - simple sound player for button clicks and notifications

public class Sounds {
    
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
