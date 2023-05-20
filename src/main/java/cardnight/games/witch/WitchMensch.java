package cardnight.games.witch;

public class WitchMensch extends WitchSpieler{
    public WitchMensch(String n, Witch s) {
        super(n, s);
    }

    @Override
    public void schaetzen() {
        //TODO: UI abfrage
    }

    @Override
    public WitchKarte spielen() {
        //TODO: UI abfrage
        return null;
    }

    @Override
    public boolean istAmZug() {
        throw new UnsupportedOperationException();
    }
}
