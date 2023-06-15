package cardnight.games;

public abstract class Spieler {
    public final String name;
    protected int punkte;
    protected final Spiel spiel;

    protected Spieler(String name, Spiel spiel) {
        this.name = name;
        this.spiel = spiel;
    }

    public void punkteHinzufuegen(int punkte) {
        this.punkte += punkte;
    }

    public int gibPunkte() {
        return punkte;
    }

    public abstract boolean istAmZug();
}
