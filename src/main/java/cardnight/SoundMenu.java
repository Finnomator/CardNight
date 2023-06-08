package cardnight;

import javafx.scene.control.Slider;
import javafx.scene.input.MouseEvent;

public class SoundMenu {
    public Slider soundVolumeSlider;

    public void initialize() {
        soundVolumeSlider.setValue(Main.getSoundVolume() * 100.0);
    }

    public void onSoundSliderMouseClick(MouseEvent mouseEvent) {
        Main.setSoundVolume(soundVolumeSlider.getValue() / 100.0);
    }
}
