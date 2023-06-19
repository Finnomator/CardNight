package cardnight;

import cardnight.games.Spiel;
import javafx.application.Application;
import javafx.stage.Stage;

import javax.sound.sampled.FloatControl;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.IOException;

public class Main {

    public static Stage mainStage;
    private static double musicVolume = 0.5;
    public static final String GitHubRepo = "https://github.com/Finnomator/CardNight";
    public static final String BugReportUrl = "https://github.com/Finnomator/CardNight/issues/new";
    public static final double HANDKARTE_BREITE = 113.6;
    public static final double GEGNERKARTE_HOEHE = 32.0;
    private static Spiel spielDasGespieltWird;
    private static FloatControl volumeControl;
    public static boolean enableSound = true;
    public static boolean debugMode;

    public static void main(String[] args) throws LineUnavailableException, IOException, UnsupportedAudioFileException {

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

    public static double getMusicVolume() {
        return musicVolume;
    }

    public static void setMusicVolume(double musicVolume) {
        Main.musicVolume = musicVolume;
        // throw new UnsupportedOperationException();
        /*
        TODO: Das wird benutzt, sobald wir Hintergrundmusik haben
        float range = volumeControl.getMaximum() - volumeControl.getMinimum();
        float gain = (float) (range * soundVolume + volumeControl.getMinimum());
        volumeControl.setValue(gain);

        Logger.log("Music volume now at " + (int) (musicVolume * 100) + "%");
         */
    }
}
