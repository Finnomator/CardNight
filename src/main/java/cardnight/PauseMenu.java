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
import javafx.scene.web.WebView;

import java.awt.*;
import java.io.IOException;

public class PauseMenu {

    public Slider soundVolumeSlider;
    public GridPane root;
    public Text anleitungText;

    public void initialize() {
        soundVolumeSlider.setValue(Main.SOUND_VOLUME);
        Spiel spiel = Main.gibAktuellGespieltesSpiel();
        if (spiel != null)
            anleitungText.setText(spiel.gibAnleitung());
    }

    public void onFortsetzenClick() {
        ((Pane) root.getParent()).getChildren().remove(root);
    }

    public void onZumHauptmenueClick() {
        ScreenController.activate("main-menu-view");
    }

    public static Node loadScene() throws IOException {
        return new FXMLLoader(Main.class.getResource("/cardnight/pause-menu.fxml")).load();
    }

    public void onSoundSliderMouseClick() {
        Main.SOUND_VOLUME = soundVolumeSlider.getValue();
        System.out.println("Sound volume is now " + Main.SOUND_VOLUME);
    }
}
