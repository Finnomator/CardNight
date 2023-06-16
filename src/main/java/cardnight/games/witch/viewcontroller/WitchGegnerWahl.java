package cardnight.games.witch.viewcontroller;

import cardnight.Main;
import cardnight.ScreenController;
import cardnight.SoundPlayer;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.StackPane;

import java.io.IOException;

public class WitchGegnerWahl {
    public void zuruckZumHauptmenuKlick() {
        SoundPlayer.klickSound();
        ScreenController.show(ScreenController.hautptemue);
    }

    public void zweiKlick() throws IOException {
        WitchView.anzahlGegner = 2;
        startKlick();
    }

    public void dreiKlick() throws IOException {
        WitchView.anzahlGegner = 3;
        startKlick();
    }

    public void vierKlick() throws IOException {
        WitchView.anzahlGegner = 4;
        startKlick();
    }

    public void fuenfKlick() throws IOException {
        WitchView.anzahlGegner = 5;
        startKlick();
    }

    private void startKlick() throws IOException {
        SoundPlayer.klickSound();
        WitchView.showScene();
    }

    public static StackPane loadScene() {
        try {
            return new FXMLLoader(Main.class.getResource("/cardnight/game-views/witch/gegner-wahl.fxml")).load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
