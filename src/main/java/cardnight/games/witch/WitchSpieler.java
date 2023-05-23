package cardnight.games.witch;

import cardnight.games.Spieler;

import java.util.ArrayList;

public abstract class WitchSpieler extends Spieler {

    protected Witch spiel;
    protected int anzahlErhaltenerStiche;
    protected int stichSchaetzung;
    protected ArrayList<WitchKarte> handkarten;
    protected ArrayList<Integer> punkteProRunde;

    public WitchSpieler(String name, Witch spiel) {
        super(name, spiel);
        this.spiel = spiel;
        handkarten = new ArrayList<>();
        punkteProRunde = new ArrayList<>();
    }

    public abstract void schaetzen();
    public abstract WitchKarte spielen();

    public ArrayList<WitchKarte> gibHandkarten() {
        return handkarten;
    }

    public void handkarteHinzufuegen(WitchKarte karte) {
        System.out.println(name + " bekommt Karte: " + karte.datenAlsString());
        handkarten.add(karte);
    }

    public void clearHandkarten() {
        handkarten.clear();
    }

    public void fuegeStichHinzu() {
        anzahlErhaltenerStiche++;
    }

    public int gibAnzahlErzhaltenderStiche() {
        return anzahlErhaltenerStiche;
    }

    public void setzeAnzahlErhalteneSticheZurueck() {
        anzahlErhaltenerStiche = 0;
    }

    public int gibStichSchaetzung() {
        return stichSchaetzung;
    }

    @Override
    public void punkteHinzufuegen(int punkte) {
        punkteProRunde.add(punkte);
        super.punkteHinzufuegen(punkte);
    }

    public int gibPunkte(int runde) {
        return punkteProRunde.get(runde);
    }

    public ArrayList<WitchKarte> spielbareKarten() {
        ArrayList<WitchKarte> spielbar = new ArrayList<>();  //Alle Karten die spielbar sind

        // Farbzwang
        WitchFarbe zwang = null;

        for (WitchKarte imStapel : spiel.stich) {  //von unten nach oben im Stich
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
}
