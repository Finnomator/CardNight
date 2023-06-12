package cardnight.games.tictactoe.viewcontroller;

import cardnight.Main;
import cardnight.ScreenController;
import cardnight.SoundPlayer;
import cardnight.Tools;
import cardnight.games.Ressourcen;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;

import java.io.IOException;

public class TTTGegnerWahl {

    public ImageView gegenMenschImgView;

    public void initialize() {
        if (Tools.random.nextInt(100) == 0) //🥚
            gegenMenschImgView.setImage(Ressourcen.einWuerdigerGegner);
    }

    public void zuruckZumHauptmenuKlick() {
        SoundPlayer.klickSound();
        ScreenController.activate("main-menu-view");
    }

    public void gegenComputerKlick() throws IOException {
        SoundPlayer.klickSound();
        TicTacToeView.spielGegenComputer = true;
        ScreenController.activateNewPane(TicTacToeView.loadScene());
    }

    public void gegenMenschKlick() throws IOException {
        SoundPlayer.klickSound();
        TicTacToeView.spielGegenComputer = false;
        ScreenController.activateNewPane(TicTacToeView.loadScene());
    }

    public static StackPane loadScene() throws IOException {
        return new FXMLLoader(Main.class.getResource("/cardnight/game-views/tictactoe/gegner-wahl.fxml")).load();
    }
}
