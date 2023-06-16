package cardnight.games.tictactoe.viewcontroller;

import cardnight.Main;
import cardnight.ScreenController;
import cardnight.SoundPlayer;
import cardnight.Tools;
import cardnight.games.Ressourcen;
import javafx.fxml.FXMLLoader;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

import java.io.IOException;

public class TTTGegnerWahl {

    public ImageView gegenMenschImgView;

    public void initialize() {
        if (Tools.random.nextInt(50) == 0) //🥚
            gegenMenschImgView.setImage(Ressourcen.einWuerdigerGegner);
    }

    public void zuruckZumHauptmenuKlick() {
        SoundPlayer.klickSound();
        ScreenController.show(ScreenController.hautptemue);
    }

    public void gegenComputerKlick() throws IOException {
        SoundPlayer.klickSound();
        TicTacToeView.spielGegenComputer = true;
        TicTacToeView.showScene();
    }

    public void gegenMenschKlick() throws IOException {
        SoundPlayer.klickSound();
        TicTacToeView.spielGegenComputer = false;
        TicTacToeView.showScene();
    }

    public static Pane loadScene() {
        try {
            return new FXMLLoader(Main.class.getResource("/cardnight/game-views/tictactoe/gegner-wahl.fxml")).load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void showScene() {
        ScreenController.show(ScreenController.tttGegnerWahl);
    }
}
