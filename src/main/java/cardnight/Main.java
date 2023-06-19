package cardnight;

import cardnight.games.Spiel;
import javafx.application.Application;
import javafx.stage.Stage;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.IOException;

public class Main {

    public static Stage mainStage;
    public static final String GitHubRepo = "https://github.com/Finnomator/CardNight";
    public static final String BugReportUrl = "https://github.com/Finnomator/CardNight/issues/new";
    public static final double HANDKARTE_BREITE = 113.6;
    public static final double GEGNERKARTE_HOEHE = 32.0;
    private static Spiel spielDasGespieltWird;
    public static boolean debugMode;

    public static void main(String[] args) throws LineUnavailableException, IOException, UnsupportedAudioFileException {
        Application.launch(MainController.class);
    }

    public static void setzeAktuellesSpiel(Spiel spiel) {
        spielDasGespieltWird = spiel;
    }

    public static Spiel gibAktuellGespieltesSpiel() {
        return spielDasGespieltWird;
    }
}
