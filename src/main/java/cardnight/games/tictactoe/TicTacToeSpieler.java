package cardnight.games.tictactoe;

import cardnight.games.Spieler;

import java.util.ArrayList;

public class TicTacToeSpieler extends Spieler {

    protected TicTacToe spiel;
    public final boolean istX;
    private final ArrayList<Integer> zuege;

    public TicTacToeSpieler(String name, TicTacToe spiel, boolean istX) {
        super(name, spiel);
        this.spiel = spiel;
        this.istX = istX;
        zuege = new ArrayList<>();
    }

    public int gibAnzahlHandKarten() {
        if (istX) {
            return 5 - zuege.size();
        }

        return 4 - zuege.size();
    }

    public void zugSpeichern(int zug) {
        zuege.add(zug);
    }

    public ArrayList<Integer> zuegeZurueckgeben() {
        return zuege;
    }

    @Override
    public boolean istAmZug() {
        return spiel.gibSpielerAmZug() == this;
    }
}
