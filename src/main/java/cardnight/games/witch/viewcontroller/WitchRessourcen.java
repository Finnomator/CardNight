package cardnight.games.witch.viewcontroller;

import cardnight.Main;
import cardnight.games.witch.WitchKarte;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;

public class WitchRessourcen {

    public static Button erstelleStandardHandKarte(WitchKarte karte) {
        Button btn = new Button();

        btn.getStylesheets().add(Main.class.getResource("/cardnight/transparent-image-button.css").toExternalForm());
        btn.setGraphic(new ImageView(WitchKartenBilder.karteZuBild(karte)));
        btn.setPadding(new Insets(0.001));
        btn.setOnAction((e) -> btn.fireEvent(new WitchKartenKlickEvent(WitchKartenKlickEvent.KLICK, karte)));
        return btn;
    }

    public static ImageView erstelleKartenImageView(WitchKarte karte) {
        return new ImageView(WitchKartenBilder.karteZuBild(karte));
    }
}
