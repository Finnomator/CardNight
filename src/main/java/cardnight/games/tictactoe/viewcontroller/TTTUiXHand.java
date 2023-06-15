package cardnight.games.tictactoe.viewcontroller;

import cardnight.Main;
import cardnight.games.tictactoe.TicTacToeSpieler;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class TTTUiXHand extends TTTUiHand {

    public TTTUiXHand(TicTacToeSpieler spieler) {
        super(spieler);
    }

    @Override
    public void updateUi() {

        setOpacity(spieler.istAmZug() ? 1 : 0.5);

        getChildren().clear();

        for (int i = 0; i < spieler.gibAnzahlHandKarten(); ++i) {
            ImageView img = new ImageView(handkartenBild);
            img.setPreserveRatio(true);
            img.setFitWidth(Main.HANDKARTE_BREITE);
            getChildren().add(img);
        }
    }
}
