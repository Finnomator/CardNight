package cardnight.games.ueno.viewcontroller;

import cardnight.Main;
import cardnight.MainMenuView;
import cardnight.ScreenController;
import cardnight.SoundPlayer;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Slider;
import javafx.scene.layout.StackPane;

import java.io.IOException;

public class UenoGegnerWahl {
    public Slider anzahlKartenSlider;

    public void initialize() {
        anzahlKartenSlider.valueProperty().addListener((obs, oldval, newVal) ->
                anzahlKartenSlider.setValue(newVal.intValue()));
    }

    public void einenKlick() throws IOException {
        UenoView.anzahlGegner = 1;
        startKlick();
    }

    public void zweiKlick() throws IOException {
        UenoView.anzahlGegner = 2;
        startKlick();
    }

    public void dreiKlick() throws IOException {
        UenoView.anzahlGegner = 3;
        startKlick();
    }

    public void zuruckZumHauptmenuKlick() {
        SoundPlayer.klickSound();
        MainMenuView.showScene();
    }

    private void startKlick() throws IOException {
        UenoView.kartenAnzahl = (int) anzahlKartenSlider.getValue();
        SoundPlayer.klickSound();
        UenoView.showScene();
    }

    public static StackPane loadScene() {
        try {
            return new FXMLLoader(Main.class.getResource("/cardnight/game-views/ueno/gegner-wahl.fxml")).load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
