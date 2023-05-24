package cardnight.games.tictactoe;

import cardnight.games.Spieler;

import java.util.ArrayList;

public class TicTacToeSpieler extends Spieler {

    protected TicTacToe spiel;
    public final boolean istX;
    private ArrayList<Integer> zuege;
    public TicTacToeSpieler(String name, TicTacToe spiel, boolean istX) {
        super(name, spiel);
        this.spiel = spiel;
        this.istX = istX;
        zuege = new ArrayList<>();
    }

    public void zugSpeichern(int zug) {
        zuege.add(zug);
    }

    public ArrayList<Integer> zuegeZurueckgeben() {
        return zuege;
    }

    @Override
    public boolean istAmZug() {
        throw new UnsupportedOperationException();
    }
}
