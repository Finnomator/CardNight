package cardnight.games.tictactoe;

import cardnight.games.Spieler;

public class TicTacToeSpieler extends Spieler {

    protected TicTacToe spiel;
    public final boolean istX;
    public TicTacToeSpieler(String name, TicTacToe spiel, boolean istX) {
        super(name, spiel);
        this.spiel = spiel;
        this.istX = istX;
    }

    @Override
    public boolean istAmZug() {
        throw new UnsupportedOperationException();
    }
}
