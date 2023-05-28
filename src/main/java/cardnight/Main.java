package cardnight;

import cardnight.games.Spiel;
import javafx.application.Application;
import javafx.stage.Stage;

public class Main {

    public static Stage mainStage;
    public static double SOUND_VOLUME = 50.0;
    public static final String GitHubRepo = "https://github.com/Finnomator/CardNight";
    public static final String BugReportUrl = "https://github.com/Finnomator/CardNight/issues/new";
    private static Spiel spielDasGespieltWird;

    public static void main(String[] args) {
        Application.launch(MainController.class);
    }

    public static void setzeAktuellesSpiel(Spiel spiel) {
        spielDasGespieltWird = spiel;
    }

    public static Spiel gibAktuellGespieltesSpiel() {
        return spielDasGespieltWird;
    }
}
