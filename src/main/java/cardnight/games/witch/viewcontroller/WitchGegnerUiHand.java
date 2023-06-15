package cardnight.games.witch.viewcontroller;

import cardnight.games.witch.WitchGegner;
import cardnight.games.witch.WitchKarte;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;

public class WitchGegnerUiHand {
    public Label nameText;
    public HBox kartenBox;
    public ProgressIndicator thinkingProgress;
    public Label geschaetzteSticheText;
    public Label erhalteneSticheText;

    private WitchGegner spieler;

    public void uiErstellen(WitchGegner spieler) {
        this.spieler = spieler;
        nameText.setText(spieler.name);
        setThinkingProgress(false);
        updateUi();
    }

    private void setThinkingProgress(boolean thinking) {
        thinkingProgress.setVisible(thinking);
    }

    public void updateUi() {
        setThinkingProgress(spieler.istAmZug());
        erhalteneSticheText.setText(String.valueOf(spieler.gibAnzahlErzhaltenderStiche()));

        int schaetzung = spieler.gibStichSchaetzung();

        if (schaetzung == -1)
            geschaetzteSticheText.setText("tbd");
        else
            geschaetzteSticheText.setText(String.valueOf(schaetzung));

        kartenBox.getChildren().clear();
        for (WitchKarte ignored : spieler.gibHandkarten())
            kartenBox.getChildren().add(new ImageView(WitchKartenBilder.witchKartenRueckseite));
    }
}
