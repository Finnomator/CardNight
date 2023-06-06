package cardnight.games.witch.viewcontroller;

import cardnight.games.witch.WitchKarte;
import cardnight.games.witch.WitchMensch;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;

import java.io.IOException;
import java.util.ArrayList;

public class WitchHauptspielerUiHand {
    public HBox root;
    private WitchMensch spieler;

    public void uiErstellen(WitchMensch spieler) {
        this.spieler = spieler;
        updateUi(true);
    }

    public void updateUi(boolean disableKarten) {
        ArrayList<WitchKarte> handKarten = spieler.gibHandkarten();
        ArrayList<WitchKarte> spielbareKarten = spieler.spielbareKarten();

        root.getChildren().clear();

        for (int i = 0, handKartenSize = handKarten.size(); i < handKartenSize; i++) { // Ha, try beating this ConcurrentModificationException!
            WitchKarte karte = handKarten.get(i);

            Button kartenNode = WitchRessourcen.erstelleStandardHandKarte(karte);
            kartenNode.setDisable(disableKarten || !spielbareKarten.contains(karte));

            root.getChildren().add(kartenNode);
        }
    }

    public void disableAllCards() {
        for (Node child : root.getChildren())
            child.setDisable(true);
    }
}
