package cardnight.games.ueno.viewcontroler;

import cardnight.Main;
import cardnight.games.ueno.UenoKarte;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.HashMap;

public class UenoRessourcen {

    private static final HashMap<String, Image> geladeneBilder = new HashMap<>();

    public static Image ladeBild(String pfad, double width, double height, boolean preserveRatio) {

        if (geladeneBilder.containsKey(pfad))
            return geladeneBilder.get(pfad);

        Image img = new Image(Main.class.getResourceAsStream("/cardnight/game-views/ueno/images/" + pfad),
                width, height, preserveRatio, true);

        geladeneBilder.put(pfad, img);

        return img;
    }

    public static ImageView erstelleKartenRueckseite() {
        ImageView kartenRueckseite = new ImageView(ladeBild("UNO_Rückseite.png", 0, Main.GEGNERKARTE_HOEHE, true));
        return kartenRueckseite;
    }

    public static Button erstelleStandardHandKarte(UenoKarte karte) {
        Button btn = new Button();
        ImageView kartenBild = new ImageView(UenoKartenBilder.karteZuBild(karte));
        kartenBild.setPreserveRatio(true);
        kartenBild.setFitWidth(Main.HANDKARTE_BREITE);

        btn.getStylesheets().add(Main.class.getResource("/cardnight/transparent-image-button.css").toExternalForm());
        btn.setGraphic(kartenBild);
        btn.setPadding(new Insets(0.001));
        btn.setOnAction((e) -> btn.fireEvent(new UenoKarteKlickEvent(UenoKarteKlickEvent.KLICK, karte)));
        return btn;
    }
}
