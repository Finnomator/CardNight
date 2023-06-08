package cardnight.games.ueno;

import java.util.Arrays;
import java.util.Collections;

public class UenoKartenset {
    public static final UenoKarte[] UENO_KARTEN = new UenoKarte[108];

    private static void kartenErstellen() {
        int idx = 0;
        for (UenoFarbe farbe : UenoFarbe.values()) {
            for (int i = 0; i < 10; ++i)
                UENO_KARTEN[idx++] = new UenoKarte(i, farbe);

            for (int i = 1; i < 10; ++i)
                UENO_KARTEN[idx++] = new UenoKarte(i, farbe);

            UENO_KARTEN[idx++] = new UenoKarte(farbe, UenoKartenArt.PLUS_ZWEI);
            UENO_KARTEN[idx++] = new UenoKarte(farbe, UenoKartenArt.PLUS_ZWEI);
            UENO_KARTEN[idx++] = new UenoKarte(farbe, UenoKartenArt.RICHTUNGSWECHSEL);
            UENO_KARTEN[idx++] = new UenoKarte(farbe, UenoKartenArt.RICHTUNGSWECHSEL);
            UENO_KARTEN[idx++] = new UenoKarte(farbe, UenoKartenArt.AUSSETZEN);
            UENO_KARTEN[idx++] = new UenoKarte(farbe, UenoKartenArt.AUSSETZEN);
        }

        for (int i = 0; i < 4; ++i) {
            UENO_KARTEN[idx++] = new UenoKarte(null, UenoKartenArt.PLUS_VIER);
            UENO_KARTEN[idx++] = new UenoKarte(null, UenoKartenArt.FARBWAHL);
        }
    }

    public static UenoKarte[] erstelleGemischtesSet() {
        kartenErstellen();
        UenoKarte[] shuffled = new UenoKarte[108];
        System.arraycopy(UENO_KARTEN, 0, shuffled, 0, 108);
        Collections.shuffle(Arrays.asList(shuffled));
        return shuffled;
    }
}

