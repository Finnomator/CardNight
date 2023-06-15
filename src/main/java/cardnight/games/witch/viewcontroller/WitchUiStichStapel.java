package cardnight.games.witch.viewcontroller;

import cardnight.games.witch.Witch;
import cardnight.games.witch.WitchKarte;
import javafx.geometry.Insets;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;

public class WitchUiStichStapel extends StackPane {
    private final Witch spiel;

    public WitchUiStichStapel(Witch spiel) {
        this.spiel = spiel;
    }

    public void updateUi() {
        getChildren().clear();

        WitchKarte[] gibStich = spiel.gibStich();
        for (int i = 0; i < gibStich.length; i++) {
            WitchKarte karte = gibStich[i];

            if (karte == null)
                continue;

            ImageView imgView = WitchRessourcen.erstelleKartenImageView(karte);
            imgView.setPreserveRatio(true);
            imgView.setFitWidth(150);

            StackPane.setMargin(imgView, new Insets(0, -i * 50, 0, 0));

            getChildren().add(imgView);
        }
    }
}
