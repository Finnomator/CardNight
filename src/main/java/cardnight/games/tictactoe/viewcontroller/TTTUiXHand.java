package cardnight.games.tictactoe.viewcontroller;

import cardnight.Main;
import cardnight.games.tictactoe.TicTacToeSpieler;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;

public class TTTUiXHand extends TTTUiHand {
    public HBox kartenBox;

    public static final Image xHandkartenBild = new Image(Main.class.getResourceAsStream(
            "/cardnight/game-views/tictactoe/images/X_Handkarte.png"),
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

        for (int i = 0; i < spieler.gibAnzahlHandKarten(); ++i)
            kartenBox.getChildren().add(new ImageView(xHandkartenBild));
    }
}
