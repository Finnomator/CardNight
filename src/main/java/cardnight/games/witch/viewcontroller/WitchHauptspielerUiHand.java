package cardnight.games.witch.viewcontroller;

import cardnight.games.witch.WitchKarte;
import cardnight.games.witch.WitchMensch;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.layout.HBox;

import java.io.IOException;
import java.util.ArrayList;

public class WitchHauptspielerUiHand {
    public HBox root;
    private WitchMensch spieler;

    public void uiErstellen(WitchMensch spieler) {
        this.spieler = spieler;
        updateUi();
    }

    public void updateUi() {
        ArrayList<WitchKarte> handKarten = spieler.gibHandkarten();
        ArrayList<WitchKarte> spielbareKarten = spieler.spielbareKarten();

        root.getChildren().clear();

        for (WitchKarte karte : handKarten) {
            FXMLLoader kartenLoader = new FXMLLoader(getClass().getResource("/cardnight/game-views/witch/witch-karte.fxml"));

            Node kartenNode;
            try {
                kartenNode = kartenLoader.load();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            WitchUiKarte uiKarte = kartenLoader.getController();
            uiKarte.uiErstellen(karte);

            kartenNode.setDisable(!spielbareKarten.contains(karte));

            root.getChildren().add(kartenNode);
        }
    }
}
