package cardnight.games.ueno.viewcontroler;

import cardnight.games.ueno.UenoKarte;
import cardnight.games.ueno.UenoSpieler;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;

import java.io.IOException;

public class UenoGegnerUiHand extends UenoUiHand {
    public HBox kartenBox;
    public Text nameText;
    public ProgressIndicator thinkingProgress;

    @Override
    public void uiErstellen(UenoSpieler spieler) {
        this.spieler = spieler;
        nameText.setText(spieler.name);
        setThinkingStatus(false);
        updateUi();
    }

    private void setThinkingStatus(boolean thinking) {
        thinkingProgress.setVisible(thinking);
    }

    @Override
    public void updateUi() {

        setThinkingStatus(spieler.istAmZug());

        kartenBox.getChildren().clear();
        for (UenoKarte ignored : spieler.gibHandkarten()) {
            FXMLLoader loader;
            loader = new FXMLLoader(getClass().getResource("/cardnight/game-views/ueno/karten-rueckseite.fxml"));
            try {
                kartenBox.getChildren().add(loader.load());
            } catch (IOException ex) {
                throw new RuntimeException();
            }
        }
    }
}
