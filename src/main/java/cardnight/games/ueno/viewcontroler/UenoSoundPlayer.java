package cardnight.games.ueno.viewcontroler;

import cardnight.Main;
import cardnight.SoundPlayer;
import cardnight.games.ueno.UenoFarbe;

import javax.sound.sampled.*;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.util.EnumMap;

public class UenoSoundPlayer {
    private static Clip fYouSound;
    private static Clip reverseSound;
    private static Clip skipSound;
    private static Clip unoSound;
    private static Clip unoUnoSound;
    private static Clip duHastGewonnenSound;
    private static Clip rundenStartSound;
    private static Clip vierZiehenSound;
    private static Clip zweiZiehenSound;
    private static Clip rundeVorbeiSound;
    private static EnumMap<UenoFarbe, Clip> farbwahlSounds;

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
        duHastGewonnenSound = ladeClip("UNO_You_win.wav");
        rundenStartSound = ladeClip("UNO_Start_Ansager.wav");
        vierZiehenSound = ladeClip("UNO_Draw_4.wav");
        zweiZiehenSound = ladeClip("UNO_Draw_2.wav");
        rundeVorbeiSound = ladeClip("UNO_The_round_is_over.wav");

        farbwahlSounds = new EnumMap<>(UenoFarbe.class);
        farbwahlSounds.put(UenoFarbe.ROT, ladeClip("UNO_rot.wav"));
        farbwahlSounds.put(UenoFarbe.GRUEN, ladeClip("UNO_grun.wav"));
        farbwahlSounds.put(UenoFarbe.BLAU, ladeClip("UNO_blau.wav"));
        farbwahlSounds.put(UenoFarbe.GELB, ladeClip("UNO_gelb.wav"));
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
        SoundPlayer.playSound(fYouSound);
    }

    public static void reverse() {
        SoundPlayer.playSound(reverseSound);
    }

    public static void skip() {
        SoundPlayer.playSound(skipSound);
    }

    public static void uno() {
        SoundPlayer.playSound(unoSound);
    }

    public static void unoUno() {
        SoundPlayer.playSound(unoUnoSound);
    }

    public static void duHastGewonnen() {
        SoundPlayer.playSound(duHastGewonnenSound);
    }

    public static void start(boolean async) {
        if (async)
            SoundPlayer.playSound(rundenStartSound);
        else
            SoundPlayer.playSound(rundenStartSound);
    }

    public static void vierZiehen() {
        SoundPlayer.playSound(vierZiehenSound);
    }

    public static void zweiZiehen() {
        SoundPlayer.playSound(zweiZiehenSound);
    }

    public static void rundeVorbei() {
        SoundPlayer.playSound(rundeVorbeiSound);
    }

    public static void farbwahl(UenoFarbe farbe) {
        SoundPlayer.playSound(farbwahlSounds.get(farbe));
    }
}
