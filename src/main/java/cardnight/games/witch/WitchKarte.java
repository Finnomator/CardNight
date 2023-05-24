package cardnight.games.witch;

import cardnight.games.Karte;

public class WitchKarte extends Karte {
    public final WitchFarbe farbe;
    public final int wert;
    public WitchKarte(WitchFarbe farbe, int wert) {
        this.farbe = farbe;
        this.wert = wert;
    }

    public boolean istZauberer() {
        return wert == 14;
    }

    public boolean istNarr() {
        return wert == 0;
    }

    public int vergleichsWert(WitchKarte trumpf) {
        if (istZauberer()) {
            return 27;
        }
        if (istNarr()) {
            return 0;
        }
        if (! trumpf.istNarr() && ! trumpf.istZauberer() && farbe == trumpf.farbe) {
            return wert + 13;
        }
        return wert;
    }

    @Override
    public String datenAlsString() {
        if (wert != 0 && wert != 14)
            return farbe + " " + wert;
        else if (wert == 14)
            return "Zauberer (" + farbe + ")";
        else
            return "Narr (" + farbe + ")";
    }
}
