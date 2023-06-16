package cardnight.games.tictactoe.viewcontroller;

import cardnight.Main;
import cardnight.games.tictactoe.TicTacToeSpieler;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;

public class TTTUiHand extends HBox {

    protected final TicTacToeSpieler spieler;
    public final Image handkartenBild;

    protected TTTUiHand(TicTacToeSpieler spieler) {
        this.spieler = spieler;
        handkartenBild = spieler.istX? TTTBilder.xHandkarte : TTTBilder.oHandkarte;

        setMaxSize(Double.NEGATIVE_INFINITY, Double.NEGATIVE_INFINITY);

        updateUi();
    }

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
