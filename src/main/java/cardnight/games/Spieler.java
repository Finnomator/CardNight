package cardnight.games;

public abstract class Spieler {
    public final String name;
    private int punkte;

    public Spieler(String name) {
        this.name = name;
    }

    public void punkteHinzufuegen(int punkte) {
        this.punkte += punkte;
    }

    public int gibPunkte() {
        return punkte;
    }
}
