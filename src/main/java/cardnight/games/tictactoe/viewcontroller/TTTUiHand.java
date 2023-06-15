package cardnight.games.tictactoe.viewcontroller;

import cardnight.games.tictactoe.TicTacToeSpieler;
import javafx.scene.layout.HBox;

public abstract class TTTUiHand extends HBox {

    protected TicTacToeSpieler spieler;

    public TTTUiHand(TicTacToeSpieler spieler) {
        this.spieler = spieler;
        setMaxSize(Double.NEGATIVE_INFINITY, Double.NEGATIVE_INFINITY);
        updateUi();
    }

    public abstract void updateUi();
}
