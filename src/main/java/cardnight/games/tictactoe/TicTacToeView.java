package cardnight.games.tictactoe;

import cardnight.ScreenController;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.Pane;

import java.io.IOException;

public class TicTacToeView {
    public void onBackClick() {
        ScreenController.activate("main-menu-view");
    }

    public static Pane loadScene() throws IOException {
        return new FXMLLoader(TicTacToeView.class.getResource("/cardnight/game-views/tictactoe-view.fxml")).load();
    }
}
