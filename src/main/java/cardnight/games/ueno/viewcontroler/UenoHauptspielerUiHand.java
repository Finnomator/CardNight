package cardnight.games.ueno.viewcontroler;

import cardnight.games.ueno.UenoKarte;
import cardnight.games.ueno.UenoKartenArt;
import cardnight.games.ueno.UenoSpieler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.layout.HBox;

import java.io.IOException;
import java.util.ArrayList;

public class UenoHauptspielerUiHand extends UenoUiHand {

    private final HBox handkartenBox;

    public UenoHauptspielerUiHand(UenoSpieler spieler) {
        this.spieler = spieler;
        handkartenBox = new HBox();
        handkartenBox.setMaxSize(Double.NEGATIVE_INFINITY, Double.NEGATIVE_INFINITY);
        updateUi();
    }

    public HBox root() {
        return handkartenBox;
    }

    @Override
    public void updateUi() {
        handkartenBox.getChildren().clear();

        ArrayList<UenoKarte> ablegbareKarten = spieler.ablegbareKarten();

        ArrayList<UenoKarte> gibHandkarten = spieler.gibHandkarten();
        for (int i = 0, gibHandkartenSize = gibHandkarten.size(); i < gibHandkartenSize; i++) { // ConcurrentModificationException gibt's auch hier
            UenoKarte karte = gibHandkarten.get(i);

            Node uiKarte;

            if (karte.istSchwarz()) {

                FXMLLoader loader = new FXMLLoader(getClass().getResource("/cardnight/game-views/ueno/" + (karte.art == UenoKartenArt.FARBWAHL ? "farbwahl" : "vier-ziehen") + "-karte.fxml"));

                try {
                    uiKarte = loader.load();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }

                UenoFarbwahlUiKarte farbwahlUiKarte = loader.getController();
                farbwahlUiKarte.uiErstellen(karte, this);
            } else uiKarte = UenoRessourcen.erstelleStandardHandKarte(karte);

            uiKarte.setDisable(!ablegbareKarten.contains(karte) || !spieler.istAmZug());

            handkartenBox.getChildren().add(uiKarte);
        }
    }
}
