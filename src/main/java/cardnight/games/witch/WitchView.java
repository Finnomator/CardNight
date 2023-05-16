package cardnight.games.witch;

import cardnight.PauseMenu;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;

import java.io.IOException;

public class WitchView {

    public StackPane root;

    public void initialize() {
        System.out.println("Witch initiiert");
        // Von hier aus wird die ganze Logik gestartet
        Witch game = new Witch(4);
        game.game();
    }

    public static Pane loadScene() throws IOException {
        return new FXMLLoader(cardnight.games.witch.WitchView.class.getResource("/cardnight/game-views/witch-view.fxml")).load();
    }

    public void onPauseClick() throws IOException {
        root.getChildren().add(PauseMenu.loadScene());
    }
}

