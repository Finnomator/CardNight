package cardnight.games.tictactoe;

import cardnight.PauseMenu;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;

import java.io.IOException;

public class TicTacToeView {
    public StackPane root;

    public void initialize() {

    }

    public static Pane loadScene() throws IOException {
        return new FXMLLoader(TicTacToeView.class.getResource("/cardnight/game-views/tictactoe-view.fxml")).load();
    }

    public void onPauseClick() throws IOException {
        root.getChildren().add(PauseMenu.loadScene());
    }
}
