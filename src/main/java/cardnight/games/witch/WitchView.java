package cardnight.games.witch;

import cardnight.GameOver;
import cardnight.PauseMenu;
import cardnight.games.SpielView;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;

import java.io.IOException;

public class WitchView extends SpielView {

    public StackPane root;

    public void initialize() {
        System.out.println("Witch initiiert");
        // Von hier aus wird die ganze Logik gestartet
        Witch spiel = new Witch(4);
        spiel.game();
    }

    @Override
    protected void beendeSpiel() {
        try {
            root.getChildren().add(GameOver.loadScene());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void pauseClick() throws IOException {
        root.getChildren().add(PauseMenu.loadScene());
    }

    public static Pane loadScene() throws IOException {
        return new FXMLLoader(cardnight.games.witch.WitchView.class.getResource("/cardnight/game-views/witch-view.fxml")).load();
    }
}

