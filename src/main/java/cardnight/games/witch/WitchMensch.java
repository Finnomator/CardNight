package cardnight.games.witch;

import cardnight.Logger;

public class WitchMensch extends WitchSpieler {
    public WitchMensch(String name, Witch spiel) {
        super(name, spiel);
    }

    @Override
    public void schaetzen() {
        stichSchaetzung = spiel.gibObserverView().warteAufSchaetzung();
        stichSchaetzungenProRunde.add(stichSchaetzung);
        Logger.log("\t\t" + name + " schätzt " + stichSchaetzung);
    }

    @Override
    public WitchKarte spielen() {
        WitchKarte karte = spiel.gibObserverView().warteAufKartenauswahl();
        Logger.log("\t\t\t" + name + " spielte Karte: " + karte.datenAlsString());
        handkarten.remove(karte);
        return karte;
    }
}
