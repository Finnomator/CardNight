package cardnight.games.ueno.viewcontroler;

import cardnight.games.ueno.Ueno;
import cardnight.games.ueno.UenoFarbe;
import cardnight.games.ueno.UenoKarte;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.control.*;
import javafx.scene.text.Text;

import java.io.IOException;

public class UenoFarbwahlUiKarte extends UenoUiKarte {

    public Text artText;
    private UenoHauptspielerUiHand hauptspielerUiHand;

    public void uiErstellen(UenoKarte karte, UenoHauptspielerUiHand hand) {
        this.karte = karte;
        hauptspielerUiHand = hand;
        artText.setText(uenoKartenArtZuString(karte));
    }

    public void redAction(ActionEvent actionEvent) {
        karte.farbe = UenoFarbe.ROT;
        hauptspielerUiHand.updateUi();
    }

    public void blueAction(ActionEvent actionEvent) {
        karte.farbe = UenoFarbe.BLAU;
        hauptspielerUiHand.updateUi();
    }

    public void yellowAction(ActionEvent actionEvent) {
        karte.farbe = UenoFarbe.GELB;
        hauptspielerUiHand.updateUi();
    }

    public void greenAction(ActionEvent actionEvent) {
        karte.farbe = UenoFarbe.GRUEN;
        hauptspielerUiHand.updateUi();
    }

    @Override
    public void uiErstellen(UenoKarte karte) {
        throw new RuntimeException();
    }
}
