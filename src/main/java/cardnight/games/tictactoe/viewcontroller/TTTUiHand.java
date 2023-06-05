package cardnight.games.tictactoe.viewcontroller;

import cardnight.games.tictactoe.TicTacToeSpieler;

public abstract class TTTUiHand {

    protected TicTacToeSpieler spieler;

    public abstract void uiErstellen(TicTacToeSpieler spieler);
    public abstract void updateUi();
}
