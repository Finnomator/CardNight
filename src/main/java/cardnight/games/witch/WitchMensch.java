package cardnight.games.witch;

public class WitchMensch extends WitchSpieler {
    public WitchMensch(String n, Witch s) {
        super(n, s);
    }

    @Override
    public void schaetzen() {
        stichSchaetzung = spiel.gibObserverView().warteAufSchaetzung();
        stichSchaetzungenProRunde.add(stichSchaetzung);
        System.out.println("\t\t" + name + " schätzt " + stichSchaetzung);
    }

    @Override
    public WitchKarte spielen() {
        WitchKarte karte = spiel.gibObserverView().warteAufKartenauswahl();
        System.out.println("\t\t" + name + " spielte Karte: " + karte.datenAlsString());
        handkarten.remove(karte);
        return karte;
    }
}
