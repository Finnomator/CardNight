package cardnight.games.witch;

import cardnight.games.Spieler;

import java.util.ArrayList;

public abstract class WitchSpieler extends Spieler {

    protected final Witch spiel;
    protected int anzahlErhaltenerStiche;
    protected int stichSchaetzung;
    protected ArrayList<WitchKarte> handkarten;
    protected final ArrayList<Integer> punkteProRunde;
    protected final ArrayList<Integer> stichSchaetzungenProRunde;

    public WitchSpieler(String name, Witch spiel) {
        super(name, spiel);
        this.spiel = spiel;
        handkarten = new ArrayList<>();
        punkteProRunde = new ArrayList<>();
        stichSchaetzungenProRunde = new ArrayList<>();
        stichSchaetzung = -1;
    }

    public abstract void schaetzen();
    public abstract WitchKarte spielen();

    public ArrayList<WitchKarte> gibHandkarten() {
        return handkarten;
    }

    public void handkarteHinzufuegen(WitchKarte karte) {
        System.out.println("\t\t" + name + " bekommt Karte: " + karte.datenAlsString());
        handkarten.add(karte);
    }

    public void clearHandkarten() {
        handkarten.clear();
    }

    public void fuegeStichHinzu() {
        System.out.println("\t\t" + name + " hat den Stich erhalten");
        anzahlErhaltenerStiche++;
    }

    public int gibAnzahlErzhaltenderStiche() {
        return anzahlErhaltenerStiche;
    }

    public void setzeAnzahlErhalteneSticheZurueck() {
        anzahlErhaltenerStiche = 0;
    }

    public void setzeAnzahlGeschaetzteSticheZurueck() { stichSchaetzung = -1;}

    public int gibStichSchaetzung() {
        return stichSchaetzung;
    }

    public int gibStichSchaetzung(int runde) {
        return stichSchaetzungenProRunde.get(runde);
    }

    @Override
    public void punkteHinzufuegen(int punkte) {
        punkteProRunde.add(punkte);
        super.punkteHinzufuegen(punkte);
    }

    public int gibPunkte(int runde) {
        return punkteProRunde.get(runde);
    }

    public int gibGesamtPunkte() {
        int summe = 0;
        for (int inRunde : punkteProRunde) {
            summe += inRunde;
        }
        return summe;
    }

    public ArrayList<WitchKarte> spielbareKarten() {
        ArrayList<WitchKarte> spielbar = new ArrayList<>();  //Alle Karten die spielbar sind

        // Farbzwang
        WitchFarbe zwang = null;

        for (WitchKarte imStapel : spiel.gibStich()) {  //von unten nach oben im Stich
            if (imStapel != null) {
                if (imStapel.wert != 0 && imStapel.wert != 14) {  //wenn die Karte nicht weiß ist
                    zwang = imStapel.farbe;  //Farbzwang ist die unterste Farbe
                    break;
                }
            }
        }

        if (zwang != null) {  //Falls es eine Farbe gibt
            for (WitchKarte k : handkarten) {
                if (k.farbe == zwang && k.wert != 0 && k.wert != 14) {
                    spielbar.add(k);
                }
            }

            if (! spielbar.isEmpty()) { //Falls es Karten dieser Farbe gibt
                for (WitchKarte k : handkarten) {
                    if (k.wert == 0 || k.wert == 14) {
                        spielbar.add(k);
                    }
                }
                return spielbar;
            }
        }

        //Der Spieler hat die Farbe also nicht
        //→ Alle Handkarten sind spielbar
        return handkarten;
    }

    @Override
    public boolean istAmZug() {
        return spiel.gibSpielerAmZug() == this;
    }
}
