package cardnight.games.witch;

import java.util.ArrayList;

public class WitchKartenset {
    private static ArrayList<WitchKarte> komplett;  //alle Karten im Spiel

    public static void kartensetErstellen() {
        komplett = new ArrayList<>();
        for (WitchFarbe f : WitchFarbe.values()) {
            for (int i = 0; i <= 14; i++) {
                komplett.add(new WitchKarte(f, i));
            }
        }
    }

    public static WitchKarte gibZufaelligeKarte(boolean entferneAusStapel) {
        WitchKarte karte = komplett.get((int) (Math.random() * komplett.size()));

        if (entferneAusStapel)
            komplett.remove(karte);

        return karte;
    }

}
