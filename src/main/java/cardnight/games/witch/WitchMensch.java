package cardnight.games.witch;

public class WitchMensch extends WitchSpieler {
    public WitchMensch(String n, Witch s) {
        super(n, s);
    }

    @Override
    public void schaetzen() {
        schaetzung = spiel.gibObserverView().warteAufSchaetzung();
    }

    @Override
    public WitchKarte spielen() {
        WitchKarte karte = spiel.gibObserverView().warteAufKartenauswahl();
        System.out.println("Gespielte Karte: " + karte.datenAlsString());
        handkarten.remove(karte);
        return karte;
    }

    @Override
    public boolean istAmZug() {
        throw new UnsupportedOperationException();
    }
}
