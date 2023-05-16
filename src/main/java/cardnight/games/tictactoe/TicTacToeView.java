package cardnight.games.tictactoe;

import cardnight.PauseMenu;
import cardnight.games.SpielView;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;

import java.io.IOException;

public class TicTacToeView extends SpielView {
    public StackPane root;

    public void initialize() {

    }

    @Override
    protected void beendeSpiel() {

    }

    @Override
    public void pauseClick() throws IOException {

    }

    public static Pane loadScene() throws IOException {
        return new FXMLLoader(TicTacToeView.class.getResource("/cardnight/game-views/tictactoe-view.fxml")).load();
    }

    public void onPauseClick() throws IOException {
        root.getChildren().add(PauseMenu.loadScene());
    }
}
