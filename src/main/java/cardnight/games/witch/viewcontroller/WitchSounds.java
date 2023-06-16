package cardnight.games.witch.viewcontroller;

import cardnight.SoundPlayer;

import javax.sound.sampled.Clip;

public class WitchSounds {

    private static final Clip youWinSound = ladeSound("Witch_You_win.wav");
    private static final Clip rundeVorbeiSound = ladeSound("Witch_The_round_is_over.wav");

    protected static Clip ladeSound(String pfad) {
        return SoundPlayer.ladeSound("game-views/witch/sounds/" + pfad);
    }

    public static void youWin() {
        SoundPlayer.playSound(youWinSound);
    }

    public static void rundeVorbei() {
        SoundPlayer.playSound(rundeVorbeiSound);
    }
}
