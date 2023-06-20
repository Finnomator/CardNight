package cardnight.games.ueno.viewcontroller;

import cardnight.SoundPlayer;
import cardnight.games.ueno.UenoFarbe;

import javax.sound.sampled.Clip;
import java.util.EnumMap;

public class UenoSoundPlayer {
    private static final Clip fYouSound = ladeClip("UNO_F.wav");
    private static final Clip reverseSound = ladeClip("UNO_Reverse.wav");
    private static final Clip skipSound = ladeClip("UNO_Skip.wav");
    private static final Clip unoSound = ladeClip("UNO_Sound.wav");
    private static final Clip unoUnoSound = ladeClip("UNO_UNO_Sound.wav");
    private static final Clip duHastGewonnenSound = ladeClip("UNO_You_win.wav");
    private static final Clip rundenStartSound = ladeClip("UNO_Start_Ansager.wav");
    private static final Clip vierZiehenSound = ladeClip("UNO_Draw_4.wav");
    private static final Clip zweiZiehenSound = ladeClip("UNO_Draw_2.wav");
    private static final Clip rundeVorbeiSound = ladeClip("UNO_The_round_is_over.wav");
    private static final EnumMap<UenoFarbe, Clip> farbwahlSounds = getFarbwahlSounds();

    private static EnumMap<UenoFarbe, Clip> getFarbwahlSounds() {
        EnumMap<UenoFarbe, Clip> res = new EnumMap<>(UenoFarbe.class);
        res.put(UenoFarbe.ROT, ladeClip("UNO_rot.wav"));
        res.put(UenoFarbe.GRUEN, ladeClip("UNO_grun.wav"));
        res.put(UenoFarbe.BLAU, ladeClip("UNO_blau.wav"));
        res.put(UenoFarbe.GELB, ladeClip("UNO_gelb.wav"));
        return res;
    }

    private static Clip ladeClip(String pfad) {
        return SoundPlayer.ladeSound("game-views/ueno/sounds/" + pfad);
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

    public static void start() {
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
