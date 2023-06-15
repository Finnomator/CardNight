package cardnight.games.witch.viewcontroller;

import cardnight.games.witch.WitchKarte;
import cardnight.games.witch.WitchMensch;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;

import java.util.ArrayList;

public class WitchHauptspielerUiHand extends HBox {
    private final WitchMensch spieler;

    public WitchHauptspielerUiHand(WitchMensch spieler) {
        this.spieler = spieler;
        setMaxSize(Double.NEGATIVE_INFINITY, Double.NEGATIVE_INFINITY);
        updateUi(true);
    }

    public void updateUi(boolean disableKarten) {
        ArrayList<WitchKarte> handKarten = spieler.gibHandkarten();
        ArrayList<WitchKarte> spielbareKarten = spieler.spielbareKarten();

        getChildren().clear();

        for (int i = 0, handKartenSize = handKarten.size(); i < handKartenSize; i++) { // Ha, try beating this ConcurrentModificationException!
            WitchKarte karte = handKarten.get(i);

            Button kartenNode = WitchRessourcen.erstelleStandardHandKarte(karte);
            kartenNode.setDisable(disableKarten || !spielbareKarten.contains(karte));

            getChildren().add(kartenNode);
        }
    }

    public void disableAllCards() {
        for (Node child : getChildren())
            child.setDisable(true);
    }
}
