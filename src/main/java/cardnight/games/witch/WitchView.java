package cardnight.games.witch;

import cardnight.PauseMenu;
import cardnight.games.SpielView;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.io.IOException;

public class WitchView extends SpielView {

    public StackPane root;

    public void initialize() {
        System.out.println("Witch initiiert");
        // Von hier aus wird die ganze Logik gestartet
    }

    @Override
    protected void beendeSpiel() {
        throw new NotImplementedException();
    }

    @Override
    public void pauseClick() throws IOException {
        root.getChildren().add(PauseMenu.loadScene());
    }

    public static Pane loadScene() throws IOException {
        return new FXMLLoader(cardnight.games.witch.WitchView.class.getResource("/cardnight/game-views/witch-view.fxml")).load();
    }
}

