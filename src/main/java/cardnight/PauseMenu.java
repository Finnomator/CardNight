package cardnight;

import cardnight.games.Spiel;
import cardnight.games.tictactoe.TicTacToe;
import cardnight.games.tictactoe.viewcontroller.TicTacToeView;
import cardnight.games.ueno.Ueno;
import cardnight.games.ueno.viewcontroler.UenoView;
import cardnight.games.witch.Witch;
import cardnight.games.witch.viewcontroller.WitchView;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;

import java.io.IOException;

public class PauseMenu {

    public GridPane root;
    public Label anleitungText;

    public void initialize() {
        Spiel spiel = Main.gibAktuellGespieltesSpiel();
        if (spiel != null)
            anleitungText.setText(spiel.gibAnleitung());
    }

    public void onFortsetzenClick() {
        SoundPlayer.klickSound();
        ((Pane) root.getParent()).getChildren().remove(root);
    }

    public void onZumHauptmenueClick() {
        SoundPlayer.klickSound();
        ScreenController.show(ScreenController.hautptemue);
    }

    public void onErneutSpielenClick() throws IOException {
        SoundPlayer.klickSound();

        if (Main.gibAktuellGespieltesSpiel() instanceof TicTacToe)
            TicTacToeView.showScene();
        else if (Main.gibAktuellGespieltesSpiel() instanceof Ueno)
            UenoView.showScene();
        else if (Main.gibAktuellGespieltesSpiel() instanceof Witch)
            WitchView.showScene();
    }

    public static Pane loadScene() {
        try {
            return new FXMLLoader(Main.class.getResource("pause-menu.fxml")).load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
