package cardnight.games.tictactoe.viewcontroller;

import cardnight.Main;
import cardnight.games.tictactoe.TicTacToeSpieler;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class TTTUiOHand extends TTTUiHand {

    public static final Image oHandkartenBild = new Image(Main.class.getResourceAsStream(
            "/cardnight/game-views/tictactoe/images/O_Handkarte.png"),
            0, 0, true, true);

    public TTTUiOHand(TicTacToeSpieler spieler) {
        super(spieler);
    }

    @Override
    public void updateUi() {

        setOpacity(spieler.istAmZug() ? 1 : 0.5);

        getChildren().clear();

        for (int i = 0; i < spieler.gibAnzahlHandKarten(); ++i) {
            ImageView img = new ImageView(oHandkartenBild);
            img.setPreserveRatio(true);
            img.setFitWidth(Main.HANDKARTE_BREITE);
            getChildren().add(img);
        }
    }
}
