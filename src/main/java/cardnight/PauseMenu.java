package cardnight;

import cardnight.games.Spiel;
import cardnight.games.tictactoe.TicTacToe;
import cardnight.games.tictactoe.viewcontroller.TicTacToeView;
import cardnight.games.ueno.Ueno;
import cardnight.games.ueno.viewcontroler.UenoView;
import cardnight.games.witch.Witch;
import cardnight.games.witch.viewcontroller.WitchView;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
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
        ScreenController.activate("main-menu-view");
    }

    public static Node loadScene() throws IOException {
        return new FXMLLoader(Main.class.getResource("/cardnight/pause-menu.fxml")).load();
    }

    public void onErneutSpielenClick() throws IOException {
        SoundPlayer.klickSound();

        if (Main.gibAktuellGespieltesSpiel() instanceof TicTacToe)
            ScreenController.activateNewPane(TicTacToeView.loadScene());
        else if (Main.gibAktuellGespieltesSpiel() instanceof Ueno)
            ScreenController.activateNewPane(UenoView.loadScene());
        else if (Main.gibAktuellGespieltesSpiel() instanceof Witch)
            ScreenController.activateNewPane(WitchView.loadScene());
    }
}
