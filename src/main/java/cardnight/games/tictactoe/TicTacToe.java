package cardnight.games.tictactoe;

import cardnight.Tools;
import cardnight.games.Spiel;
import cardnight.games.tictactoe.viewcontroller.TTTSoundPlayer;
import cardnight.games.tictactoe.viewcontroller.TicTacToeView;

import java.util.Arrays;


public class TicTacToe extends Spiel {

    private final TicTacToeView observerView;
    public final TicTacToeSpieler xSpieler;
    public final TicTacToeSpieler oSpieler;
    private TicTacToeSpieler spielerAmZug;
    private final String[] feld;
    private final TicTacToeSpieler[] spieler;
    private TicTacToeSpieler gewinner;
    private final boolean spieltGegenComputer;
    private int zugNummer;

    public TicTacToe(TicTacToeView observerView, boolean spieltGegenComputer) {
        this.observerView = observerView;
        this.spieltGegenComputer = spieltGegenComputer;

        xSpieler = new TicTacToeSpieler("X",this, true);
        oSpieler = new TicTacToeSpieler("O",this, false);
        feld = new String[9];
        spieler = new TicTacToeSpieler[2];
        spieler[0] = xSpieler;
        spieler[1] = oSpieler;
        spielerAmZug = xSpieler;

        Arrays.fill(feld, "");
    }

    public String[] gibFeld() {
        return feld;
    }

    public void spielAblauf() {

        Thread t = new Thread(() -> {
            while (!istSpielBeendet()) {

                // warte bis Spieler Karte legt
                spielerAmZug = xSpieler;
                int gesetztesFeld = observerView.warteAufSpielerZug(xSpieler);
                zugNummer++;
                System.out.println("Zug " + zugNummer + ": " + xSpieler.name + " hat auf Feld " + gesetztesFeld + " gelegt");
                xSpieler.zugSpeichern(gesetztesFeld);
                feld[gesetztesFeld] = "x";
                observerView.updateUi();

                // hat jemand gewonnen?
                gewinner = bestimmeGewinner();
                if (istSpielBeendet())
                    break;

                // Computer macht Zug bzw. warte auf den Zug des Anderen
                // boolean im Konstruktor, ob gegen Mensch oder Computer
                spielerAmZug = oSpieler;
                if (spieltGegenComputer)
                    gesetztesFeld = computerzugMachen();
                else
                    gesetztesFeld = observerView.warteAufSpielerZug(oSpieler);

                zugNummer++;
                System.out.println("Zug " + zugNummer + ": " + oSpieler.name + " hat auf Feld " + gesetztesFeld + " gelegt");
                // Zug in Array speichern
                oSpieler.zugSpeichern(gesetztesFeld);
                feld[gesetztesFeld] = "o";

                // hat jemand gewonnen?
                gewinner = bestimmeGewinner();
                if (istSpielBeendet())
                    break;

                if (spieltGegenComputer)
                    TTTSoundPlayer.randomFeldAusgesucht();

                delay(400);

                observerView.updateUi();
            }

            observerView.updateUi();

            observerView.beendeSpiel();
        });

        t.setDaemon(true);
        t.start();
    }

    private int computerzugMachen() {
        TTTSoundPlayer.randomUeberlegen();
        delay(2000);
        return TicTacToeGegner.zugMachen(feld);
    }

    private void delay(long millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    private TicTacToeSpieler bestimmeGewinner() {
        // wenn 3 nebeneinander/diagonale Felder dieselbe Karte haben, dann Gewinner zurückgeben

        // 0 | 1 | 2
        // 3 | 4 | 5
        // 6 | 7 | 8

        String[] spielerStrings = {"x", "o"};

        for (int i = 0; i < 2; ++i) {

            String xo = spielerStrings[i];
            TicTacToeSpieler s = spieler[i];

            // Horizontal

            if (feld[0].equals(xo) && feld[1].equals(xo) && feld[2].equals(xo))
                return s;
            if (feld[3].equals(xo) && feld[4].equals(xo) && feld[5].equals(xo))
                return s;
            if (feld[6].equals(xo) && feld[7].equals(xo) && feld[8].equals(xo))
                return s;

            // Vertical

            if (feld[0].equals(xo) && feld[3].equals(xo) && feld[6].equals(xo))
                return s;
            if (feld[1].equals(xo) && feld[4].equals(xo) && feld[7].equals(xo))
                return s;
            if (feld[2].equals(xo) && feld[5].equals(xo) && feld[8].equals(xo))
                return s;

            // Schräg

            if (feld[0].equals(xo) && feld[4].equals(xo) && feld[8].equals(xo))
                return s;
            if (feld[2].equals(xo) && feld[4].equals(xo) && feld[6].equals(xo))
                return s;

        }
        return null;
    }

    public TicTacToeSpieler gibGewinner() {
        return gewinner;
    }

    public TicTacToeSpieler gibSpielerAmZug() {
        return spielerAmZug;
    }

    public boolean istSpielBeendet() {
        return gewinner != null || zugNummer >= 9;
    }

    @Override
    public String gibAnleitung() {
        return Tools.readFile("/cardnight/anleitungen/TicTacToeAnleitung");
    }
}
