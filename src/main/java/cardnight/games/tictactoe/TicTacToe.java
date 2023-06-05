package cardnight.games.tictactoe;

import cardnight.Tools;
import cardnight.games.Spiel;
import cardnight.games.ueno.UenoSpieler;
import javafx.application.Platform;

import java.util.ArrayList;
import java.util.Arrays;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;


public class TicTacToe extends Spiel {

    private TicTacToeView observerView;
    private TicTacToeSpieler xSpieler;
    private TicTacToeSpieler oSpieler;
    private TicTacToeSpieler spielerAmZug;
    private String[] feld;
    private TicTacToeSpieler[] spieler;
    private TicTacToeSpieler gewinner;
    private boolean spieltGegenComputer;

    public TicTacToe(TicTacToeView observerView, boolean spieltGegenComputer) {
        this.observerView = observerView;
        this.spieltGegenComputer = spieltGegenComputer;

        xSpieler = new TicTacToeSpieler("Chris",this, true);
        oSpieler = new TicTacToeSpieler("Finn",this, false);
        feld = new String[9];
        spieler = new TicTacToeSpieler[2];
        spieler[0] = xSpieler;
        spieler[1] = oSpieler;

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
                System.out.println(xSpieler.name + " hat auf Feld " + gesetztesFeld + " gelegt");
                xSpieler.zugSpeichern(gesetztesFeld);
                feld[gesetztesFeld] = "x";
                observerView.updateUi();

                // hat jemand gewonnen?
                gewinner = bestimmeGewinner();
                if (gewinner != null)
                    break;

                // Computer macht Zug bzw. warte auf den Zug des Anderen
                // boolean im Konstruktor, ob gegen Mensch oder Computer
                spielerAmZug = oSpieler;
                if (spieltGegenComputer)
                    gesetztesFeld = computerzugBerechnen();
                else
                    gesetztesFeld = observerView.warteAufSpielerZug(oSpieler);
                System.out.println(oSpieler.name + " hat auf Feld " + gesetztesFeld + " gelegt");
                // Zug in Array speichern
                oSpieler.zugSpeichern(gesetztesFeld);
                feld[gesetztesFeld] = "o";

                // hat jemand gewonnen?
                gewinner = bestimmeGewinner();
                if (gewinner != null)
                    break;

                Platform.runLater(() -> observerView.updateUi());
            }

            Platform.runLater(() -> observerView.beendeSpiel());
        });

        t.setDaemon(true);
        t.start();
    }

    public int computerzugBerechnen() {
        int zahl = (int) (Math.random() * 8);

        while (!feld[zahl].equals(""))
            zahl = (int) (Math.random() * 8);

        return zahl;
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

            if (feld[0].equals(xo) && feld[1].equals(xo) && feld[2].equals(xo))
                return s;
            if (feld[3].equals(xo) && feld[4].equals(xo) && feld[5].equals(xo))
                return s;
            if (feld[6].equals(xo) && feld[7].equals(xo) && feld[8].equals(xo))
                return s;
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
        // TODO: implement this
        return false;
    }

    @Override
    public String gibAnleitung() {
        return Tools.readFile("/cardnight/anleitungen/TicTacToeAnleitung");
    }
}
