package cardnight.games.witch;

import cardnight.games.Spiel;

import java.util.ArrayList;

public class Witch extends Spiel {
    private WitchSpieler [] spieler;
    private int anz;
    private int start;
    private WitchKarte[] stich;
    private ArrayList<WitchKarte> benutzt;
    private int amZug;
    private WitchKarte trumpf;

    private ArrayList<WitchKarte> komplett;

    public Witch(int anzahl) {
        anz = anzahl;
        spieler = new WitchSpieler[anzahl];
        for (int i = 1; i < anz; i++) {
            spieler[i] = new WitchGegner("Chris" + i, this);
        }
        spieler[0] = new WitchMensch("DU", this);
        start = (int)(Math.random() * anzahl);
        stich = new WitchKarte[anzahl];
        kartenSammeln();
    }

    public void game() {
        // Runden
        // i = Anzahl der Karten
        for (int i = 1; i <= 60/anz; i++) {
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
                // Der Stich wird dem Gewinner gegeben
                // Der Gewinner ist als Nächstes dran
                dran = stichGeben(dran);
                spieler[dran].stichBekommen();
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
        // Trumpf festlesen
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
                spieler[(start + i)%4].stiche++;
                return (start + i)%4;
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
                spieler[(start + s)%4].stiche++;
                return (start + s)%4;
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
            spieler[(start + s)%4].stiche++;
            return (start + s)%4;
        }

        //Es gab also nur Narren
        spieler[start].stiche++;
        return start;
    }

    public void punkteVerteilen() {
        //TODO: hier
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

        WitchFarbe[] farben = {WitchFarbe.ROT, WitchFarbe.BLAU, WitchFarbe.GELB, WitchFarbe.GRUEN};
        for (WitchFarbe f: farben) {
            for (int i = 0; i <= 14; i++) {
                komplett.add(new WitchKarte(f, i));
            }
        }
    }
}
