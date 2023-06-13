package cardnight;

import cardnight.games.tictactoe.viewcontroller.TTTGegnerWahl;
import cardnight.games.ueno.viewcontroler.UenoView;
import cardnight.games.witch.viewcontroller.WitchView;
import javafx.scene.control.Hyperlink;

import java.awt.*;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;

public class MainMenuView {

    public Hyperlink debugLink;

    public void initialize() {
        debugLink.setVisited(Main.debugMode);
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

    public void debugClick() {
        Main.debugMode = !Main.debugMode;
        debugLink.setVisited(Main.debugMode);

        if (Main.debugMode)
            DebugWindow.oeffnen();
        else
            DebugWindow.schliessen();
    }
}
