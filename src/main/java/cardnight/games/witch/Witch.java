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
    private int rundenNummer;

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
    public WitchMensch gibHauptspieler() {
        return (WitchMensch) spieler[0];
    }

    public int gibRundenNummer() {
        return rundenNummer;
    }

    public WitchView gibObserverView() {
        return observerView;
    }

    public void game() {
        // Große Runde = alle Leute legen Karten ab, bis sie keine mehr haben.
        // Kleine Runde = alle Leute legen 1 Karte ab.

        new Thread(() -> {
            for (int anzahlKartenProSpieler = 1; anzahlKartenProSpieler <= kartenAnzahlInEinemSpiel / anzahlSpieler; anzahlKartenProSpieler++, ++rundenNummer) {

                System.out.println("\nRunde " + rundenNummer + " mit " + anzahlKartenProSpieler + " Karte(n) pro Spieler");

                kartenAusteilen(anzahlKartenProSpieler);
                spielerSchaetzen();

                // Spielen
                int startSpielerDerKleinenRunde = start;
                spielerAmZug = startSpielerDerKleinenRunde;
                for (int anzahlUebrigerKarten = anzahlKartenProSpieler; anzahlUebrigerKarten > 0; anzahlUebrigerKarten--) {
                    // Jeder Stich
                    for (int i = 0; i < anzahlSpieler; i++) {
                        update();
                        spielerAmZug = (startSpielerDerKleinenRunde + i) % anzahlSpieler;
                        delay(500);
                        stich[i] = spieler[spielerAmZug].spielen();
                    }

                    // Der Stich wird dem Gewinner gegeben.
                    // Der Gewinner ist als Nächstes dran
                    startSpielerDerKleinenRunde = stichGeben(startSpielerDerKleinenRunde);
                    spieler[startSpielerDerKleinenRunde].fuegeStichHinzu();
                    // Ablage wird geleert
                    stich = new WitchKarte[anzahlSpieler];
                    spielerAmZug = startSpielerDerKleinenRunde;
                    update();
                }

                punkteVerteilen();
                update();
                kartenSammeln();
            }

            Platform.runLater(observerView::beendeSpiel);
        }).start();
    }

    private void spielerSchaetzen() {

        System.out.println("Es wird geschätzt");

        for (int j = 0; j < anzahlSpieler; j++)
            spieler[j].schaetzen();

        update();
    }

    public void update() {
        // TODO: Wenn alle 60 Karten verteilt werden, darf nur die Farbe als Trumpf angezeigt werden

        System.out.println("***Ui Update***");
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

        System.out.println("Es werden Karten ausgeteilt");

        // Karten austeilen
        for (WitchSpieler s : spieler)
            for (int i = 0; i < anzKarten; i++)
                s.handkarteHinzufuegen(WitchKartenset.gibZufaelligeKarte(true));

        update();
    }

    public int stichGeben(int startSpieler) {
        // Falls es einen Zauberer gab
        for (int i = 0; i < anzahlSpieler; i++) {
            if (stich[i].istZauberer())
                return (startSpieler + i) % anzahlSpieler;
        }

        // Höchste Trumpffarbe
        if (!trumpfKarte.istNarr() && !trumpfKarte.istZauberer()) { //Falls Trumpf eine weiße Karte ist
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

            //Falls es eine Trumpfkarte (keinen Narren) gegeben hat
            if (hoechste > 0) return (startSpieler + s) % anzahlSpieler;
        }

        // Höchste Karte (nicht Trumpf)
        WitchFarbe farbe = null;
        int hoechste = 0;
        int s = 0;
        for (int i = 0; i < anzahlSpieler; i++) {

            if (farbe == null && !stich[i].istNarr())
                farbe = stich[i].farbe;

            if (stich[i].farbe == farbe && stich[i].wert > hoechste) {
                hoechste = stich[i].wert;
                s = i;
            }
        }
        // Falls es eine Farbige Karte gegeben hat (also nicht nur Narren)
        if (hoechste > 0)
            return (startSpieler + s) % anzahlSpieler;

        // Es gab also nur Narren
        return startSpieler;
    }

    public void punkteVerteilen() {

        System.out.println("Es werden Punkte verteilt");

        for (WitchSpieler s : spieler) {
            // Wenn falsch geschätzt wurde: -10 pro Stich daneben,
            // wenn richtig geschätzt: +20 und +10 pro Stich
            int erzhaltendeStiche = s.gibAnzahlErzhaltenderStiche();
            int geschaetzteStiche = s.gibStichSchaetzung();

            if (erzhaltendeStiche != geschaetzteStiche)
                s.punkteHinzufuegen(-10 * Math.abs(erzhaltendeStiche - geschaetzteStiche));
            else
                s.punkteHinzufuegen(20 + 10 * erzhaltendeStiche);
        }
    }

    public void kartenSammeln() {
        for (WitchSpieler s : spieler) {
            s.clearHandkarten();
            s.setzeAnzahlErhalteneSticheZurueck();
        }
        stich = new WitchKarte[anzahlSpieler];
        benutzt.clear();
        WitchKartenset.kartensetErstellen();
    }

    public int anzahlBessererKarten(WitchKarte referenzKarte, ArrayList<WitchKarte> hand) {
        // Gibt zurück, wie viele bessere Karten noch im Spiel sind.
        // referenzKarte muss in hand sein

        ArrayList<WitchKarte> neuBenutzt = new ArrayList<>(benutzt);
        neuBenutzt.addAll(hand);
        neuBenutzt.remove(referenzKarte);

        int anzahlBessererKarten = 0;

        if (referenzKarte.istZauberer())
            return anzahlBessererKarten;

        if (referenzKarte.istNarr()) {
            anzahlBessererKarten = 56 - neuBenutzt.size(); // Alle Karten außer Narren sind anzahlBessererKarten
            // Alle schon benutzten Narren werden dazuaddiert
            for (WitchKarte karte : neuBenutzt)
                if (karte.istNarr())
                    anzahlBessererKarten++;
            return anzahlBessererKarten;
        }

        // Ab hier ist die Karte nicht weiß

        // Die 4 Zauberer
        anzahlBessererKarten = 4;

        // Alle schon benutzten Zauberer werden abgezogen
        for (WitchKarte karte : neuBenutzt)
            if (karte.istZauberer())
                anzahlBessererKarten--;

        if (trumpfKarte.wert != 0 && trumpfKarte.wert != 14) { // Falls der Trumpf nicht weiß ist
            if (referenzKarte.farbe != trumpfKarte.farbe) { // Falls die Karte nicht Trumpf ist
                anzahlBessererKarten += 13; // Die Trumpfkarten sind anzahlBessererKarten

                // Alle schon benutzten Trümpfe werden abgezogen
                for (WitchKarte i : neuBenutzt)
                    if (i.farbe == trumpfKarte.farbe && i.wert != 0 && i.wert != 14)
                        anzahlBessererKarten--;
            }
        }

        // Alle höheren Karten der gleichen Farbe sind anzahlBessererKarten
        anzahlBessererKarten += 13 - referenzKarte.wert;

        // Alle schon benutzten besseren gleichfarbigen Karten werden abgezogen
        for (WitchKarte i : neuBenutzt)
            if (i.farbe == referenzKarte.farbe && i.wert > referenzKarte.wert && i.wert != 14)
                anzahlBessererKarten--;

        return anzahlBessererKarten;
    }

    public int anzahlSchlechtererKarten(WitchKarte referenzKarte, ArrayList<WitchKarte> hand) {
        // Gibt zurück, wie viele schlechtere Karten noch im Spiel sind.
        // referenzKarte muss in hand sein

        // ALle übrigen Karten minus die besseren Karten davon
        return kartenAnzahlInEinemSpiel - benutzt.size() - hand.size() - anzahlBessererKarten(referenzKarte, hand);
    }

    public double wahrscheinlichkeit(WitchKarte k, ArrayList<WitchKarte> hand) {
        // Gibt zurück, wie wahrscheinlich es ist, dass eine Karte bei einem Gegner durchläuft
        int anzKarten = hand.size();
        int uebrig = kartenAnzahlInEinemSpiel - benutzt.size() - hand.size();

        // Wahrscheinlichkeit eine bessere Karte legen zu müssen
        // TODO: here
        throw new RuntimeException();
    }

    @Override
    public boolean istSpielBeendet() {
        throw new UnsupportedOperationException();
    }
}
