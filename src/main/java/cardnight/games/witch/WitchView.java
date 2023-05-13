package cardnight.games.witch;

import cardnight.ScreenController;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.Pane;

import java.io.IOException;

public class WitchView {

    public void initialize() {
        System.out.println("Witch initiiert");
        // Von hier aus wird die ganze Logik gestartet
    }

    public static Pane loadScene() throws IOException {
        return new FXMLLoader(cardnight.games.witch.WitchView.class.getResource("/cardnight/game-views/witch-view.fxml")).load();
    }

    public void onZurueckClick() {
        ScreenController.activate("main-menu-view");
    }
}

