package cardnight.games.witch;

import cardnight.games.Spiel;

import java.util.ArrayList;

public class Witch extends Spiel {
    private final WitchSpieler[] spieler;
    protected int anz;
    protected int start;
    protected WitchKarte[] stich;
    protected ArrayList<WitchKarte> benutzt;
    protected int amZug;
    protected WitchKarte trumpf;

    private ArrayList<WitchKarte> komplett;

    public Witch(int anzahl) {
        anz = anzahl;
        spieler = new WitchSpieler[anzahl];
        for (int i = 1; i < anz; i++) {
            spieler[i] = new WitchGegner("Chris" + i, this);
        }
        spieler[0] = new WitchMensch("DU", this);
        start = (int) (Math.random() * anzahl);
        stich = new WitchKarte[anzahl];
        kartenSammeln();
    }

    public void game() {
        // Runden
        // i = Anzahl der Karten
        for (int i = 1; i <= 60 / anz; i++) {
            // Karten austeilen
            kartenAusteilen(i);
            update();
            // Schätzen
            for (int j = 0; j < 4; j++) {
                spieler[(i + j) % 4].schaetzen();
            }
            update();
            // Spielen
            // j = Anzahl übrige Karten
            int dran = start;
            amZug = dran;
            for (int j = i; j > 0; j--) {
                // Jeder Stich
                for (int k = 0; k < 4; k++) {
                    amZug = (dran + k) % 4;
                    update();
                    stich[k] = spieler[(i + k) % 4].spielen();
                }
                update();
                // Der Stich wird dem Gewinner gegeben.
                // Der Gewinner ist als Nächstes dran
                dran = stichGeben(dran);
                // Ablage wird geleert
                stich = new WitchKarte[anz];
                amZug = dran;
                update();
            }
            punkteVerteilen();
            update();
        }
        endeAnzeigen();
    }

    public void update() {
        //TODO: hier
        //TODO: Wenn alle Karten verteilt werden, darf nur die Farbe als Trumpf angezeigt werden
    }

    public void kartenAusteilen(int anzKarten) {
        // Trumpf festlegen
        int t = (int) (Math.random() * komplett.size());
        trumpf = komplett.get(t);

        if (60 / anzKarten != anz) {
            komplett.remove(t);
        }

        // Karten austeilen
        for (WitchSpieler s : spieler) {
            for (int i = 0; i < anzKarten; i++) {
                int k = (int) (Math.random() * komplett.size());
                s.hand.add(komplett.get(k));
                komplett.remove(k);
            }
        }
    }

    public int stichGeben(int start) {
        // Falls es einen Zauberer gab
        for (int i = 0; i < 4; i++) {
            if (stich[i].wert == 14) {
                spieler[(start + i) % 4].stiche++;
                return (start + i) % 4;
            }
        }

        // Höchste Trumpffarbe
        if (trumpf.wert != 0 && trumpf.wert != 14) { //Falls Trumpf eine weiße Karte ist
            int hoechste = 0;
            int s = 0;
            for (int i = 0; i < 4; i++) {
                if (stich[i].farbe == trumpf.farbe) {
                    if (stich[i].wert > hoechste) {
                        hoechste = stich[i].wert;
                        s = i;
                    }
                }
            }
            if (hoechste > 0) { //Falls es eine Trumpfkarte (keinen Narren) gegeben hat
                spieler[(start + s) % 4].stiche++;
                return (start + s) % 4;
            }
        }

        // Höchste Karte (nicht Trumpf)
        WitchFarbe f = null;
        int hoechste = 0;
        int s = 0;
        for (int i = 0; i < 4; i++) {
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
            spieler[(start + s) % 4].stiche++;
            return (start + s) % 4;
        }

        //Es gab also nur Narren
        spieler[start].stiche++;
        return start;
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

    public void endeAnzeigen() {
        //TODO: hier
    }

    public void kartenSammeln() {
        for (WitchSpieler s : spieler) {
            s.hand = null;
            s.stiche = 0;
        }
        stich = new WitchKarte[anz];
        benutzt.clear();
        komplett.clear();

        for (WitchFarbe f : WitchFarbe.values()) {
            for (int i = 0; i <= 14; i++) {
                komplett.add(new WitchKarte(f, i));
            }
        }
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
        if (trumpf.wert != 0 && trumpf.wert != 14) { //Falls der Trumpf nicht weiß ist
            if (k.farbe != trumpf.farbe) { //Falls die Karte nicht Trumpf ist
                besser += 13; //Die Trumpfkarten sind besser
                for (WitchKarte i : neu_benutzt) { //Alle schon benutzten Trümpfe werden abgezogen
                    if (i.farbe == trumpf.farbe && i.wert != 0 && i.wert != 14) {
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
        return 60 - benutzt.size() - hand.size() - anzKartenBesser(k, hand);
    }

    public double wahrscheinlichkeit(WitchKarte k, ArrayList<WitchKarte> hand) {
        //Gibt zurück, wie wahrscheinlich es ist, dass eine Karte bei einem Gegner durchläuft
        int anzKarten = hand.size();
        int uebrig = 60 - benutzt.size() - hand.size();

        //Wahrscheinlichkeit eine bessere Karte legen zu müssen
        //TODO: here
        return 0;
    }
}
