package cardnight.games.witch;

import cardnight.games.Spieler;

import java.util.ArrayList;

public abstract class WitchSpieler extends Spieler {

    protected Witch spiel;
    protected int punkte;
    protected int stiche;
    protected int schaetzung;
    protected ArrayList<WitchKarte> hand;

    public WitchSpieler(String name, Witch spiel) {
        super(name, spiel);
        this.spiel = spiel;
        hand = new ArrayList<>();
    }

    public abstract void schaetzen();
    public abstract WitchKarte spielen();

    public ArrayList<WitchKarte> getSpielbar() {
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
            for (WitchKarte k : hand) {
                if (k.farbe == zwang && k.wert != 0 && k.wert != 14) {
                    spielbar.add(k);
                }
            }

            if (! spielbar.isEmpty()) { //Falls es Karten dieser Farbe gibt
                for (WitchKarte k : hand) {
                    if (k.wert == 0 || k.wert == 14) {
                        spielbar.add(k);
                    }
                }
                return spielbar;
            }
        }

        //Der Spieler hat die Farbe also nicht
        //→ Alle Handkarten sind spielbar
        return hand;
    }
}
