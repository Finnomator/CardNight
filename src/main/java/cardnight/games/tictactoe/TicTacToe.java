package cardnight.games.tictactoe;

import cardnight.games.Spiel;


public class TicTacToe extends Spiel {

    public TicTacToe() {
        TicTacToeSpieler ts1 = new TicTacToeSpieler("Chris",this, true);
        TicTacToeSpieler ts2 = new TicTacToeSpieler("Finn",this, false);
    }

    public TicTacToeSpieler gibGewinner() {
        throw new UnsupportedOperationException();
    }

    public TicTacToeSpieler gibSpielerAmZug() {
        // return new TicTacToeSpieler("Temp", this, true);
        throw new UnsupportedOperationException();
    }

    public boolean istSpielBeendet() {
        throw new UnsupportedOperationException();
    }
}
