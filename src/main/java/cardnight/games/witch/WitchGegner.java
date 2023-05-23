package cardnight.games.witch;

import java.util.ArrayList;

public class WitchGegner extends WitchSpieler {
    public WitchGegner(String n, Witch s) {
        super(n, s);
        handkarten = new ArrayList<>();
    }

    @Override
    public void schaetzen() {
        //TODO: schätzung
        schaetzung = 0;
    }

    @Override
    public WitchKarte spielen() {
        ArrayList<WitchKarte> spielbar = spielbareKarten();

        // Random Karte zum Testen TODO: smart AI
        WitchKarte k = spielbar.get((int)(Math.random() * spielbar.size())); //Zufälliges Element der Liste

        handkarten.remove(k);
        return k;
    }

    @Override
    public boolean istAmZug() {
        throw new UnsupportedOperationException();
    }
}
