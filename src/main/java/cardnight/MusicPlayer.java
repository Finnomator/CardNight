package cardnight;

import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.LineEvent;

public class MusicPlayer {

    private static double musicVolume = 0.5;
    private static Clip playingMusicClip;

    public static void spielePlaylist() {
        spieleMusik(Tools.random.nextInt(13) + 1);
        playingMusicClip.addLineListener(e -> {
            if (e.getType() == LineEvent.Type.STOP)
                spielePlaylist();
        });
    }

    private static void spieleMusik(int nummer) {
        Clip sound = ladeMusik(nummer);
        playingMusicClip = sound;
        setMusicVolume(musicVolume);
        sound.start();
    }

    private static Clip ladeMusik(int nummer) {
        return SoundPlayer.ladeSound("music/Musik-" + (nummer < 10? "0" : "") + nummer + ".wav");
    }

    public static double getMusicVolume() {
        return musicVolume;
    }

    public static void setMusicVolume(double musicVolume) {
        setMusicVolume(playingMusicClip, musicVolume);
    }

    private static void setMusicVolume(Clip clip, double volume) {

        if (clip == null)
            return;

        musicVolume = volume;

        FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);

        float gainValue = (float) (Math.log10(musicVolume) * 20.0);

        if (gainValue < gainControl.getMinimum())
            gainValue = gainControl.getMinimum();
        else if (gainValue > gainControl.getMaximum())
            gainValue = gainControl.getMaximum();

        gainControl.setValue(gainValue);
    }
}
