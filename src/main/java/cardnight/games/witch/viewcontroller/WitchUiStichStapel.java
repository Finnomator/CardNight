package cardnight.games.witch.viewcontroller;

import cardnight.games.witch.Witch;
import cardnight.games.witch.WitchKarte;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;

import java.io.IOException;

public class WitchUiStichStapel {
    public StackPane root;
    private Witch spiel;

    public void uiErstellen(Witch spiel) {
        this.spiel = spiel;
    }

    public void updateUi() {
        root.getChildren().clear();

        WitchKarte[] gibStich = spiel.gibStich();
        for (int i = 0; i < gibStich.length; i++) {
            WitchKarte karte = gibStich[i];

            if (karte == null)
                continue;

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/cardnight/game-views/witch/witch-karte.fxml"));
            Button karteBtn;

            try {
                karteBtn = loader.load();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            StackPane.setMargin(karteBtn, new Insets(0, 0, i * 30, 0));
            karteBtn.setDisable(true);
            karteBtn.setPrefWidth(100);

            WitchUiKarte uiKarte = loader.getController();
            uiKarte.uiErstellen(karte);

            root.getChildren().add(karteBtn);
        }
    }
}
