package com.example.informatiksoftwareprojekt.games.ueno;

import com.example.informatiksoftwareprojekt.games.Spiel;

import java.util.Arrays;
import java.util.Stack;

public class Ueno extends Spiel {

    /*
    Aufbau und Bedeutung der Spielkarten:
    Insgesamt befinden sich im UNO Spiel 108 Spielkarten, welche wie folgt aufgeteilt sind:
        19 blaue Karten mit den Ziffern 0 bis 9 (0 nur einmal, alles andere zweimal)
        19 grüne
        19 rote
        19 gelbe
    Macht zusammen 76 Nummernkarten.
    Dazu kommen die 32 Aktionskarten: je
        8 Zieh-Zwei-, Retour- und Aussetzkarten
        und je 4 schwarze Farbwahl- und Zieh-Vier Farbenwahlkarten (beide werden auch als Joker bezeichnet).
    */

    private final Stack<UenoKarte> ablagestapel;
    private final Stack<UenoKarte> nachziehstapel;
    public final UenoSpieler[] spieler;

    public Ueno(int spielerAnzahl, int kartenProSpieler) {

        UenoKartenset.kartenErstellen();

        ablagestapel = new Stack<>();
        nachziehstapel = new Stack<>();
        nachziehstapel.addAll(Arrays.asList(UenoKartenset.erstelleGemischtesSet()));
        spieler = new UenoSpieler[spielerAnzahl];

        for (int i = 0; i < spielerAnzahl; ++i) {
            spieler[i] = new UenoSpieler("Spieler " + i);
            for (int j = 0; j < kartenProSpieler; ++j) {
                spieler[i].handkarten.add(karteNachziehen());
            }
        }

        karteAblegen(karteNachziehen());
        while (zuletztAbgelegteKarte().art != UenoKartenArt.ZAHL)
            karteAblegen(karteNachziehen());
    }

    public UenoKarte zuletztAbgelegteKarte() {
        return ablagestapel.peek();
    }

    public UenoKarte obersteNachziehkarte() {
        return nachziehstapel.peek();
    }

    private UenoKarte karteNachziehen() {
        return nachziehstapel.pop();
    }

    private void karteAblegen(UenoKarte karte) {
        ablagestapel.add(karte);
    }

    public void spielerZug(UenoZug zug) {

        if (zug.start == ZugStart.NACHZIEHSTAPEL) {
            if (zug.ziel != ZugZiel.HAND)
                return;
            zug.spieler.handkarten.add(karteNachziehen());
        } else if (zug.start == ZugStart.HAND) {
            if(zug.ziel != ZugZiel.ABLAGESTAPEL || !istKarteAblegbar(zug.karte))
                return;
            karteAblegen(zug.karte);
            zug.spieler.handkarten.remove(zug.karte);
        }
    }

    public boolean mussKarteZiehen(UenoSpieler spieler) {
        for(UenoKarte handkarte : spieler.handkarten)
            if (istKarteAblegbar(handkarte))
                return false;
        return true;
    }

    public boolean istKarteAblegbar(UenoKarte karte) {
        UenoKarte obersteKarte = zuletztAbgelegteKarte();
        if (karte.art == UenoKartenArt.ZAHL) {
            return karte.wert == obersteKarte.wert || karte.farbe == obersteKarte.farbe;
        } else if (karte.art == UenoKartenArt.FARBWAHL || karte.art == UenoKartenArt.PLUS_VIER) {
            if (karte.farbe == null)
                return true;
            return karte.farbe == obersteKarte.farbe;
        } else {
            return karte.art == obersteKarte.art || karte.farbe == obersteKarte.farbe;
        }
    }
}
