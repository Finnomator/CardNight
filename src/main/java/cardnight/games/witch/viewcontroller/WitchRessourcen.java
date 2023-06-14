package cardnight.games.witch.viewcontroller;

import cardnight.Main;
import cardnight.games.witch.WitchKarte;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;

public class WitchRessourcen {

    public static Button erstelleStandardHandKarte(WitchKarte karte) {
        Button btn = new Button();

        btn.getStylesheets().add(Main.class.getResource("/cardnight/styles/transparent-image-button.css").toExternalForm());
        ImageView img = new ImageView(WitchKartenBilder.karteZuBild(karte));
        img.setFitWidth(Main.HANDKARTE_BREITE);
        img.setPreserveRatio(true);
        btn.setGraphic(img);
        btn.setPadding(new Insets(0.001));
        btn.setOnAction((e) -> btn.fireEvent(new WitchKartenKlickEvent(WitchKartenKlickEvent.KLICK, karte)));
        return btn;
    }

    public static ImageView erstelleKartenImageView(WitchKarte karte) {
        ImageView img = new ImageView(WitchKartenBilder.karteZuBild(karte));
        img.setFitWidth(Main.HANDKARTE_BREITE);
        img.setPreserveRatio(true);
        return img;
    }
}
