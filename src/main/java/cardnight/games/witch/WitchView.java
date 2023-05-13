package cardnight.games.witch;

import cardnight.ScreenController;
import cardnight.games.ueno.Ueno;
import cardnight.games.ueno.viewcontroler.UenoGegnerUiHand;
import cardnight.games.ueno.viewcontroler.UenoHauptspielerUiHand;
import cardnight.games.ueno.viewcontroler.UenoKarteKlickEvent;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;

import java.io.IOException;
import java.util.HashMap;

public class WitchView {
    public void onBackClick() {
        ScreenController.activate("main-menu-view");
    }

    public static Pane loadScene() throws IOException {
        return new FXMLLoader(cardnight.games.witch.WitchView.class.getResource("/cardnight/game-views/witch-view.fxml")).load();
    }
}

