package cardnight.games.ueno;

import cardnight.games.Karte;

public class UenoKarte extends Karte {

    public UenoFarbe farbe;
    public final UenoKartenArt art;
    public final int wert;
    private boolean effektWurdeAktiviert = false;

    public UenoKarte(UenoFarbe farbe, UenoKartenArt art) {
        assert art != UenoKartenArt.ZAHL;

        this.farbe = farbe;
        this.art = art;
        wert = -1;
    }

    public UenoKarte(int wert, UenoFarbe farbe) {
        this.wert = wert;
        this.farbe = farbe;
        art = UenoKartenArt.ZAHL;
    }

    public void setzeFarbe(UenoFarbe farbe) {
        assert this.farbe == null;
        this.farbe = farbe;
    }

    public void aktiviereEffekt() {
        effektWurdeAktiviert = true;
    }

    public boolean wurdeEffektAktiviert() {
        return effektWurdeAktiviert;
    }

    @Override
    public String datenAlsString() {
        return art + " " + farbe + " " + wert;
    }
}
