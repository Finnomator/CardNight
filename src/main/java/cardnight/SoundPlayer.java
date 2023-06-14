package cardnight;

import javax.sound.sampled.*;
import java.io.BufferedInputStream;
import java.io.IOException;

public class SoundPlayer {

    private static Clip klickSoundEffekt;

    public static void ladeSounds() {
        try {
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new BufferedInputStream(
                    Main.class.getResourceAsStream("/cardnight/sounds/Button_Sound.wav")));
            klickSoundEffekt = (Clip) AudioSystem.getLine(new DataLine.Info(Clip.class, audioInputStream.getFormat()));
            klickSoundEffekt.open(audioInputStream);
        } catch (UnsupportedAudioFileException | LineUnavailableException | IOException e) {
            throw new RuntimeException(e);
        }
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
