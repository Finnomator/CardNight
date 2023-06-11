package cardnight.games.witch.viewcontroller;

import cardnight.Main;
import cardnight.games.witch.WitchKarte;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.HashMap;

public class WitchRessourcen {
    private static final HashMap<String, Image> geladeneBilder = new HashMap<>();

    private static Image ladeBild(String pfad, double width, double height, boolean preserveRatio) {

        if (geladeneBilder.containsKey(pfad))
            return geladeneBilder.get(pfad);

        Image img = new Image(Main.class.getResourceAsStream("/cardnight/game-views/witch/images/" + pfad),
                width, height, preserveRatio, true);

        geladeneBilder.put(pfad, img);

        return img;
    }

    public static ImageView erstelleKartenRueckseite() {
        return new ImageView(ladeBild("Witch_Ruckseite.png", 0, Main.GEGNERKARTE_HOEHE, true));
    }

    public static Button erstelleStandardHandKarte(WitchKarte karte) {
        Button btn = new Button();
        btn.getStylesheets().add(Main.class.getResource("/cardnight/transparent-image-button.css").toExternalForm());
        ImageView imgView = erstelleKartenImageView(karte);
        imgView.setPreserveRatio(true);
        imgView.setFitWidth(Main.HANDKARTE_BREITE);
        btn.setGraphic(imgView);
        btn.setPadding(new Insets(0.001));
        btn.setOnAction((e) -> btn.fireEvent(new WitchKartenKlickEvent(WitchKartenKlickEvent.KLICK, karte)));
        return btn;
    }

    public static ImageView erstelleKartenImageView(WitchKarte karte) {
        return new ImageView(WitchKartenBilder.karteZuBild(karte));
    }
}
