package cardnight.games.witch.viewcontroller;

import cardnight.games.witch.WitchKarte;
import javafx.scene.control.Button;

public class WitchUiKarte {
    public Button root;

    public void uiErstellen(WitchKarte karte) {
        switch (karte.farbe){
            case ROT:
                root.setStyle("-fx-background-color: red");
                break;
            case GRUEN:
                root.setStyle("-fx-background-color: green");
                break;
            case BLAU:
                root.setStyle("-fx-background-color: blue");
                break;
            case GELB:
                root.setStyle("-fx-background-color: yellow");
                break;
        }

        root.setText(String.valueOf(karte.wert));
    }
}
