package cardnight.games.witch.viewcontroller;

import cardnight.games.witch.Witch;
import cardnight.games.witch.WitchKarte;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;

import java.io.IOException;

public class WitchUiStichStapel {
    public VBox root;
    private Witch spiel;

    public void uiErstellen(Witch spiel) {
        this.spiel = spiel;
    }

    public void updateUi() {
        root.getChildren().clear();

        for (WitchKarte karte : spiel.gibStich()) {

            if (karte == null)
                continue;

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/cardnight/game-views/witch/witch-karte.fxml"));
            Button karteBtn;
            try {
                karteBtn = loader.load();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            karteBtn.setDisable(true);
            karteBtn.setPrefWidth(100);
            karteBtn.toFront();

            WitchUiKarte uiKarte = loader.getController();
            uiKarte.uiErstellen(karte);

            VBox.setMargin(karteBtn, new Insets(-20, 0, 0, 0));
            root.getChildren().add(0, karteBtn);
        }
    }
}
