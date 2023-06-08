package cardnight;

import javafx.scene.control.Button;
import javafx.scene.control.Slider;
import javafx.scene.input.MouseEvent;

public class SoundMenu {
    public Button soundEnableButton;
    public Slider musicVolumeSlider;

    public void initialize() {
        musicVolumeSlider.setValue(Main.getMusicVolume() * 100.0);
    }

    public void soundEnableKlick() {
        Main.enableSound = !Main.enableSound;
        soundEnableButton.setText(Main.enableSound? "\uD83D\uDD0A" : "\uD83D\uDD07");
    }

    public void musicSliderMouseClick(MouseEvent mouseEvent) {
        Main.setMusicVolume(musicVolumeSlider.getValue() / 100.0);
    }
}
