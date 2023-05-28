package cardnight.games.tictactoe;

import cardnight.Tools;
import cardnight.games.Spiel;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;


public class TicTacToe extends Spiel {

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

    @Override
    public String gibAnleitung() {
        return Tools.readFile(getClass().getResource("/cardnight/anleitungen/TicTacToeAnleitung"));
    }
}
