package cardnight.games.tictactoe.viewcontroller;

import cardnight.Main;
import cardnight.SoundPlayer;

import javax.sound.sampled.*;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.util.Random;

public class TTTSoundPlayer {

    private static Clip[] ueberlegenSounds;
    private static Clip[] feldAusgesuchtSounds;
    private static Clip hatGewonnenSound;
    private static Clip hatVerlorenSound;
    private static boolean soundsWurdenGeladen;

    public static void ladeSounds() {

        if (soundsWurdenGeladen)
            return;

        soundsWurdenGeladen = true;

        hatGewonnenSound = ladeClip("TicTacToe_Gewinner_Sound.wav");
        hatVerlorenSound = ladeClip("TicTacToe_Verlieren.wav");

        ueberlegenSounds = new Clip[3];
        feldAusgesuchtSounds = new Clip[3];
        for (int i = 0; i < 3; ++i) {
            ueberlegenSounds[i] = ladeClip("TicTacToe_Uberlegen_" + (i + 1) + ".wav");
            feldAusgesuchtSounds[i] = ladeClip("TicTacToe_Ausgesucht_" + (i + 1) + ".wav");
        }
    }

    private static Clip ladeClip(String pfad) {
        try {
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new BufferedInputStream(
                    Main.class.getResourceAsStream("/cardnight/game-views/tictactoe/sounds/" + pfad)));
            Clip clip = (Clip) AudioSystem.getLine(new DataLine.Info(Clip.class, audioInputStream.getFormat()));
            clip.open(audioInputStream);
            return clip;
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            throw new RuntimeException(e);
        }
    }

    public static void randomFeldAusgesucht() {
        SoundPlayer.playSoundAsync(feldAusgesuchtSounds[new Random().nextInt(3)]);
    }

    public static void gewonnen() {
        SoundPlayer.playSoundAsync(hatGewonnenSound);
    }

    public static void verloren() {
        SoundPlayer.playSoundAsync(hatVerlorenSound);
    }

    public static void randomUeberlegen() {
        SoundPlayer.playSoundAsync(ueberlegenSounds[new Random().nextInt(3)]);
    }
}
