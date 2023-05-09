package cardnight.games.ueno.viewcontroler;

import cardnight.games.ueno.UenoSpieler;

public abstract class UenoUiHand {

    protected UenoSpieler spieler;

    public void uiErstellen(UenoSpieler spieler) {
        this.spieler = spieler;
        updateUi();
    }

    public abstract void updateUi();
}
