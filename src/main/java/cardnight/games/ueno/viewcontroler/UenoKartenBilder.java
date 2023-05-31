package cardnight.games.ueno.viewcontroler;

import cardnight.Main;
import cardnight.games.ueno.UenoFarbe;
import cardnight.games.ueno.UenoKarte;
import cardnight.games.ueno.UenoKartenArt;
import javafx.scene.image.Image;

import java.util.HashMap;

public class UenoKartenBilder {
    public static HashMap<UenoFarbe, Image[]> zahlenKarten;

    public static void ladeBilder() {

        zahlenKarten = new HashMap<>();

        for (UenoFarbe farbe : UenoFarbe.values()) {

            // TODO: Das Ändern, sobald alle Bilder da sind
            if (farbe != UenoFarbe.ROT)
                continue;

            Image[] karten = new Image[10];
            for (int i = 0; i < 10; ++i)
                karten[i] = new Image(Main.class.getResourceAsStream(
                        "/cardnight/game-views/ueno/images/UNO_" + i + "_" + farbe.toString().toLowerCase() + ".png"),
                        56.8 * 2, 82.2 * 2, true, true);
            zahlenKarten.put(farbe, karten);
        }
    }

    public static Image karteZuBild(UenoKarte karte) {
        if (karte.art == UenoKartenArt.ZAHL)
            return zahlenKarten.get(karte.farbe)[karte.wert];

        throw new RuntimeException();
    }
}
