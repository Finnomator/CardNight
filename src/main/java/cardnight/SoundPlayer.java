package cardnight;

import javax.sound.sampled.*;
import java.io.BufferedInputStream;
import java.io.IOException;

public class SoundPlayer {

    private static final Clip klickSoundEffekt = ladeSound("sounds/Button_Sound.wav");
    private static final Clip soundEinstellenSound = ladeSound("sounds/Soundeinstellen_Sound.wav");

    public static double soundVolume = 0.5;

    public static Clip ladeSound(String pfad) {
        try {
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new BufferedInputStream(
                    Main.class.getResourceAsStream("/cardnight/" + pfad)));
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
        if (soundVolume <= 0)
            return;

        FloatControl gainControl = (FloatControl) sound.getControl(FloatControl.Type.MASTER_GAIN);
        gainControl.setValue((float) (Math.log10(soundVolume) * 20.0));

        sound.setFramePosition(0);
        sound.start();
    }
}
