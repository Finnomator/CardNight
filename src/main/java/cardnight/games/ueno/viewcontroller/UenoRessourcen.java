package cardnight.games.ueno.viewcontroller;

import cardnight.Main;
import cardnight.games.ueno.UenoKarte;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;

public class UenoRessourcen {

    public static Button erstelleStandardHandKarte(UenoKarte karte) {
        Button btn = new Button();
        ImageView kartenBild = new ImageView(UenoKartenBilder.karteZuBild(karte));

        btn.getStylesheets().add(Main.class.getResource("/cardnight/styles/transparent-image-button.css").toExternalForm());
        btn.setGraphic(kartenBild);
        btn.setPadding(new Insets(0.001));
        btn.setOnAction((e) -> btn.fireEvent(new UenoKarteKlickEvent(UenoKarteKlickEvent.KLICK, karte)));
        return btn;
    }
}
