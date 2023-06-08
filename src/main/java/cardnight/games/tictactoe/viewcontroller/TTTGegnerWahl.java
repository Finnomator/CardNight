package cardnight.games.tictactoe.viewcontroller;

import cardnight.Main;
import cardnight.ScreenController;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.StackPane;

import java.io.IOException;

public class TTTGegnerWahl {
    public void zuruckZumHauptmenuKlick() {
        ScreenController.activate("main-menu-view");
    }

    public void gegenComputerKlick() throws IOException {
        TicTacToeView.spielGegenComputer = true;
        ScreenController.activateNewPane(TicTacToeView.loadScene());
    }

    public void gegenMenschKlick() throws IOException {
        TicTacToeView.spielGegenComputer = false;
        ScreenController.activateNewPane(TicTacToeView.loadScene());
    }

    public static StackPane loadScene() throws IOException {
        return new FXMLLoader(Main.class.getResource("/cardnight/game-views/tictactoe/gegner-wahl.fxml")).load();
    }
}
