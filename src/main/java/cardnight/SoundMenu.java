package cardnight;

import javafx.scene.control.Button;
import javafx.scene.control.Slider;

import java.util.Random;

public class SoundMenu {
    public Button soundEnableButton;
    public Slider musicVolumeSlider;

    public void initialize() {
        musicVolumeSlider.setValue(Main.getMusicVolume() * 100.0);
    }

    public void soundEnableKlick() {
        Main.enableSound = !Main.enableSound;
        soundEnableButton.setText(Main.enableSound ? "\uD83D\uDD0A" : "\uD83D\uDD07");
    }

    public void musicSliderMouseClick() {
        Main.setMusicVolume(musicVolumeSlider.getValue() / 100.0);

        if (new Random().nextInt(20) == 0) // 🤔
            SoundPlayer.soundEinstellen();
    }
}
