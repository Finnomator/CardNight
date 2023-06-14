package cardnight.games.tictactoe.viewcontroller;

import cardnight.Main;
import cardnight.games.Ressourcen;
import cardnight.games.tictactoe.TicTacToeSpieler;
import cardnight.games.ueno.UenoKarte;
import cardnight.games.ueno.UenoSpieler;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;

import java.io.IOException;

public class TTTGegnerUiHand extends TTTUiHand {
    public HBox kartenBox;
    public ProgressIndicator thinkingProgress;
    public ImageView stickmanImageView;
    public static final Image tttKartenRueckseite = new Image(Main.class.getResourceAsStream(
       "/cardnight/game-views/tictactoe/images/TicTacToe_Ruckseite.png"),
            0, Main.GEGNERKARTE_HOEHE, true, true);

    @Override
    public void uiErstellen(TicTacToeSpieler spieler) {
        this.spieler = spieler;
        setThinkingStatus(false);
        updateUi();
    }

    private void setThinkingStatus(boolean thinking) {
        thinkingProgress.setVisible(thinking);
    }

    public void setHappy(boolean happy) {
        if (happy) {
            stickmanImageView.setImage(Ressourcen.stickmanHappyImage);
            TTTSoundPlayer.gewonnen();
            kartenBox.setOpacity(0);
        } else {
            stickmanImageView.setImage(Ressourcen.stickmanImage);
            kartenBox.setOpacity(1);
        }

        if (happy)
            setThinkingStatus(false);
    }

    @Override
    public void updateUi() {

        setThinkingStatus(spieler.istAmZug());

        kartenBox.getChildren().clear();
        for (int i = 0; i < spieler.gibAnzahlHandKarten(); ++i)
            kartenBox.getChildren().add(new ImageView(tttKartenRueckseite));
    }
}
