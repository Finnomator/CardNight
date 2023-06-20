package cardnight;

import cardnight.games.tictactoe.TicTacToe;
import cardnight.games.tictactoe.viewcontroller.TicTacToeView;
import cardnight.games.ueno.Ueno;
import cardnight.games.ueno.viewcontroller.UenoView;
import cardnight.games.witch.Witch;
import cardnight.games.witch.viewcontroller.WitchView;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;

import java.io.IOException;

public class GameOver {
    public Label nachrichtText;
    private static String nachricht;

    public void initialize() {
        nachrichtText.setText(nachricht);
    }

    public static void setzeNachricht(String text) {
        nachricht = text;
    }

    public static StackPane loadScene() {
        try {
            return new FXMLLoader(Main.class.getResource("/cardnight/game-over.fxml")).load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void onZumHauptmenueClick() {
        SoundPlayer.klickSound();
        MainMenuView.showScene();
    }

    public void onNochmalSpielenClick() throws IOException {
        SoundPlayer.klickSound();

        if (Main.gibAktuellGespieltesSpiel() instanceof TicTacToe)
            TicTacToeView.showScene();
        else if (Main.gibAktuellGespieltesSpiel() instanceof Ueno)
            UenoView.showScene();
        else if (Main.gibAktuellGespieltesSpiel() instanceof Witch)
            WitchView.showScene();
    }
}
