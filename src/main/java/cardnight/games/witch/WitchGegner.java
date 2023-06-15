package cardnight.games.witch;

import cardnight.Logger;

import java.util.ArrayList;
import java.util.Collections;

public class WitchGegner extends WitchSpieler {
    public WitchGegner(String n, Witch s) {
        super(n, s);
        handkarten = new ArrayList<>();
    }

    @Override
    public void schaetzen() {
        handkarten.sort((o1, o2) -> o1.vergleichsWert(spiel.gibTrumpfKarte()) - (o2.vergleichsWert(spiel.gibTrumpfKarte())));
        Collections.reverse(handkarten);

        stichSchaetzung = (int) Math.round(handSchaetzung(handkarten, istAmZug() ? 1 : 0));
        stichSchaetzungenProRunde.add(stichSchaetzung);

        Logger.log("\t\t" + name + " schätzt " + stichSchaetzung + " Stich(e)");
    }

    public double handSchaetzung(ArrayList<WitchKarte> karten, double faengtAn) {
        double anfaenge = faengtAn;

        double schaetzung = 0;
        for (int i = 0; i < karten.size(); i++) {
            double wahrscheinlichkeit = spiel.wahrscheinlichkeit(karten.get(i), karten, anfaenge / (i + 1));
            schaetzung += wahrscheinlichkeit;
            anfaenge += wahrscheinlichkeit;
        }

        double durchschnittsSchaetzung = (double) karten.size() / spiel.gibAnzahlSpieler();
        if (schaetzung > durchschnittsSchaetzung + 1) {
            schaetzung = (2 * schaetzung + ((double) karten.size() / spiel.gibAnzahlSpieler())) / 3;
        }
        return schaetzung;
    }

    @Override
    public WitchKarte spielen() {
        ArrayList<WitchKarte> spielbar = spielbareKarten();

        WitchKarte besterZug = spielbar.get(0);
        double bewertung = 100;

        for (WitchKarte betrachteteKarte : spielbar) {
            int karteAufStich = spiel.gibStich().length + 1;
            for (int i = 0; i < spiel.gibStich().length; i++) {
                if (spiel.gibStich()[i] == null) {
                    karteAufStich = i;
                    break;
                }
            }
            WitchKarte[] neuerStich = spiel.gibStich().clone();
            neuerStich[karteAufStich] = betrachteteKarte;
            int gewinnerStich = spiel.stichGeben(0, neuerStich, karteAufStich + 1);
            double machtStich;
            if (gewinnerStich == karteAufStich) {
                machtStich = spiel.wahrscheinlichkeit(betrachteteKarte, handkarten, 1);
            } else {
                machtStich = 0;
            }

            ArrayList<WitchKarte> neueHand = (ArrayList<WitchKarte>) handkarten.clone();
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

        Logger.log("\t\t\t" + name + " spielt Karte " + besterZug.datenAlsString());

        return besterZug;
    }
}
