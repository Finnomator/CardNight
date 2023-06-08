package cardnight;

import cardnight.games.tictactoe.viewcontroller.TTTGegnerWahl;
import cardnight.games.ueno.viewcontroler.UenoView;
import cardnight.games.witch.viewcontroller.WitchView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;

import java.awt.*;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;

public class MainMenuView {

    public ImageView hintergrundImageView;
    public StackPane root;

    public void initialize() {
        MainMenuBilder.ladeBilder();
        hintergrundImageView.fitWidthProperty().bind(root.widthProperty().subtract(100));
        setzeHintergrund(MainMenuBilder.gibDefaultHintergrund());
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

    private void setzeHintergrund(Image img) {
        hintergrundImageView.setImage(img);
    }

    public void githubRepoLinkClick() {
        openLinkInBrowser(Main.GitHubRepo);
    }

    public void reportBugLinkClick() {
        openLinkInBrowser(Main.BugReportUrl);
    }

    public void mouseEnteredTTT() {
        setzeHintergrund(MainMenuBilder.gibHintergrundBild(SpielTyp.TIC_TAC_TOE));
    }

    public void mouseExitedTTT() {
        setzeHintergrund(MainMenuBilder.gibDefaultHintergrund());
    }

    public void mouseExitedUeno() {
        setzeHintergrund(MainMenuBilder.gibDefaultHintergrund());
    }

    public void mouseEnteredWitch() {
        setzeHintergrund(MainMenuBilder.gibHintergrundBild(SpielTyp.WITCH));
    }

    public void mouseEnteredUeno() {
        setzeHintergrund(MainMenuBilder.gibHintergrundBild(SpielTyp.UENO));
    }

    public void mouseExitedWitch() {
        setzeHintergrund(MainMenuBilder.gibDefaultHintergrund());
    }
}
