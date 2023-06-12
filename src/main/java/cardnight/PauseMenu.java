package cardnight;

import cardnight.games.Spiel;
import cardnight.games.ueno.viewcontroler.UenoView;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Slider;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
//import javafx.scene.web.WebView;

import java.awt.*;
import java.io.IOException;

public class PauseMenu {

    public GridPane root;
    public Text anleitungText;

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

}
