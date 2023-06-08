package cardnight;

import cardnight.games.tictactoe.viewcontroller.TTTGegnerWahl;
import cardnight.games.tictactoe.viewcontroller.TicTacToeView;
import cardnight.games.ueno.viewcontroler.UenoView;
import cardnight.games.witch.viewcontroller.WitchView;
import javafx.scene.control.Slider;

import java.awt.*;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;

public class MainMenuView {

    public Slider soundVolumeSlider;

    public void initialize() {
        soundVolumeSlider.setValue(Main.getSoundVolume() * 100.0);
    }

    public void onTicTacToeClick() throws IOException {
        SoundPlayer.klickSound();
        ScreenController.activateNewPane(TTTGegnerWahl.loadScene());
    }

    public void onWitchClick() throws IOException {
        SoundPlayer.klickSound();
        ScreenController.activateNewPane(WitchView.loadScene());
    }

    public void onBeendenClick() {
        SoundPlayer.klickSound();
        System.exit(0);
    }

    public void onUenoClick() throws IOException {
        SoundPlayer.klickSound();
        ScreenController.activateNewPane(UenoView.loadScene());
    }

    public void onSoundSliderMouseClick() {
        Main.setSoundVolume(soundVolumeSlider.getValue() / soundVolumeSlider.getMax());
    }

    public static void openLinkInBrowser(String link) {
        try {
            Desktop.getDesktop().browse(new URL(link).toURI());
        } catch (IOException | URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }

    public void githubRepoLinkClick() {
        openLinkInBrowser(Main.GitHubRepo);
    }

    public void reportBugLinkClick() {
        openLinkInBrowser(Main.BugReportUrl);
    }
}
