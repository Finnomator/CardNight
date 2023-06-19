package cardnight;

import cardnight.games.Ressourcen;
import cardnight.games.tictactoe.viewcontroller.TTTBilder;
import cardnight.games.tictactoe.viewcontroller.TTTGegnerWahl;
import cardnight.games.ueno.UenoFarbe;
import cardnight.games.ueno.viewcontroler.UenoKartenBilder;
import cardnight.games.witch.WitchFarbe;
import cardnight.games.witch.viewcontroller.WitchKartenBilder;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Hyperlink;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Box;
import javafx.scene.transform.Rotate;
import javafx.util.Duration;

import java.awt.*;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Random;
import java.util.concurrent.atomic.AtomicBoolean;

public class MainMenuView {

    public ImageView hintergrundImageView;
    public StackPane root;
    public ImageView tttButtonImgView;
    public ImageView uenoButtonImgView;
    public ImageView witchButtonImgView;
    public ImageView beendenButtonImgView;
    public Hyperlink debugLink;
    public Box spinnBox;


    public void initialize() {
        debugLink.setVisited(Main.debugMode);
        MainMenuBilder.ladeBilder();
        hintergrundImageView.fitWidthProperty().bind(root.widthProperty().subtract(500));
        spinnBox.heightProperty().bind(root.heightProperty().divide(2));
        spinnBox.widthProperty().bind(spinnBox.heightProperty().multiply(0.69));

        tttButtonImgView.setImage(MainMenuBilder.gibButtonBild(SpielTyp.TIC_TAC_TOE, false));
        uenoButtonImgView.setImage(MainMenuBilder.gibButtonBild(SpielTyp.UENO, false));
        witchButtonImgView.setImage(MainMenuBilder.gibButtonBild(SpielTyp.WITCH, false));
        beendenButtonImgView.setImage(MainMenuBilder.beendenButtonBild(false));

        setzeBoxFace();

        MusicPlayer.spielePlaylist();
        startRotatingCards();
        setzeHintergrund(null);
    }

    public void onTicTacToeClick() {
        SoundPlayer.klickSound();
        TTTGegnerWahl.showScene();
    }

    public void onWitchClick() {
        SoundPlayer.klickSound();
        ScreenController.show(ScreenController.witchGegnerWahl);
    }

    public void onBeendenClick() {
        SoundPlayer.klickSound();
        System.exit(0);
    }

    public void onUenoClick() {
        SoundPlayer.klickSound();
        ScreenController.show(ScreenController.uenoGegnerWahl);
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
        zeigeSpinnBox(false);
    }

    public void mouseExitedTTT() {
        setzeHintergrund(null);
        tttButtonImgView.setImage(MainMenuBilder.gibButtonBild(SpielTyp.TIC_TAC_TOE, false));
        zeigeSpinnBox(true);
    }

    public void mouseExitedUeno() {
        setzeHintergrund(null);
        uenoButtonImgView.setImage(MainMenuBilder.gibButtonBild(SpielTyp.UENO, false));
        zeigeSpinnBox(true);
    }

    public void mouseEnteredWitch() {
        setzeHintergrund(MainMenuBilder.gibHintergrundBild(SpielTyp.WITCH));
        witchButtonImgView.setImage(MainMenuBilder.gibButtonBild(SpielTyp.WITCH, true));
        zeigeSpinnBox(false);
    }

    public void mouseEnteredUeno() {
        setzeHintergrund(MainMenuBilder.gibHintergrundBild(SpielTyp.UENO));
        uenoButtonImgView.setImage(MainMenuBilder.gibButtonBild(SpielTyp.UENO, true));
        zeigeSpinnBox(false);
    }

    public void mouseExitedWitch() {
        setzeHintergrund(null);
        witchButtonImgView.setImage(MainMenuBilder.gibButtonBild(SpielTyp.WITCH, false));
        zeigeSpinnBox(true);
    }

    public void mouseEnteredBeenden() {
        beendenButtonImgView.setImage(MainMenuBilder.beendenButtonBild(true));
    }

    public void mouseExitedBeenden() {
        beendenButtonImgView.setImage(MainMenuBilder.beendenButtonBild(false));
    }

    private void startRotatingCards() {

        Rotate yRotation = new Rotate(0, Rotate.Y_AXIS);
        // Rotate zRotation = new Rotate(-34.66, Rotate.Z_AXIS);

        spinnBox.getTransforms().addAll(yRotation);

        Timeline timeline = new Timeline();
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.getKeyFrames().add(new KeyFrame(Duration.millis(10), actionEvent -> {

            int winkel = (int) Math.abs(yRotation.getAngle()) % 360;

            if (winkel == 90 || winkel == 270)
                setzeBoxFace();

            yRotation.setAngle(yRotation.getAngle() + 1);
        }));
        timeline.play();
    }

    private void setzeBoxFace() {
        PhongMaterial material = new PhongMaterial();
        material.setDiffuseMap(randomKartenBild());
        spinnBox.setMaterial(material);
    }

    private static Image randomKartenBild() {

        Random rnd = Tools.random;

        if (rnd.nextInt(5) == 0) {
            if (rnd.nextBoolean())
                return TTTBilder.xHandkarte;
            else
                return TTTBilder.oHandkarte;
        }

        String farbe;
        int zahl;

        if (rnd.nextBoolean()) {
            farbe = UenoFarbe.values()[rnd.nextInt(UenoFarbe.values().length)].toString().toLowerCase();
            zahl = rnd.nextInt(10);
            return UenoKartenBilder.ladeBild("zahlen/" + farbe + "/UNO_" + zahl + "_" + farbe + ".png", 0, 0);
        } else {
            if (rnd.nextInt(100) == 0) // What could this be? 👀
                return Ressourcen.dieDiebin;

            farbe = WitchFarbe.values()[rnd.nextInt(WitchFarbe.values().length)].toString().toLowerCase();
            zahl = rnd.nextInt(13) + 1;
            return WitchKartenBilder.ladeBild(farbe + "/Witch_" + farbe + "_" + zahl + ".png", 0, 0);
        }
    }

    private void zeigeSpinnBox(boolean sichtbar) {
        spinnBox.setVisible(sichtbar);
    }

    public void debugClick() {
        Main.debugMode = !Main.debugMode;
        debugLink.setVisited(Main.debugMode);

        if (Main.debugMode)
            DebugWindow.oeffnen();
        else
            DebugWindow.schliessen();
    }

    public static Pane loadScene() {
        try {
            return new FXMLLoader(Main.class.getResource("main-menu-view.fxml")).load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
