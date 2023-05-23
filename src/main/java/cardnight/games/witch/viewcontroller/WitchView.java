package cardnight.games.witch.viewcontroller;

import cardnight.GameOver;
import cardnight.PauseMenu;
import cardnight.games.SpielView;
import cardnight.games.ueno.viewcontroler.UenoKarteKlickEvent;
import cardnight.games.witch.Witch;
import cardnight.games.witch.WitchKarte;
import javafx.event.Event;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

public class WitchView extends SpielView {

    public StackPane root;
    public TextField schaetzungsEingabeFeld;
    public VBox schaetzungsRoot;
    private Witch witch;
    private WitchUiKarte trumpfUiKarte;
    private WitchHauptspielerUiHand hauptspielerUiHand;
    private WitchPunktetafel punktetafel;
    private final AtomicBoolean hatStichSchaetzungBestaetigt = new AtomicBoolean(false);
    private final AtomicBoolean hatKarteGeklickt = new AtomicBoolean(false);
    private final AtomicReference<WitchKarte> geklickteKarte = new AtomicReference<>();

    public void initialize() throws IOException {
        //TODO: Die Anzahl der Stiche anzeigen, die jeder Spieler gerade hat
        //TODO: Die Rückseiten der Gegnerkarten anzeigen
        //TODO: Anzeigen, wer am Zug ist
        //TODO: Anzeigen, welche Karten auf dem Stich (Ablagestapel) liegen

        //TODO: Falls der Zuständige (Finn) richtig viel Bock hat:
        //TODO: In der allerersten Runde (jeder hat nur 1 Karte) sieht man nur die Karte JEDES Gegners, NICHT seine eigene Karte

        witch = new Witch(4, this);

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

        System.out.println("Warte auf Schätzung...");

        hauptspielerUiHand.setDisableAllCards(true);
        schaetzungsRoot.setDisable(false);

        while (!hatStichSchaetzungBestaetigt.get())
            Witch.delay(50);

        hatStichSchaetzungBestaetigt.set(false);

        return Integer.parseInt(schaetzungsEingabeFeld.getText());
    }

    public WitchKarte warteAufKartenauswahl() {
        // Wartet, bis der Spieler eine Karte geklickt hat, die er ablegen will und gibt diese zurück

        System.out.println("Warte auf Karte...");

        hauptspielerUiHand.setDisableAllCards(false);
        schaetzungsRoot.setDisable(true);

        while (!hatKarteGeklickt.get())
            Witch.delay(50);

        hatKarteGeklickt.set(false);

        return geklickteKarte.get();
    }

    public void updateUi() {
        hauptspielerUiHand.updateUi();
        trumpfUiKarte.uiErstellen(witch.gibTrumpfKarte());
        punktetafel.updateUi();
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

    public static void numericOnly(final TextField field) {
        field.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*"))
                field.setText(newValue.replaceAll("\\D", ""));
        });
    }
}

