package cardnight.games.tictactoe.viewcontroller;

import cardnight.Main;
import cardnight.games.tictactoe.TicTacToeSpieler;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class TTTUiXHand extends TTTUiHand {
    public static final Image xHandkartenBild = new Image(Main.class.getResourceAsStream(
            "/cardnight/game-views/tictactoe/images/X_Handkarte.png"),
            Main.HANDKARTE_BREITE, 0, true, true);

    public TTTUiXHand(TicTacToeSpieler spieler) {
        super(spieler);
    }

    @Override
    public void updateUi() {

        setOpacity(spieler.istAmZug() ? 1 : 0.5);

        getChildren().clear();

        for (int i = 0; i < spieler.gibAnzahlHandKarten(); ++i) {
            ImageView img = new ImageView(xHandkartenBild);
            img.setPreserveRatio(true);
            img.setFitWidth(Main.HANDKARTE_BREITE);
            getChildren().add(img);
        }
    }
}
