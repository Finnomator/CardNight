package cardnight.games.witch.viewcontroller;

import cardnight.Main;
import cardnight.Tools;
import cardnight.games.Ressourcen;
import cardnight.games.witch.WitchFarbe;
import cardnight.games.witch.WitchKarte;
import javafx.scene.image.Image;

import java.util.HashMap;
import java.util.Random;

public class WitchKartenBilder {

    private static final HashMap<WitchFarbe, Image[]> farbigeKarten = new HashMap<>(52);
    private static final HashMap<WitchFarbe, Image> hexenKarten = new HashMap<>(4);
    private static final HashMap<WitchFarbe, Image> narrenKarten = new HashMap<>(4);
    public static final Image witchKartenRueckseite = ladeBild("Witch_Ruckseite.png", 0, Main.GEGNERKARTE_HOEHE);
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
        return ladeBild(pfad, Main.HANDKARTE_BREITE, 0);
    }

    public static Image ladeBild(String pfad, double breite, double hoehe) {
        return new Image(Main.class.getResourceAsStream("/cardnight/game-views/witch/images/" + pfad), breite, hoehe, true, true);
    }

    public static Image karteZuBild(WitchKarte karte) {
        assert bilderWurdenGeladen;

        if (karte.istZauberer())
            return hexenKarten.get(karte.farbe);

        if (karte.istNarr())
            return narrenKarten.get(karte.farbe);

        if (karte.wert == 1 && karte.farbe == WitchFarbe.GELB && Tools.random.nextInt(10) == 0) // What could this mean? ㄟ( ▔, ▔ )ㄏ
            return Ressourcen.dieDiebin;

        return farbigeKarten.get(karte.farbe)[karte.wert - 1];
    }
}
