package cardnight.games.ueno.viewcontroler;

import cardnight.Main;
import cardnight.SoundPlayer;

import javax.sound.sampled.*;
import java.io.BufferedInputStream;
import java.io.IOException;

public class UenoSoundPlayer {
    private static Clip fYouSound;
    private static Clip reverseSound;
    private static Clip skipSound;
    private static Clip unoSound;
    private static Clip unoUnoSound;
    private static boolean soundsWurdenGeladen;


    public static void ladeSounds() {

        if (soundsWurdenGeladen)
            return;

        soundsWurdenGeladen = true;

        fYouSound = ladeClip("UNO_F.wav");
        reverseSound = ladeClip("UNO_Reverse.wav");
        skipSound = ladeClip("UNO_Skip.wav");
        unoSound = ladeClip("UNO_Sound.wav");
        unoUnoSound = ladeClip("UNO_UNO_Sound.wav");
    }

    private static Clip ladeClip(String pfad) {
        try {
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new BufferedInputStream(
                    Main.class.getResourceAsStream("/cardnight/game-views/ueno/sounds/" + pfad)));
            Clip clip = (Clip) AudioSystem.getLine(new DataLine.Info(Clip.class, audioInputStream.getFormat()));
            clip.open(audioInputStream);
            return clip;
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            throw new RuntimeException(e);
        }
    }

    public static void fYou() {
        SoundPlayer.playSoundAsync(fYouSound);
    }

    public static void reverse() {
        SoundPlayer.playSoundAsync(reverseSound);
    }

    public static void skip() {
        SoundPlayer.playSoundAsync(skipSound);
    }

    public static void uno() {
        SoundPlayer.playSoundAsync(unoSound);
    }

    public static void unoUno() {
        SoundPlayer.playSoundAsync(unoUnoSound);
    }
}
