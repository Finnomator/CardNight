package cardnight.games.witch;

import cardnight.Tools;

import java.util.ArrayList;

public class WitchKartenset {
    private static ArrayList<WitchKarte> alleKarten;  // Alle Karten in einem Spiel

    public static void kartensetErstellen() {
        alleKarten = new ArrayList<>();
        for (WitchFarbe f : WitchFarbe.values())
            for (int i = 0; i <= 14; i++)
                alleKarten.add(new WitchKarte(f, i));
    }

    public static WitchKarte gibZufaelligeKarte(boolean entferneAusStapel) {
        WitchKarte karte = alleKarten.get(Tools.random.nextInt(alleKarten.size()));

        if (entferneAusStapel)
            alleKarten.remove(karte);

        return karte;
    }
}
