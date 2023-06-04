package cardnight.games.ueno.viewcontroler;

import cardnight.games.ueno.UenoKarte;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;

public class UenoStandartUiKarte extends UenoUiKarte {

    @FXML
    public Button mainButton;

    public void uiErstellen(UenoKarte karte) {
        this.karte = karte;
        bildSetzen(karte);
    }

    private void bildSetzen(UenoKarte karte) {

        ImageView img = new ImageView(UenoKartenBilder.karteZuBild(karte));

        mainButton.setGraphic(img);
    }

    public void mainButtonAction() {
        mainButton.fireEvent(new UenoKarteKlickEvent(UenoKarteKlickEvent.KLICK, karte));
    }
}
