package cardnight.games.ueno.viewcontroler;

import cardnight.games.ueno.UenoFarbe;
import cardnight.games.ueno.UenoKarte;

public class UenoFarbwahlUiKarte extends UenoUiKarte {

    private UenoHauptspielerUiHand hauptspielerUiHand;

    public void uiErstellen(UenoKarte karte, UenoHauptspielerUiHand hand) {
        this.karte = karte;
        hauptspielerUiHand = hand;
    }

    public void redAction() {
        karte.farbe = UenoFarbe.ROT;
        hauptspielerUiHand.updateUi();
    }

    public void blueAction() {
        karte.farbe = UenoFarbe.BLAU;
        hauptspielerUiHand.updateUi();
    }

    public void yellowAction() {
        karte.farbe = UenoFarbe.GELB;
        hauptspielerUiHand.updateUi();
    }

    public void greenAction() {
        karte.farbe = UenoFarbe.GRUEN;
        hauptspielerUiHand.updateUi();
    }
}
