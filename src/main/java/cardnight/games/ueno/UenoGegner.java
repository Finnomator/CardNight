package cardnight.games.ueno;

public class UenoGegner extends UenoSpieler {

    public UenoGegner(String name, Ueno spiel) {
        super(name, spiel);
    }

    public UenoKarte whaeleKarteZumAblegen() {
        for (UenoKarte karte : handkarten) {
            if (spiel.istKarteAblegbar(karte)) {

                if (karte.art == UenoKartenArt.FARBWAHL || karte.art == UenoKartenArt.PLUS_VIER)
                    karte.setzeFarbe(spiel.gibObersteNachziehkarte().farbe);

                return karte;
            }
        }
        return null;
    }
}
