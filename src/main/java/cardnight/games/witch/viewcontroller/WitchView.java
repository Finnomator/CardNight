package cardnight.games.witch.viewcontroller;

import cardnight.GameOver;
import cardnight.PauseMenu;
import cardnight.games.SpielView;
import cardnight.games.witch.Witch;
import cardnight.games.witch.WitchGegner;
import cardnight.games.witch.WitchKarte;
import cardnight.games.witch.WitchSpieler;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

import java.io.IOException;
import java.util.HashMap;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

public class WitchView extends SpielView {

    public StackPane root;
    public TextField schaetzungsEingabeFeld;
    public VBox schaetzungsRoot;
    public Button schaetzungsOkButton;
    public HBox gegnerUiHaendeContainer;
    public Text rundenNummerText;
    private Witch witch;
    private WitchUiKarte trumpfUiKarte;
    private WitchHauptspielerUiHand hauptspielerUiHand;
    private WitchPunktetafel punktetafel;
    private final AtomicBoolean hatStichSchaetzungBestaetigt = new AtomicBoolean(false);
    private final AtomicBoolean hatKarteGeklickt = new AtomicBoolean(false);
    private final AtomicReference<WitchKarte> geklickteKarte = new AtomicReference<>();
    private HashMap<WitchGegner, WitchGegnerUiHand> gegnerUiHaende;

    public void initialize() throws IOException {
        //TODO: Die Anzahl der Stiche anzeigen, die jeder Spieler gerade hat
        //TODO: Die Rückseiten der Gegnerkarten anzeigen
        //TODO: Anzeigen, wer am Zug ist
        //TODO: Anzeigen, welche Karten auf dem Stich (Ablagestapel) liegen

        //TODO: Falls der Zuständige (Finn) richtig viel Bock hat:
        //TODO: In der allerersten Runde (jeder hat nur 1 Karte) sieht man nur die Karte JEDES Gegners, NICHT seine eigene Karte
        // Hallo, Finn hier. NÖ! (-> irgendwann?)

        witch = new Witch(4, this);
        gegnerUiHaende = new HashMap<>();

        for (int i = 1; i < witch.gibSpieler().length; ++i) {
            WitchGegner gegner = (WitchGegner) witch.gibSpieler()[i];

            FXMLLoader gegnerHandLoader = new FXMLLoader(getClass().getResource("/cardnight/game-views/witch/gegner-hand.fxml"));
            Node gegnerHandNode = gegnerHandLoader.load();
            
            gegnerUiHaendeContainer.getChildren().add(gegnerHandNode);
            WitchGegnerUiHand uiHand = gegnerHandLoader.getController();
            uiHand.uiErstellen(gegner);

            gegnerUiHaende.put(gegner, uiHand);
        }

        FXMLLoader trumpfKartenLoader = new FXMLLoader(getClass().getResource("/cardnight/game-views/witch/witch-karte.fxml"));
        Node uiTrumpfKarte = trumpfKartenLoader.load();
        uiTrumpfKarte.setDisable(true);
        root.getChildren().add(uiTrumpfKarte);
        trumpfUiKarte = trumpfKartenLoader.getController();
        numericOnly(schaetzungsEingabeFeld);

        FXMLLoader handkartenLoader = new FXMLLoader(getClass().getResource("/cardnight/game-views/witch/hauptspieler-hand.fxml"));
        root.getChildren().add(handkartenLoader.load());
        hauptspielerUiHand = handkartenLoader.getController();
        hauptspielerUiHand.uiErstellen(witch.gibHauptspieler());

        FXMLLoader punktetafelLoader = new FXMLLoader(getClass().getResource("/cardnight/game-views/witch/punktetafel.fxml"));
        root.getChildren().add(punktetafelLoader.load());
        punktetafel = punktetafelLoader.getController();
        punktetafel.uiErstellen(witch);

        root.addEventFilter(WitchKartenKlickEvent.ANY, this::handleWitchKartenClick);

        witch.game();
    }

    private void handleWitchKartenClick(WitchKartenKlickEvent event) {
        hatKarteGeklickt.set(true);
        geklickteKarte.set(event.geklickteKarte);
    }

    public int warteAufSchaetzung() {

        System.out.println("\t\tWarte auf Schätzung vom Spieler...");

        Platform.runLater(() -> hauptspielerUiHand.disableAllCards());
        schaetzungsRoot.setVisible(true);

        hatStichSchaetzungBestaetigt.set(false);

        while (!hatStichSchaetzungBestaetigt.get())
            Witch.delay(50);

        return Integer.parseInt(schaetzungsEingabeFeld.getText());
    }

    public WitchKarte warteAufKartenauswahl() {
        // Wartet, bis der Spieler eine Karte geklickt hat, die er ablegen will und gibt diese zurück

        System.out.println("\t\t\tWarte bis Spieler Karte ausgewählt hat...");

        Platform.runLater(() -> hauptspielerUiHand.updateUi(false));
        schaetzungsRoot.setVisible(false);

        while (!hatKarteGeklickt.get())
            Witch.delay(50);

        hatKarteGeklickt.set(false);

        return geklickteKarte.get();
    }

    public void updateUi() {
        hauptspielerUiHand.updateUi(true);
        trumpfUiKarte.uiErstellen(witch.gibTrumpfKarte());
        punktetafel.updateUi();
        rundenNummerText.setText(String.valueOf(witch.gibRundenNummer() + 1));

        for (WitchGegnerUiHand hand : gegnerUiHaende.values())
            hand.updateUi();
    }

    @Override
    public void beendeSpiel() {
        try {
            root.getChildren().add(GameOver.loadScene());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void pauseClick() throws IOException {
        root.getChildren().add(PauseMenu.loadScene());
    }

    public static Pane loadScene() throws IOException {
        return new FXMLLoader(WitchView.class.getResource("/cardnight/game-views/witch-view.fxml")).load();
    }

    public void schaetzungOkKlick() {
        hatStichSchaetzungBestaetigt.set(true);
    }

    private void numericOnly(final TextField field) {
        field.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*"))
                field.setText(newValue.replaceAll("\\D", ""));
            schaetzungsOkButton.setDisable(field.getText().equals(""));
        });
    }
}

