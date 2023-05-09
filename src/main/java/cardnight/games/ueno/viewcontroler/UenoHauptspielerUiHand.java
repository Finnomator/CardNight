package cardnight.games.ueno.viewcontroler;

import cardnight.games.ueno.UenoKarte;
import cardnight.games.ueno.UenoKartenArt;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.layout.HBox;

import java.io.IOException;
import java.util.ArrayList;

public class UenoHauptspielerUiHand extends UenoUiHand {

    public HBox handkartenBox;
    @Override
    public void updateUi() {
        handkartenBox.getChildren().clear();

        ArrayList<UenoKarte> ablegbareKarten = spieler.ablegbareKarten();

        for (UenoKarte karte : spieler.gibHandkarten()) {

            FXMLLoader loader;
            Node uiKarte;

            if ((karte.art == UenoKartenArt.PLUS_VIER ||karte.art == UenoKartenArt.FARBWAHL) && karte.farbe == null) {
                loader = new FXMLLoader(getClass().getResource("/cardnight/game-views/ueno/farbwahl-karte.fxml"));
                try {
                    uiKarte = loader.load();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                UenoFarbwahlUiKarte farbwahlUiKarte = loader.getController();
                farbwahlUiKarte.uiErstellen(karte, this);
            } else {
                loader = new FXMLLoader(getClass().getResource("/cardnight/game-views/ueno/standard-karte.fxml"));
                try {
                    uiKarte = loader.load();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                UenoStandartUiKarte standardKarte = loader.getController();
                standardKarte.uiErstellen(karte);
            }

            uiKarte.setDisable(!ablegbareKarten.contains(karte));

            handkartenBox.getChildren().add(uiKarte);
        }
    }
}
