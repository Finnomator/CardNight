package cardnight.games.ueno.viewcontroler;

import cardnight.games.ueno.UenoFarbe;
import cardnight.games.ueno.UenoKarte;
import cardnight.games.ueno.UenoKartenArt;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;

public class UenoStandartUiKarte extends UenoUiKarte {

    @FXML
    public Button mainButton;

    @Override
    public void uiErstellen(UenoKarte karte) {
        this.karte = karte;
        // TODO: Das Ändern, sobald alle Bilder da sind
        if (karte.art == UenoKartenArt.ZAHL && karte.farbe == UenoFarbe.ROT)
            bildSetzen(karte);
        else {
            mainButton.setStyle("-fx-background-color: " + farbeZuString(karte.farbe) + ";");
            mainButton.setText(uenoKartenArtZuString(karte));
        }
    }

    private void bildSetzen(UenoKarte karte) {

        ImageView img = new ImageView(UenoKartenBilder.zahlenKarten.get(karte.farbe)[karte.wert]);

        if (karte.art == UenoKartenArt.ZAHL && karte.farbe == UenoFarbe.ROT)
            mainButton.setGraphic(img);

    }

    public void mainButtonAction() {
        mainButton.fireEvent(new UenoKarteKlickEvent(UenoKarteKlickEvent.KLICK, karte));
    }
}
