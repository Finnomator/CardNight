package cardnight.games.ueno;

import cardnight.games.Spieler;

import java.util.ArrayList;

public class UenoSpieler extends Spieler {

    protected final ArrayList<UenoKarte> handkarten;
    protected final Ueno spiel;
    public UenoSpieler(String name, Ueno spiel) {
        super(name, spiel);
        this.spiel = spiel;
        handkarten = new ArrayList<>();
    }

    public void entferneKarte(UenoKarte karte) {
        handkarten.remove(karte);
    }

    public void fuegeKarteHinzu(UenoKarte karte) {
        handkarten.add(karte);
    }

    public ArrayList<UenoKarte> ablegbareKarten() {
        ArrayList<UenoKarte> karten = new ArrayList<>();
        for(UenoKarte karte : handkarten) {
            if (spiel.istKarteAblegbar(karte))
                karten.add(karte);
        }
        return karten;
    }

    public boolean kannKarteAblegen() {
        return ablegbareKarten().size() > 0;
    }

    public ArrayList<UenoKarte> gibHandkarten() {
        return handkarten;
    }

    @Override
    public boolean istAmZug() {
        return spiel.gibAktivenSpieler() == this;
    }
}
