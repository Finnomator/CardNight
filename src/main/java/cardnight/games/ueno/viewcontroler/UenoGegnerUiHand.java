package cardnight.games.ueno.viewcontroler;

import cardnight.games.ueno.UenoKarte;
import cardnight.games.ueno.UenoSpieler;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.HBox;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;

import java.io.IOException;

public class UenoGegnerUiHand extends UenoUiHand {
    public HBox kartenBox;
    public Text nameText;
    public Circle turnIndicator;

    @Override
    public void uiErstellen(UenoSpieler spieler) {
        this.spieler = spieler;
        nameText.setText(spieler.name);
        updateUi();
    }

    @Override
    public void updateUi() {

        turnIndicator.setVisible(spieler.istAmZug());

        kartenBox.getChildren().clear();
        for (UenoKarte ignored : spieler.gibHandkarten()) {
            FXMLLoader loader;
            loader = new FXMLLoader(getClass().getResource("/cardnight/game-views/ueno/karten-rueckseite.fxml"));
            try {
                kartenBox.getChildren().add(loader.load());
            } catch (IOException ex) {
                throw new RuntimeException();
            }
        }
    }
}
