package cardnight;

import javax.sound.sampled.*;
import java.io.BufferedInputStream;
import java.io.IOException;

public class SoundPlayer {

    private static Clip klickSoundEffekt;
    private static Clip soundEinstellenSound;

    public static void ladeSounds() {
        klickSoundEffekt = ladeSound("Button_Sound.wav");
        soundEinstellenSound = ladeSound("Soundeinstellen_Sound.wav");
    }

    private static Clip ladeSound(String pfad) {
        try {
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new BufferedInputStream(
                    Main.class.getResourceAsStream("/cardnight/sounds/" + pfad)));
            Clip clip = (Clip) AudioSystem.getLine(new DataLine.Info(Clip.class, audioInputStream.getFormat()));
            clip.open(audioInputStream);
            return clip;
        } catch (LineUnavailableException | IOException | UnsupportedAudioFileException e) {
            throw new RuntimeException(e);
        }
    }

    public static void soundEinstellen() {
        playSound(soundEinstellenSound);
    }

    public static void klickSound() {
        playSound(klickSoundEffekt);
    }

    public static void playSound(Clip sound) {
        if (!Main.enableSound)
            return;

        sound.setFramePosition(0);
        sound.start();
    }
}
