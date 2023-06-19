package cardnight;

import javafx.scene.control.Label;
import javafx.scene.control.Slider;

public class SoundMenu {
    public Slider musicVolumeSlider;
    public Slider soundVolumeSlider;
    public Label soundLabel;
    public Label musicLabel;

    private boolean valueChanged;

    public void initialize() {

        musicVolumeSlider.valueProperty().addListener((obs, oldVal, newVal) -> {

            double volume = newVal.doubleValue();

            if (volume <= 0)
                musicLabel.setText("\uD834\uDD94");
            else if (volume < 1 / 3.0)
                musicLabel.setText("♪");
            else if (volume < 2 / 3.0)
                musicLabel.setText("🎵");
            else
                musicLabel.setText("🎶");

            Main.setMusicVolume(volume);
        });

        soundVolumeSlider.valueProperty().addListener((obs, oldVal, newVal) -> {
            valueChanged = true;

            double volume = newVal.doubleValue();

            if (volume <= 0)
                soundLabel.setText("🔇");
            else if (volume < 1 / 3.0)
                soundLabel.setText("🔈");
            else if (volume < 2 / 3.0)
                soundLabel.setText("🔉");
            else
                soundLabel.setText("🔊");
        });

        musicVolumeSlider.setValue(Main.getMusicVolume());
        soundVolumeSlider.setValue(SoundPlayer.soundVolume);
    }

    public void soundSliderMouseRelease() {
        if (!valueChanged)
            return;

        SoundPlayer.soundVolume = soundVolumeSlider.getValue();
        SoundPlayer.soundEinstellen();
        valueChanged = false;
    }
}
