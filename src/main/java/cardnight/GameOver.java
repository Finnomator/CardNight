package cardnight;

import cardnight.games.ueno.viewcontroler.UenoView;
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

    public static StackPane loadScene() throws IOException {
        return new FXMLLoader(UenoView.class.getResource("/cardnight/game-over.fxml")).load();
    }

    public void onZumHauptmenueClick() {
        SoundPlayer.klickSound();
        ScreenController.activate("main-menu-view");
    }
}
