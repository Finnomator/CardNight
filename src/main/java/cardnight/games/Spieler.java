package cardnight.games;

import java.util.ArrayList;

public abstract class Spieler {
    public final String name;
    protected int punkte;
    protected Spiel spiel;

    public Spieler(String name, Spiel spiel) {
        this.name = name;
        this.spiel = spiel;
    }


    public void punkteHinzufuegen(int punkte) {
        this.punkte += punkte;
    }

    public int gibPunkte() {
        return punkte;
    }
}
