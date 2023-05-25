package cardnight.games.witch;

import cardnight.games.Spiel;
import cardnight.games.witch.viewcontroller.WitchView;
import javafx.application.Platform;

import java.util.ArrayList;

public class Witch extends Spiel {
    private final WitchSpieler[] spieler;
    private final int anzahlSpieler;
    private final int start;
    private WitchKarte[] stich;
    private final ArrayList<WitchKarte> benutzt;
    private int spielerAmZug;
    private WitchKarte trumpfKarte;
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
        spielerAmZug = start;
        stich = new WitchKarte[anzahl];
        benutzt = new ArrayList<>();

        kartenSammeln();
    }

    public WitchKarte gibTrumpfKarte() {
        return trumpfKarte;
    }

    public int gibAnzahlSpieler() {
        return anzahlSpieler;
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

    public WitchSpieler gibSpielerAmZug() {
        return spieler[spielerAmZug];
    }

    public WitchKarte[] gibStich() {
        return stich;
    }

    public void game() {
        // Große Runde = alle Leute legen Karten ab, bis sie keine mehr haben.
        // Kleine Runde = alle Leute legen 1 Karte ab.
        int GameDelay = 1000;

        Thread t = new Thread(() -> {
            for (int kartenProSpieler = 1; kartenProSpieler <= kartenAnzahlInEinemSpiel / anzahlSpieler; kartenProSpieler++, ++rundenNummer) {

                System.out.println("---Große Runde " + rundenNummer + " mit " + kartenProSpieler + " Karte(n) pro Spieler---");

                int startSpielerDerKleinenRunde = (start + kartenProSpieler - 1) % anzahlSpieler;
                spielerAmZug = startSpielerDerKleinenRunde;

                kartenAusteilen(kartenProSpieler);
                spielerSchaetzen();

                // Spielen
                System.out.println("\tEs wird gespielt ");

                for (int uebrigeKarten = kartenProSpieler; uebrigeKarten > 0; uebrigeKarten--) {
                    // Jeder Stich

                    int kleineRundeNummer = kartenProSpieler - uebrigeKarten;
                    System.out.println("\t\t--Kleine Runde " + kleineRundeNummer + "--");

                    for (int i = 0; i < anzahlSpieler; i++) {
                        update();
                        spielerAmZug = (startSpielerDerKleinenRunde + i) % anzahlSpieler;
                        delay(GameDelay);
                        stich[i] = spieler[spielerAmZug].spielen();
                    }
                    update();
                    delay(GameDelay * 2);

                    // Der Stich wird dem Gewinner gegeben.
                    // Der Gewinner ist als Nächstes dran
                    startSpielerDerKleinenRunde = stichGeben(startSpielerDerKleinenRunde, stich, anzahlSpieler);
                    spieler[startSpielerDerKleinenRunde].fuegeStichHinzu();
                    // Ablage wird geleert
                    stich = new WitchKarte[anzahlSpieler];
                    spielerAmZug = startSpielerDerKleinenRunde;
                    update();
                }

                punkteVerteilen();
                update();
                delay(GameDelay * 2);
                kartenSammeln();
            }

            System.out.println("Das Spiel ist vorbei");
            Platform.runLater(observerView::beendeSpiel);
        });
        t.setDaemon(true);
        t.start();
    }

    private void spielerSchaetzen() {

        System.out.println("\tEs wird geschätzt");

        for (int j = 0; j < anzahlSpieler; j++)
            spieler[j].schaetzen();

        update();
    }

    public void update() {
        // TODO: Wenn alle 60 Karten verteilt werden, darf nur die Farbe als Trumpf angezeigt werden

        // System.out.println("***Ui Update***");
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

        System.out.println("\tTrumpf: " + trumpfKarte.datenAlsString());
        System.out.println("\tEs werden Karten ausgeteilt");
        System.out.println("\t\tStartspieler: " + spieler[spielerAmZug].name);

        // Karten austeilen
        for (WitchSpieler s : spieler)
            for (int i = 0; i < anzKarten; i++)
                s.handkarteHinzufuegen(WitchKartenset.gibZufaelligeKarte(true));

        update();
    }

    public int stichGeben(int startSpieler, WitchKarte [] derStich, int anzahlKarten) {
        // Falls es einen Zauberer gab
        for (int i = 0; i < anzahlKarten; i++) {
            if (derStich[i].istZauberer())
                return (startSpieler + i) % anzahlSpieler;
        }

        // Höchste Trumpffarbe
        if (!trumpfKarte.istNarr() && !trumpfKarte.istZauberer()) { // Falls Trumpf keine weiße Karte ist
            int hoechste = 0;
            int s = 0;

            for (int i = 0; i < anzahlKarten; i++) {
                if (derStich[i].farbe == trumpfKarte.farbe && derStich[i].wert > hoechste) {
                    hoechste = derStich[i].wert;
                    s = i;
                }
            }

            //Falls es eine Trumpfkarte (keinen Narren) gegeben hat
            if (hoechste > 0)
                return (startSpieler + s) % anzahlSpieler;
        }

        // Höchste Karte (nicht Trumpf)
        WitchFarbe farbe = null;
        int hoechste = 0;
        int s = 0;
        for (int i = 0; i < anzahlKarten; i++) {

            if (farbe == null && !derStich[i].istNarr())
                farbe = derStich[i].farbe;

            if (derStich[i].farbe == farbe && derStich[i].wert > hoechste) {
                hoechste = derStich[i].wert;
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

        System.out.println("\tEs werden Punkte verteilt");

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

    public ArrayList<WitchKarte> benutzteKarten(WitchKarte referenzKarte, ArrayList<WitchKarte> hand) {
        ArrayList<WitchKarte> neuBenutzt = new ArrayList<>(benutzt);
        neuBenutzt.addAll(hand);
        neuBenutzt.remove(referenzKarte);

        return neuBenutzt;
    }

    public int anzahlBessererKarten(WitchKarte referenzKarte, ArrayList<WitchKarte> neuBenutzt) {
        // Gibt zurück, wie viele bessere Karten noch im Spiel sind.

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

        // Alle schon benutzten Zauberer werden abgezogen
        anzahlBessererKarten = anzahlUebrigerZauberer(neuBenutzt);

        if (!trumpfKarte.istNarr() && !trumpfKarte.istZauberer()) { // Falls der Trumpf nicht weiß ist
            if (referenzKarte.farbe != trumpfKarte.farbe) { // Falls die Karte nicht Trumpf ist
                anzahlBessererKarten += anzahlUebrigerTruempfe(neuBenutzt); //Alle Trümpfe sind dann besser
            }
        }

        // Alle höheren Karten der gleichen Farbe sind anzahlBessererKarten
        anzahlBessererKarten += anzahlBessererKartenGleicherFarbe(referenzKarte, neuBenutzt);

        return anzahlBessererKarten;
    }

    public int anzahlBessererKartenGleicherFarbe(WitchKarte referenzKarte, ArrayList<WitchKarte> neuBenutzt) {
        // Alle höheren Karten der gleichen Farbe sind besser
        int anzahlBessererKarten = 13 - referenzKarte.wert;

        // Alle schon benutzten besseren gleichfarbigen Karten werden abgezogen
        for (WitchKarte i : neuBenutzt)
            if (i.farbe == referenzKarte.farbe && i.wert > referenzKarte.wert && ! i.istZauberer())
                anzahlBessererKarten--;

        return anzahlBessererKarten;
    }

    public int anzahlSchlechtererKartenGleicherFarbe(WitchKarte referenzKarte, ArrayList<WitchKarte> neuBenutzt) {
        // Alle kleineren Karten der gleichen Farbe sind schlechter
        int anzahlSchlechtererKarten = referenzKarte.wert - 1;

        // Alle schon benutzten besseren gleichfarbigen Karten werden abgezogen
        for (WitchKarte i : neuBenutzt)
            if (i.farbe == referenzKarte.farbe && i.wert < referenzKarte.wert && ! i.istZauberer())
                anzahlSchlechtererKarten--;

        return anzahlSchlechtererKarten;
    }
    public int anzahlUebrigerZauberer(ArrayList<WitchKarte> neuBenutzt) {
        // Die 4 Zauberer
        int anzahlZauberer = 4;

        // Alle schon benutzten Zauberer werden abgezogen
        for (WitchKarte karte : neuBenutzt) {
            if (karte.istZauberer())
                anzahlZauberer--;
        }

        return anzahlZauberer;
    }

    public int anzahlUebrigerNarren(ArrayList<WitchKarte> neuBenutzt) {
        // Die 4 Zauberer
        int anzahlNarren = 4;

        // Alle schon benutzten Zauberer werden abgezogen
        for (WitchKarte karte : neuBenutzt) {
            if (karte.istNarr())
                anzahlNarren--;
        }

        return anzahlNarren;
    }

    public int anzahlUebrigerTruempfe(ArrayList<WitchKarte> neuBenutzt) {
        int anzahlTruempfe = 13;

        // Alle schon benutzten Trümpfe werden abgezogen
        for (WitchKarte i : neuBenutzt) {
            if (i.farbe == trumpfKarte.farbe && ! i.istNarr() && ! i.istZauberer())
                anzahlTruempfe--;
        }

        return anzahlTruempfe;
    }

    public int anzahlSchlechtererKarten(WitchKarte referenzKarte, ArrayList<WitchKarte> neuBenutzt) {
        // Gibt zurück, wie viele schlechtere Karten noch im Spiel sind.
        // referenzKarte muss in hand sein

        // ALle übrigen Karten minus die besseren Karten davon
        return kartenAnzahlInEinemSpiel - neuBenutzt.size() - anzahlBessererKarten(referenzKarte, neuBenutzt);
    }


    public double wahrscheinlichkeit(WitchKarte referenzKarte, ArrayList<WitchKarte> hand, double wahrscheinlichekeitAnzufangen) {
        // Gibt zurück, wie wahrscheinlich es ist, dass eine Karte bei einem Gegner durchläuft
        if (referenzKarte.istZauberer()) {
            return 1;
        }
        if (referenzKarte.istNarr()) {
            return 0;
        }

        int anzKarten = hand.size();
        int uebrig = kartenAnzahlInEinemSpiel - benutzt.size() - hand.size();
        ArrayList<WitchKarte> neuBenutzt = benutzteKarten(referenzKarte, hand);

        // Wahrscheinlichkeiten
        double hatBessereKartenDerFarbe = 1 - Math.pow((1 - (double) anzahlBessererKartenGleicherFarbe(referenzKarte, neuBenutzt) / uebrig), anzKarten);
        double hatSchlechtereKarteDerFarbe = 1 - Math.pow((1 - (double) anzahlSchlechtererKartenGleicherFarbe(referenzKarte, neuBenutzt) / uebrig), anzKarten);
        double hatZauberer = 1 - Math.pow(1 - (double) anzahlUebrigerZauberer(neuBenutzt) / uebrig, anzKarten);
        double hatNarren = 1 - Math.pow(1 - (double) anzahlUebrigerNarren(neuBenutzt) / uebrig, anzKarten);
        double hatTruempfe = 1 - Math.pow(1 - (double) anzahlUebrigerTruempfe(neuBenutzt) / uebrig, anzKarten);

        if (referenzKarte.farbe == trumpfKarte.farbe) {
            hatTruempfe = 0;
        }

        // ungefähre Wahrscheinlichkeiten
        double mussBessereLegen = hatBessereKartenDerFarbe * (1 - hatSchlechtereKarteDerFarbe) * (1 - hatNarren);
        double mussSchlechtereLegen = hatSchlechtereKarteDerFarbe * (1 - hatBessereKartenDerFarbe) * (1 - hatZauberer) +
                (1 - hatBessereKartenDerFarbe) * (1 - hatSchlechtereKarteDerFarbe) * (1 - hatTruempfe) * (1 - hatZauberer);
        double kannEntscheiden = 1 - mussBessereLegen - mussSchlechtereLegen;

        double wahrscheinlichkeitNichtZuStehlen = 0.2;
        double wahrscheinlichkeitBeiAnfang = mussSchlechtereLegen + wahrscheinlichkeitNichtZuStehlen * kannEntscheiden;

        double wahrscheinlichkeitBeiNichtAnfang = 0;
        if (referenzKarte.farbe == trumpfKarte.farbe) {
            //Die Wahrscheinlichkeit bei Trümpfen ist viel höher, wenn sie nicht anfangen
            wahrscheinlichkeitBeiNichtAnfang = 1 - (1 - wahrscheinlichkeitBeiAnfang) / 2;
        }

        return wahrscheinlichekeitAnzufangen * wahrscheinlichkeitBeiAnfang + (1 - wahrscheinlichekeitAnzufangen) * wahrscheinlichkeitBeiNichtAnfang;

    }

    @Override
    public boolean istSpielBeendet() {
        throw new UnsupportedOperationException();
    }
}
