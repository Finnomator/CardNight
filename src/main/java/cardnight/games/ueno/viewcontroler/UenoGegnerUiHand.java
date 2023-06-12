package cardnight.games.ueno.viewcontroler;

import cardnight.games.Ressourcen;
import cardnight.games.ueno.UenoKarte;
import cardnight.games.ueno.UenoSpieler;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;

public class UenoGegnerUiHand extends UenoUiHand {
    public HBox kartenBox;
    public Text nameText;
    public ProgressIndicator thinkingProgress;
    public ImageView stickmanImageView;

    @Override
    public void uiErstellen(UenoSpieler spieler) {
        this.spieler = spieler;
        nameText.setText(spieler.name);
        updateUi();
    }

    private void setThinkingStatus(boolean thinking) {
        thinkingProgress.setVisible(thinking);
    }

    public void setHappy(boolean happy) {
        stickmanImageView.setImage(happy ? Ressourcen.stickmanHappyImage : Ressourcen.stickmanImage);
        if (happy)
            setThinkingStatus(false);
    }

    @Override
    public void updateUi() {

        setThinkingStatus(spieler.istAmZug());

        setHappy(spieler.istFertig());

        kartenBox.getChildren().clear();
        for (UenoKarte ignored : spieler.gibHandkarten())
            kartenBox.getChildren().add(new ImageView(UenoRessourcen.uenoKartenRueckseite));
    }
}
