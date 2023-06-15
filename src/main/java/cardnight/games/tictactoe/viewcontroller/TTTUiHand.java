package cardnight.games.tictactoe.viewcontroller;

import cardnight.games.tictactoe.TicTacToeSpieler;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;

public abstract class TTTUiHand extends HBox {

    protected final TicTacToeSpieler spieler;
    public final Image handkartenBild;

    protected TTTUiHand(TicTacToeSpieler spieler) {
        this.spieler = spieler;
        handkartenBild = spieler.istX? TTTBilder.xHandkarte : TTTBilder.oHandkarte;

        setMaxSize(Double.NEGATIVE_INFINITY, Double.NEGATIVE_INFINITY);

        updateUi();
    }

    public abstract void updateUi();
}
