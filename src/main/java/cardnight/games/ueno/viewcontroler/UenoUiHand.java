package cardnight.games.ueno.viewcontroler;

import cardnight.games.Spiel;
import cardnight.games.ueno.UenoKarte;
import cardnight.games.ueno.UenoSpieler;

import java.io.IOException;

public abstract class UenoUiHand {

    protected UenoSpieler spieler;

    public void uiErstellen(UenoSpieler spieler) {
        this.spieler = spieler;
        updateUi();
    }

    public abstract void updateUi();
}
