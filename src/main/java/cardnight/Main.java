package cardnight;

import cardnight.games.Spiel;
import javafx.application.Application;
import javafx.stage.Stage;

import javax.sound.sampled.*;
import java.io.IOException;

public class Main {

    public static Stage mainStage;
    private static double soundVolume = 0.5;
    public static final String GitHubRepo = "https://github.com/Finnomator/CardNight";
    public static final String BugReportUrl = "https://github.com/Finnomator/CardNight/issues/new";
    public static final double HANDKARTE_BREITE = 113.6;
    public static final double GEGNERKARTE_HOEHE = 32.0;
    private static Spiel spielDasGespieltWird;
    private static FloatControl volumeControl;

    public static void main(String[] args) throws LineUnavailableException, IOException, UnsupportedAudioFileException {

        SoundPlayer.ladeSounds();

        /*
        TODO: Das wird benutzt, sobald wir Hintergrundmusik haben
        AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(Main.class.getResourceAsStream("/cardnight/.../file.wav"));
        Clip clip = (Clip) AudioSystem.getLine(new DataLine.Info(Clip.class, audioInputStream.getFormat()));
        clip.open(audioInputStream);
        volumeControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
        clip.start();

        setSoundVolume(soundVolume);
         */

        Application.launch(MainController.class);
    }

    public static void setzeAktuellesSpiel(Spiel spiel) {
        spielDasGespieltWird = spiel;
    }

    public static Spiel gibAktuellGespieltesSpiel() {
        return spielDasGespieltWird;
    }

    public static double getSoundVolume() {
        return soundVolume;
    }

    public static void setSoundVolume(double soundVolume) {
        throw new UnsupportedOperationException();
        /*
        TODO: Das wird benutzt, sobald wir Hintergrundmusik haben
        Main.soundVolume = soundVolume;
        float range = volumeControl.getMaximum() - volumeControl.getMinimum();
        float gain = (float) (range * soundVolume + volumeControl.getMinimum());
        volumeControl.setValue(gain);

        System.out.println("Sound: " + soundVolume);
         */
    }
}
