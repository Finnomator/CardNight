package cardnight.games.witch;

public class WitchMensch extends WitchSpieler {
    public WitchMensch(String n, Witch s) {
        super(n, s);
    }

    @Override
    public void schaetzen() {
        stichSchaetzung = spiel.gibObserverView().warteAufSchaetzung();
        stichSchaetzungenProRunde.add(stichSchaetzung);
        System.out.println(name + " schätzt " + stichSchaetzung);
    }

    @Override
    public WitchKarte spielen() {
        WitchKarte karte = spiel.gibObserverView().warteAufKartenauswahl();
        System.out.println(name + " spielte Karte: " + karte.datenAlsString());
        handkarten.remove(karte);
        return karte;
    }

    @Override
    public boolean istAmZug() {
        throw new UnsupportedOperationException();
    }
}
