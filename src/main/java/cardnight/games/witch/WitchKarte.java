package cardnight.games.witch;

import cardnight.games.Karte;

public class WitchKarte extends Karte {
    protected WitchFarbe farbe;
    protected int wert;
    public WitchKarte(WitchFarbe f, int w) {
        farbe = f;
        wert = w;
    }

    @Override
    public String datenAlsString() {
        if (wert != 0 && wert != 14) {
            return farbe + " " + wert;
        }
        else if (wert == 14) {
            return "Zauberer (" + farbe + ")";
        }
        else {
            return "Narr (" + farbe + ")";
        }
    }
}
