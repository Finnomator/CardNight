package cardnight.games.ueno.viewcontroler;

import cardnight.Main;
import cardnight.games.ueno.UenoKarte;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.HashMap;

public class UenoRessourcen {

    public static Button erstelleStandardHandKarte(UenoKarte karte) {
        Button btn = new Button();
        ImageView kartenBild = new ImageView(UenoKartenBilder.karteZuBild(karte));

        btn.getStylesheets().add(Main.class.getResource("/cardnight/transparent-image-button.css").toExternalForm());
        btn.setGraphic(kartenBild);
        btn.setPadding(new Insets(0.001));
        btn.setOnAction((e) -> btn.fireEvent(new UenoKarteKlickEvent(UenoKarteKlickEvent.KLICK, karte)));
        return btn;
    }
}
