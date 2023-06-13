package cardnight;

import cardnight.games.Ressourcen;
import cardnight.games.tictactoe.viewcontroller.TTTGegnerWahl;
import cardnight.games.ueno.UenoFarbe;
import cardnight.games.ueno.viewcontroler.UenoKartenBilder;
import cardnight.games.ueno.viewcontroler.UenoView;
import cardnight.games.witch.WitchFarbe;
import cardnight.games.witch.viewcontroller.WitchKartenBilder;
import cardnight.games.witch.viewcontroller.WitchView;
import javafx.scene.control.Hyperlink;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.transform.Rotate;
import javafx.util.Duration;

import java.awt.*;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Random;

public class MainMenuView {

    public ImageView hintergrundImageView;
    public StackPane root;
    public ImageView tttButtonImgView;
    public ImageView uenoButtonImgView;
    public ImageView witchButtonImgView;
    public ImageView beendenButtonImgView;
    public ImageView spinKarte;
    public Hyperlink debugLink;

    public void initialize() {
        debugLink.setVisited(Main.debugMode);
        MainMenuBilder.ladeBilder();
        hintergrundImageView.fitWidthProperty().bind(root.widthProperty().subtract(500));
        spinKarte.fitHeightProperty().bind(root.heightProperty().subtract(500));

        tttButtonImgView.setImage(MainMenuBilder.gibButtonBild(SpielTyp.TIC_TAC_TOE, false));
        uenoButtonImgView.setImage(MainMenuBilder.gibButtonBild(SpielTyp.UENO, false));
        witchButtonImgView.setImage(MainMenuBilder.gibButtonBild(SpielTyp.WITCH, false));
        beendenButtonImgView.setImage(MainMenuBilder.beendenButtonBild(false));

        setzeHintergrund(null);
        startRotatingCards();
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
        zeigeSpinKarten(false);
    }

    public void mouseExitedTTT() {
        setzeHintergrund(null);
        tttButtonImgView.setImage(MainMenuBilder.gibButtonBild(SpielTyp.TIC_TAC_TOE, false));
        zeigeSpinKarten(true);
    }

    public void mouseExitedUeno() {
        setzeHintergrund(null);
        uenoButtonImgView.setImage(MainMenuBilder.gibButtonBild(SpielTyp.UENO, false));
        zeigeSpinKarten(true);
    }

    public void mouseEnteredWitch() {
        setzeHintergrund(MainMenuBilder.gibHintergrundBild(SpielTyp.WITCH));
        witchButtonImgView.setImage(MainMenuBilder.gibButtonBild(SpielTyp.WITCH, true));
        zeigeSpinKarten(false);
    }

    public void mouseEnteredUeno() {
        setzeHintergrund(MainMenuBilder.gibHintergrundBild(SpielTyp.UENO));
        uenoButtonImgView.setImage(MainMenuBilder.gibButtonBild(SpielTyp.UENO, true));
        zeigeSpinKarten(false);
    }

    public void mouseExitedWitch() {
        setzeHintergrund(null);
        witchButtonImgView.setImage(MainMenuBilder.gibButtonBild(SpielTyp.WITCH, false));
        zeigeSpinKarten(true);
    }

    public void mouseEnteredBeenden() {
        beendenButtonImgView.setImage(MainMenuBilder.beendenButtonBild(true));
    }

    public void mouseExitedBeenden() {
        beendenButtonImgView.setImage(MainMenuBilder.beendenButtonBild(false));
    }

    private void startRotatingCards() {

        Rotate yRotation = new Rotate(0, Rotate.Y_AXIS);
        Rotate zRotation = new Rotate(-34.66, Rotate.Z_AXIS);

        zRotation.pivotXProperty().bind(spinKarte.fitHeightProperty().divide(2).divide(1.447));
        zRotation.pivotYProperty().bind(spinKarte.fitHeightProperty().divide(2));
        yRotation.pivotXProperty().bind(spinKarte.fitHeightProperty().divide(2).divide(1.447));
        yRotation.pivotYProperty().bind(spinKarte.fitHeightProperty().divide(2));

        spinKarte.getTransforms().addAll(yRotation, zRotation);

        Timeline timeline = new Timeline();
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.getKeyFrames().add(new KeyFrame(Duration.millis(10), actionEvent -> {

            int winkel = (int) Math.abs(yRotation.getAngle()) % 360;

            if (winkel == 90 || winkel == 270) {
                spinKarte.setImage(randomKartenBild());
            }

            yRotation.setAngle(yRotation.getAngle() + 1);
        }));
        timeline.play();
    }

    private static Image randomKartenBild() {

        Random rnd = new Random();

        int rndGame = rnd.nextInt(2);

        String farbe;
        int zahl;

        switch (rndGame) {
            case 0:
                farbe = UenoFarbe.values()[rnd.nextInt(UenoFarbe.values().length)].toString().toLowerCase();
                zahl = rnd.nextInt(10);
                return UenoKartenBilder.ladeBild("zahlen/" + farbe + "/UNO_" + zahl + "_" + farbe + ".png",  0, 0);
            case 1:

                if (rnd.nextInt(100) == 0) // What could this be? 👀
                    return Ressourcen.dieDiebin;

                farbe = WitchFarbe.values()[rnd.nextInt(WitchFarbe.values().length)].toString().toLowerCase();
                zahl = rnd.nextInt(13) + 1;
                return WitchKartenBilder.ladeBild(farbe + "/Witch_" + farbe + "_" + zahl + ".png", 0, 0);
        }

        throw new RuntimeException();
    }

    private void zeigeSpinKarten(boolean sichtbar) {
        spinKarte.setVisible(sichtbar);
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
