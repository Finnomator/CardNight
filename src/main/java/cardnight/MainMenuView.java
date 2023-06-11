package cardnight;

import cardnight.games.tictactoe.viewcontroller.TTTGegnerWahl;
import cardnight.games.ueno.viewcontroler.UenoView;
import cardnight.games.witch.viewcontroller.WitchView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import sun.security.provider.ConfigFile;

import java.awt.*;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;

public class MainMenuView {

    public ImageView hintergrundImageView;
    public StackPane root;
    public ImageView tttButtonImgView;
    public ImageView uenoButtonImgView;
    public ImageView witchButtonImgView;
    public ImageView beendenButtonImgView;

    public void initialize() {
        MainMenuBilder.ladeBilder();
        hintergrundImageView.fitWidthProperty().bind(root.widthProperty().subtract(500));
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
        tttButtonImgView.setImage(MainMenuBilder.gibButtonBild(SpielTyp.TIC_TAC_TOE, true));
    }

    public void mouseExitedTTT() {
        setzeHintergrund(MainMenuBilder.gibDefaultHintergrund());
        tttButtonImgView.setImage(MainMenuBilder.gibButtonBild(SpielTyp.TIC_TAC_TOE, false));
    }

    public void mouseExitedUeno() {
        setzeHintergrund(MainMenuBilder.gibDefaultHintergrund());
        uenoButtonImgView.setImage(MainMenuBilder.gibButtonBild(SpielTyp.UENO, false));
    }

    public void mouseEnteredWitch() {
        setzeHintergrund(MainMenuBilder.gibHintergrundBild(SpielTyp.WITCH));
        witchButtonImgView.setImage(MainMenuBilder.gibButtonBild(SpielTyp.WITCH, true));
    }

    public void mouseEnteredUeno() {
        setzeHintergrund(MainMenuBilder.gibHintergrundBild(SpielTyp.UENO));
        uenoButtonImgView.setImage(MainMenuBilder.gibButtonBild(SpielTyp.UENO, true));
    }

    public void mouseExitedWitch() {
        setzeHintergrund(MainMenuBilder.gibDefaultHintergrund());
        witchButtonImgView.setImage(MainMenuBilder.gibButtonBild(SpielTyp.WITCH, false));
    }

    public void mouseEnteredBeenden() {
        beendenButtonImgView.setImage(MainMenuBilder.beendenButtonBild(true));
    }

    public void mouseExitedBeenden() {
        beendenButtonImgView.setImage(MainMenuBilder.beendenButtonBild(false));
    }
}
