package cardnight.games.witch.viewcontroller;

import cardnight.Main;
import cardnight.games.witch.WitchFarbe;
import cardnight.games.witch.WitchKarte;
import javafx.scene.image.Image;

import java.util.HashMap;

public class WitchKartenBilder {

    private static final HashMap<WitchFarbe, Image[]> farbigeKarten = new HashMap<>(52);
    private static final HashMap<WitchFarbe, Image> hexenKarten = new HashMap<>(4);
    private static final HashMap<WitchFarbe, Image> narrenKarten = new HashMap<>(4);

    private static boolean bilderWurdenGeladen;

    public static void bilderLaden() {

        if (bilderWurdenGeladen)
            return;

        bilderWurdenGeladen = true;

        for (WitchFarbe farbe : WitchFarbe.values()) {
            String farbeString = farbe.toString().toLowerCase();

            Image[] bilderEinerFarbe = new Image[13];

            for (int i = 0; i < 13; ++i)
                bilderEinerFarbe[i] = ladeBild(farbeString + "/Witch_" + farbeString + "_" + (i + 1) + ".png");

            farbigeKarten.put(farbe, bilderEinerFarbe);

            hexenKarten.put(farbe, ladeBild("hexen/Witch_" + farbeString + "_Hexe.png"));
            narrenKarten.put(farbe, ladeBild("narren/Witch_" + farbeString + "_Narr.png"));
        }
    }

    private static Image ladeBild(String pfad) {
        return new Image(Main.class.getResourceAsStream("/cardnight/game-views/witch/images/" + pfad));
    }

    public static Image karteZuBild(WitchKarte karte) {
        assert bilderWurdenGeladen;

        if (karte.istZauberer())
            return hexenKarten.get(karte.farbe);

        if (karte.istNarr())
            return narrenKarten.get(karte.farbe);

        return farbigeKarten.get(karte.farbe)[karte.wert - 1];
    }
}
