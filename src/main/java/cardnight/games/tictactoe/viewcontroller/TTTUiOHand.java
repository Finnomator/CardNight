package cardnight.games.tictactoe.viewcontroller;

import cardnight.Main;
import cardnight.games.tictactoe.TicTacToeSpieler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;

import java.io.IOException;

public class TTTUiOHand extends TTTUiHand {
    public HBox kartenBox;

    public static final Image oHandkartenBild = new Image(Main.class.getResourceAsStream(
            "/cardnight/game-views/tictactoe/images/O_Handkarte.png"),
            Main.HANDKARTE_BREITE, 0, true, true);

    @Override
    public void uiErstellen(TicTacToeSpieler spieler) {
        this.spieler = spieler;
        updateUi();
    }

    @Override
    public void updateUi() {

        kartenBox.setDisable(!spieler.istAmZug());

        kartenBox.getChildren().clear();

        for (int i = 0; i < spieler.gibAnzahlHandKarten(); ++i) {
            Button btn = new Button();
            btn.setPadding(new Insets(0.001));
            btn.getStylesheets().add(getClass().getResource("/cardnight/transparent-image-button.css").toExternalForm());
            btn.setGraphic(new ImageView(oHandkartenBild));
            kartenBox.getChildren().add(btn);
        }
    }
}
