package cardnight.games.witch;

import java.util.ArrayList;
import java.util.Collections;

public class WitchGegner extends WitchSpieler {
    public WitchGegner(String n, Witch s) {
        super(n, s);
        handkarten = new ArrayList<>();
    }

    @Override
    public void schaetzen() {
        handkarten.sort((o1, o2) -> o1.vergleichsWert(spiel.trumpfKarte) - (o2.vergleichsWert(spiel.trumpfKarte)));
        Collections.reverse(handkarten);

        double anfaenge = 0;
        if (istAmZug()) {
            anfaenge += 1;
        }

        double schaetzung = 0;
        for (int i = 0; i < handkarten.size(); i++) {
            double wahrscheinlichkeit = spiel.wahrscheinlichkeit(handkarten.get(i), handkarten, 0.5 + 0.75 * (anfaenge / (i + 1)));
            schaetzung += wahrscheinlichkeit;
            anfaenge += wahrscheinlichkeit;
        }

        stichSchaetzung = (int) Math.round(schaetzung);
        stichSchaetzungenProRunde.add(stichSchaetzung);

        System.out.println("\t\t" + name + " schätzt " + stichSchaetzung + " Stich(e)");
    }

    @Override
    public WitchKarte spielen() {
        ArrayList<WitchKarte> spielbar = spielbareKarten();

        // Random Karte zum Testen TODO: smart AI
        WitchKarte k = spielbar.get((int) (Math.random() * spielbar.size())); //Zufälliges Element der Liste

        handkarten.remove(k);

        System.out.println("\t\t\t" + name + " spielt Karte " + k.datenAlsString());

        return k;
    }
}
