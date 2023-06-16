package cardnight.games.tictactoe.viewcontroller;

import cardnight.Main;
import cardnight.SoundPlayer;
import cardnight.Tools;

import javax.sound.sampled.*;
import java.io.BufferedInputStream;
import java.io.IOException;

public class TTTSoundPlayer {

    private static final Clip[] ueberlegenSounds = {ladeClip("TicTacToe_Uberlegen_1.wav"), ladeClip("TicTacToe_Uberlegen_2.wav"), ladeClip("TicTacToe_Uberlegen_3.wav")};
    private static final Clip[] feldAusgesuchtSounds = {ladeClip("TicTacToe_Ausgesucht_1.wav"), ladeClip("TicTacToe_Ausgesucht_2.wav"), ladeClip("TicTacToe_Ausgesucht_3.wav")};
    private static final Clip hatGewonnenSound = ladeClip("TicTacToe_Gewinner_Sound.wav");
    private static final Clip hatVerlorenSound = ladeClip("TicTacToe_Verlieren.wav");
    private static final Clip wellPlayedSound = ladeClip("TicTacToe_Well_played.wav");
    private static final Clip startClassicSound = ladeClip("TicTacToe_Classic.wav");
    private static final Clip startSupremacySound = ladeClip("TicTacToe_Supremacy.wav");

    private static Clip ladeClip(String pfad) {
        return SoundPlayer.ladeSound("game-views/tictactoe/sounds/" + pfad);
    }

    public static void randomFeldAusgesucht() {
        SoundPlayer.playSound(feldAusgesuchtSounds[Tools.random.nextInt(feldAusgesuchtSounds.length)]);
    }

    public static void gewonnen() {
        SoundPlayer.playSound(hatGewonnenSound);
    }

    public static void verloren() {
        if (Tools.random.nextInt(2) == 0)
            SoundPlayer.playSound(hatVerlorenSound);
        else
            SoundPlayer.playSound(wellPlayedSound);
    }

    public static void start() {
        if (Tools.random.nextInt(2) == 0)
            SoundPlayer.playSound(startClassicSound);
        else
            SoundPlayer.playSound(startSupremacySound);
    }

    public static void randomUeberlegen() {
        SoundPlayer.playSound(ueberlegenSounds[Tools.random.nextInt(ueberlegenSounds.length)]);
    }
}
