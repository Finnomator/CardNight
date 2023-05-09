package cardnight.games.ueno.viewcontroler;

import cardnight.games.ueno.Ueno;
import cardnight.games.ueno.UenoKarte;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;

import javax.swing.*;

public class UenoStandartUiKarte extends UenoUiKarte {

    @FXML
    public Button mainButton;

    @Override
    public void uiErstellen(UenoKarte karte) {
        this.karte = karte;
        mainButton.setStyle("-fx-background-color: " + farbeZuString(karte.farbe) + ";");
        mainButton.setText(uenoKartenArtZuString(karte));
    }

    public void mainButtonAction() {
        mainButton.fireEvent(new UenoKarteKlickEvent(UenoKarteKlickEvent.KLICK, karte));
    }
}
