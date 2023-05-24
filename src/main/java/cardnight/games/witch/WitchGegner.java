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

        stichSchaetzung = (int) Math.round(handSchaetzung(handkarten, istAmZug() ? 1 : 0));
        stichSchaetzungenProRunde.add(stichSchaetzung);

        System.out.println("\t\t" + name + " schätzt " + stichSchaetzung + " Stich(e)");
    }

    public double handSchaetzung(ArrayList<WitchKarte> karten, double faengtAn) {
        double anfaenge = faengtAn;

        double schaetzung = 0;
        for (int i = 0; i < karten.size(); i++) {
            double wahrscheinlichkeit = spiel.wahrscheinlichkeit(karten.get(i), karten, 0.5 + 0.75 * (anfaenge / (i + 1)));
            schaetzung += wahrscheinlichkeit;
            anfaenge += wahrscheinlichkeit;
        }

        return  schaetzung;
    }

    @Override
    public WitchKarte spielen() {
        ArrayList<WitchKarte> spielbar = spielbareKarten();

        WitchKarte besterZug = spielbar.get(0);
        double bewertung = 100;

        for (WitchKarte betrachteteKarte : spielbar) {
            int karteAufStich = spiel.stich.length + 1;
            for (int i = 0; i < spiel.stich.length; i++) {
                if (spiel.stich[i] == null) {
                    karteAufStich = i;
                    break;
                }
            }
            WitchKarte [] neuerStich = spiel.stich.clone();
            neuerStich[karteAufStich] = betrachteteKarte;
            double gewinnerStich = spiel.stichGeben(0, neuerStich, karteAufStich + 1);
            double machtStich = spiel.wahrscheinlichkeit(betrachteteKarte, handkarten, gewinnerStich == karteAufStich ? 1 : 0);

            ArrayList<WitchKarte> neueHand = (ArrayList<WitchKarte>)handkarten.clone();
            neueHand.remove(betrachteteKarte);

            double neueSticheAnzahl = stichSchaetzung - anzahlErhaltenerStiche - machtStich;
            double neueWahrscheinlichkeit = handSchaetzung(neueHand, machtStich);

            double neueBewertung = Math.abs(neueWahrscheinlichkeit - neueSticheAnzahl);

            if (neueBewertung < bewertung) {
                bewertung = neueBewertung;
                besterZug = betrachteteKarte;
            }
        }



        // Random Karte zum Testen
        // WitchKarte k = spielbar.get((int) (Math.random() * spielbar.size())); //Zufälliges Element der Liste

        handkarten.remove(besterZug);

        System.out.println("\t\t\t" + name + " spielt Karte " + besterZug.datenAlsString());

        return besterZug;
    }
}
