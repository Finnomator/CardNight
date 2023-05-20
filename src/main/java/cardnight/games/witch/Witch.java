package cardnight.games.witch;

import cardnight.games.Spiel;
import cardnight.games.witch.viewcontroller.WitchView;
import javafx.application.Platform;

import java.util.ArrayList;

public class Witch extends Spiel {
    private final WitchSpieler[] spieler;
    protected int anzahlSpieler;
    protected int start;
    protected WitchKarte[] stich;
    protected ArrayList<WitchKarte> benutzt;
    protected int spielerAmZug;
    protected WitchKarte trumpfKarte;
    private final int kartenAnzahlInEinemSpiel = 60;
    private final WitchView observerView;

    public Witch(int anzahl, WitchView observerView) {
        this.observerView = observerView;
        anzahlSpieler = anzahl;

        spieler = new WitchSpieler[anzahl];
        spieler[0] = new WitchMensch("Hauptspieler", this);
        for (int i = 1; i < anzahlSpieler; i++)
            spieler[i] = new WitchGegner("Gegner " + i, this);

        start = (int) (Math.random() * anzahl);
        stich = new WitchKarte[anzahl];
        benutzt = new ArrayList<>();

        kartenSammeln();
    }

    public WitchKarte gibTrumpfKarte() {
        return trumpfKarte;
    }

    public WitchSpieler[] gibSpieler() {
        return spieler;
    }

    public WitchView gibObserverView() {
        return observerView;
    }

    public void game() {
        // Große Runde = alle Leute legen Karten ab, bis sie keine mehr haben.
        // Kleine Runde = alle Leute legen 1 Karte ab.

        new Thread(() -> {
            for (int anzahlKartenProSpieler = 1; anzahlKartenProSpieler <= kartenAnzahlInEinemSpiel / anzahlSpieler; anzahlKartenProSpieler++) {
                kartenAusteilen(anzahlKartenProSpieler);
                spielerSchaetzen();

                // Spielen
                int startSpielerDerKleinenRunde = start;
                spielerAmZug = startSpielerDerKleinenRunde;
                for (int anzahlUebrigerKarten = anzahlKartenProSpieler; anzahlUebrigerKarten > 0; anzahlUebrigerKarten--) {
                    // Jeder Stich
                    for (int i = 0; i < anzahlSpieler; i++) {
                        spielerAmZug = (startSpielerDerKleinenRunde + i) % anzahlSpieler;
                        stich[i] = spieler[spielerAmZug].spielen();
                    }
                    // Der Stich wird dem Gewinner gegeben.
                    // Der Gewinner ist als Nächstes dran
                    startSpielerDerKleinenRunde = stichGeben(startSpielerDerKleinenRunde);
                    // Ablage wird geleert
                    stich = new WitchKarte[anzahlSpieler];
                    spielerAmZug = startSpielerDerKleinenRunde;
                    update();
                }
                punkteVerteilen();
                update();
            }

            Platform.runLater(observerView::beendeSpiel);
        }).start();
    }

    private void spielerSchaetzen() {
        delay(500);

        for (int j = 0; j < anzahlSpieler; j++)
            spieler[j].schaetzen();

        update();
    }

    public void update() {
        //TODO: hier
        //TODO: Wenn alle Karten verteilt werden, darf nur die Farbe als Trumpf angezeigt werden
        //temporär:
        System.out.println("Trumpf: " + trumpfKarte.farbe);
        System.out.println("Am Zug: " + spielerAmZug);
        System.out.println("Stich: " + stich.toString());
        System.out.println("Handkarten: " + spieler[0].hand.toString());

        Platform.runLater(observerView::updateUi);
    }

    public static void delay(long millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public void kartenAusteilen(int anzKarten) {
        // Trumpf festlegen                              Wenn nicht alle Karten verteilt werden
        trumpfKarte = WitchKartenset.gibZufaelligeKarte(kartenAnzahlInEinemSpiel / anzKarten != anzahlSpieler);

        // Karten austeilen
        for (WitchSpieler s : spieler)
            for (int i = 0; i < anzKarten; i++)
                s.hand.add(WitchKartenset.gibZufaelligeKarte(true));

        update();
    }

    public int stichGeben(int startSpieler) {
        // Falls es einen Zauberer gab
        for (int i = 0; i < anzahlSpieler; i++) {
            if (stich[i].wert == 14) {
                spieler[(startSpieler + i) % anzahlSpieler].stiche++;
                return (startSpieler + i) % anzahlSpieler;
            }
        }

        // Höchste Trumpffarbe
        if (trumpfKarte.wert != 0 && trumpfKarte.wert != 14) { //Falls Trumpf eine weiße Karte ist
            int hoechste = 0;
            int s = 0;
            for (int i = 0; i < anzahlSpieler; i++) {
                if (stich[i].farbe == trumpfKarte.farbe) {
                    if (stich[i].wert > hoechste) {
                        hoechste = stich[i].wert;
                        s = i;
                    }
                }
            }
            if (hoechste > 0) { //Falls es eine Trumpfkarte (keinen Narren) gegeben hat
                spieler[(startSpieler + s) % anzahlSpieler].stiche++;
                return (startSpieler + s) % anzahlSpieler;
            }
        }

        // Höchste Karte (nicht Trumpf)
        WitchFarbe f = null;
        int hoechste = 0;
        int s = 0;
        for (int i = 0; i < anzahlSpieler; i++) {
            if (f == null && stich[i].wert != 0) {  //Wenn
                f = stich[i].farbe;
            }
            if (stich[i].farbe == f) {
                if (stich[i].wert > hoechste) {
                    hoechste = stich[i].wert;
                    s = i;
                }
            }
        }
        if (hoechste > 0) { //Falls es eine Farbige Karte gegeben hat (also nicht nur Narren)
            spieler[(startSpieler + s) % anzahlSpieler].stiche++;
            return (startSpieler + s) % anzahlSpieler;
        }

        //Es gab also nur Narren
        spieler[startSpieler].stiche++;
        return startSpieler;
    }

    public void punkteVerteilen() {
        //Punkte werden verteilt
        for (WitchSpieler s : spieler) {
            if (s.stiche != s.schaetzung) {
                //Wenn Falsch geschätzt wurde: -10 pro Stich daneben
                s.punkte -= 10 * (Math.abs(s.stiche - s.schaetzung));
            } else {
                //Wenn richtig geschätzt: +20 und +10 pro Stich
                s.punkte += 20 + 10 * s.stiche;
            }
        }
    }

    public void kartenSammeln() {
        for (WitchSpieler s : spieler) {
            s.hand.clear();
            s.stiche = 0;
        }
        stich = new WitchKarte[anzahlSpieler];
        benutzt.clear();
        WitchKartenset.kartensetErstellen();
    }

    public int anzKartenBesser(WitchKarte k, ArrayList<WitchKarte> hand) {
        //Gibt zurück, wie viele bessere Karten noch im Spiel sind.
        //k muss in hand sein
        ArrayList<WitchKarte> neu_benutzt = new ArrayList<>(benutzt);
        neu_benutzt.addAll(hand);
        neu_benutzt.remove(k);
        int besser;

        if (k.wert == 14) {
            return 0;
        }
        if (k.wert == 0) {
            besser = 56 - neu_benutzt.size(); //Alle Karten außer Narren sind besser
            for (WitchKarte i : neu_benutzt) { //Alle schon benutzten Narren werden dazuaddiert
                if (i.wert == 0) {
                    besser++;
                }
            }
            return besser;
        }

        //Die Karte ist nicht weiß:
        besser = 4; //Die 4 Zauberer
        for (WitchKarte i : neu_benutzt) { //Alle schon benutzten Zauberer werden abgezogen
            if (i.wert == 14) {
                besser--;
            }
        }
        if (trumpfKarte.wert != 0 && trumpfKarte.wert != 14) { //Falls der Trumpf nicht weiß ist
            if (k.farbe != trumpfKarte.farbe) { //Falls die Karte nicht Trumpf ist
                besser += 13; //Die Trumpfkarten sind besser
                for (WitchKarte i : neu_benutzt) { //Alle schon benutzten Trümpfe werden abgezogen
                    if (i.farbe == trumpfKarte.farbe && i.wert != 0 && i.wert != 14) {
                        besser--;
                    }
                }
            }
        }

        besser += 13 - k.wert; //Alle höheren Karten der gleichen Farbe sind besser
        for (WitchKarte i : neu_benutzt) { //Alle schon benutzten besseren gleichfarbigen Karten werden abgezogen
            if (i.farbe == k.farbe && i.wert > k.wert && i.wert != 14) {
                besser--;
            }
        }

        return besser;
    }

    public int anzKartenSchlechter(WitchKarte k, ArrayList<WitchKarte> hand) {
        //Gibt zurück, wie viele schlechtere Karten noch im Spiel sind.
        //k muss in hand sein

        //ALle übrigen Karten minus die besseren Karten davon
        return kartenAnzahlInEinemSpiel - benutzt.size() - hand.size() - anzKartenBesser(k, hand);
    }

    public double wahrscheinlichkeit(WitchKarte k, ArrayList<WitchKarte> hand) {
        //Gibt zurück, wie wahrscheinlich es ist, dass eine Karte bei einem Gegner durchläuft
        int anzKarten = hand.size();
        int uebrig = kartenAnzahlInEinemSpiel - benutzt.size() - hand.size();

        //Wahrscheinlichkeit eine bessere Karte legen zu müssen
        //TODO: here
        throw new RuntimeException();
    }

    @Override
    public boolean istSpielBeendet() {
        throw new UnsupportedOperationException();
    }
}
