package cardnight;

import cardnight.games.tictactoe.TicTacToeView;
import cardnight.games.ueno.UenoView;

import java.io.IOException;

public class MainMenuView {

    public void onTicTacToeClick() throws IOException {
        ScreenController.activateNewPane(TicTacToeView.loadScene());
    }

    public void onExitClick() {
        System.exit(0);
    }

    public void onUenoClick() throws IOException {
        ScreenController.activateNewPane(UenoView.loadScene());
    }
}
