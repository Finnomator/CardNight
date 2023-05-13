package cardnight;

import cardnight.games.ueno.viewcontroler.UenoView;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;

import java.io.IOException;

public class GameOver {
    public Text nachrichtText;

    public void initialize() {

    }

    public void setzeNachricht(String nachricht) {
        nachrichtText.setText(nachricht);
    }

    public static StackPane loadScene() throws IOException {
        return new FXMLLoader(UenoView.class.getResource("/cardnight/game-over.fxml")).load();
    }

    public void onZumHauptmenueClick() {
        ScreenController.activate("main-menu-view");
    }
}
