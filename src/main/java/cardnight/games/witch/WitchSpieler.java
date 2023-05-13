package cardnight.games.witch;

import cardnight.games.Spieler;

import java.util.ArrayList;

public abstract class WitchSpieler extends Spieler {

    protected Witch spiel;
    protected int punkte;
    protected int stiche;
    protected ArrayList<WitchKarte> hand;

    public WitchSpieler(String name, Witch spiel) {
        super(name, spiel);
        this.spiel = spiel;
    }

    public abstract void schaetzen();
    public abstract WitchKarte spielen();

    public abstract void stichBekommen();
    public abstract void kartenBekommen(WitchKarte[] neu);
}
