package cardnight.games.ueno.viewcontroler;

import cardnight.games.ueno.UenoKarte;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.HBox;

import java.io.IOException;

public class UenoGegnerUiHand extends UenoUiHand {
    public HBox kartenBox;

    @Override
    public void updateUi() {
        kartenBox.getChildren().clear();
        for (UenoKarte ignored : spieler.gibHandkarten()) {
            FXMLLoader loader;
            loader = new FXMLLoader(getClass().getResource("/cardnight/game-views/ueno/karten-rueckseite.fxml"));
            try {
                kartenBox.getChildren().add(loader.load());
            } catch (IOException ex) {
                throw new RuntimeException();
            }
        }
    }
}
