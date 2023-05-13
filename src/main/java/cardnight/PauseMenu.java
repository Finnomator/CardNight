package cardnight;

import cardnight.games.ueno.viewcontroler.UenoView;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Slider;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;

import java.io.IOException;

public class PauseMenu {

    public Slider soundVolumeSlider;
    public StackPane root;

    public void initialize() {
        soundVolumeSlider.setValue(Main.SOUND_VOLUME);
    }

    public void onFortsetzenClick() {
        ((Pane) root.getParent()).getChildren().remove(root);
    }

    public void onZumHauptmenueClick() {
        ScreenController.activate("main-menu-view");
    }

    public static StackPane loadScene() throws IOException {
        return new FXMLLoader(UenoView.class.getResource("/cardnight/pause-menu.fxml")).load();
    }

    public void onSoundSliderMouseClick() {
        Main.SOUND_VOLUME = soundVolumeSlider.getValue();
        System.out.println("Sound volume is now " + Main.SOUND_VOLUME);
    }
}
