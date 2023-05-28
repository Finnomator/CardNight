package cardnight.games.ueno.viewcontroler;

import cardnight.games.ueno.UenoKarte;
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

        ArrayList<UenoKarte> gibHandkarten = spieler.gibHandkarten();
        for (int i = 0, gibHandkartenSize = gibHandkarten.size(); i < gibHandkartenSize; i++) { // ConcurrentModificationException gibt's auch hier
            UenoKarte karte = gibHandkarten.get(i);

            FXMLLoader loader;
            Node uiKarte;

            if (karte.istSchwarz()) {
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

            uiKarte.setDisable(!ablegbareKarten.contains(karte) || !spieler.istAmZug());

            handkartenBox.getChildren().add(uiKarte);
        }
    }
}
