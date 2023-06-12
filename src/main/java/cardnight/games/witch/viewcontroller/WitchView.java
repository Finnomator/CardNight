package cardnight.games.witch.viewcontroller;

import cardnight.GameOver;
import cardnight.Main;
import cardnight.PauseMenu;
import cardnight.SoundPlayer;
import cardnight.games.SpielView;
import cardnight.games.witch.Witch;
import cardnight.games.witch.WitchGegner;
import cardnight.games.witch.WitchKarte;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
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
    public Text erhalteneSticheText;
    public GridPane tableGrid;
    public ImageView trumpfImageView;
    public GridPane tischContentContainer;
    private Witch witch;
    private WitchHauptspielerUiHand hauptspielerUiHand;
    private WitchPunktetafel punktetafel;
    private final AtomicBoolean hatStichSchaetzungBestaetigt = new AtomicBoolean(false);
    private final AtomicBoolean hatKarteGeklickt = new AtomicBoolean(false);
    private final AtomicReference<WitchKarte> geklickteKarte = new AtomicReference<>();
    private HashMap<WitchGegner, WitchGegnerUiHand> gegnerUiHaende;
    private WitchUiStichStapel uiStichStapel;

    public void initialize() throws IOException {
        //TODO: Falls der Zuständige (Finn) richtig viel Bock hat:
        //TODO: In der allerersten Runde (jeder hat nur 1 Karte) sieht man nur die Karte JEDES Gegners, NICHT seine eigene Karte
        // Hallo, Finn hier. NÖ! (-> irgendwann?)

        WitchKartenBilder.bilderLaden();

        witch = new Witch(4, 1000, this);
        Main.setzeAktuellesSpiel(witch);

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

        FXMLLoader stichLoader = new FXMLLoader(getClass().getResource("/cardnight/game-views/witch/stich-stapel.fxml"));
        Node uiStich = stichLoader.load();
        tischContentContainer.add(uiStich, 2, 0);
        uiStichStapel = stichLoader.getController();
        uiStichStapel.uiErstellen(witch);

        numericOnly(schaetzungsEingabeFeld);

        FXMLLoader handkartenLoader = new FXMLLoader(getClass().getResource("/cardnight/game-views/witch/hauptspieler-hand.fxml"));
        Node uiHauptHand = handkartenLoader.load();
        GridPane.setRowIndex(uiHauptHand, 2);
        GridPane.setHalignment(uiHauptHand, HPos.CENTER);
        tableGrid.getChildren().add(uiHauptHand);
        hauptspielerUiHand = handkartenLoader.getController();
        hauptspielerUiHand.uiErstellen(witch.gibHauptspieler());

        FXMLLoader punktetafelLoader = new FXMLLoader(getClass().getResource("/cardnight/game-views/witch/punktetafel.fxml"));
        Node uiPunkteTafel = punktetafelLoader.load();
        StackPane.setAlignment(uiPunkteTafel, Pos.CENTER_RIGHT);
        root.getChildren().add(uiPunkteTafel);
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
        schaetzungsRoot.setDisable(false);

        hatStichSchaetzungBestaetigt.set(false);

        while (!hatStichSchaetzungBestaetigt.get())
            Witch.delay(50);

        schaetzungsRoot.setDisable(true);

        try {
            return Integer.parseInt(schaetzungsEingabeFeld.getText());
        } catch (NumberFormatException ex) {
            return warteAufSchaetzung();
        }
    }

    public WitchKarte warteAufKartenauswahl() {
        // Wartet, bis der Spieler eine Karte geklickt hat, die er ablegen will und gibt diese zurück

        System.out.println("\t\t\tWarte bis Spieler Karte ausgewählt hat...");

        Platform.runLater(() -> hauptspielerUiHand.updateUi(false));

        while (!hatKarteGeklickt.get())
            Witch.delay(50);

        hatKarteGeklickt.set(false);

        return geklickteKarte.get();
    }

    public void updateUi() {
        hauptspielerUiHand.updateUi(true);
        trumpfImageView.setImage(WitchKartenBilder.karteZuBild(witch.gibTrumpfKarte()));
        punktetafel.updateUi();
        rundenNummerText.setText(String.valueOf(witch.gibRundenNummer() + 1));
        uiStichStapel.updateUi();
        erhalteneSticheText.setText(String.valueOf(witch.gibHauptspieler().gibAnzahlErzhaltenderStiche()));

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
        SoundPlayer.klickSound();
        root.getChildren().add(PauseMenu.loadScene());
    }

    public static Pane loadScene() throws IOException {
        return new FXMLLoader(WitchView.class.getResource("/cardnight/game-views/witch-view.fxml")).load();
    }

    public void schaetzungOkKlick() {
        SoundPlayer.klickSound();
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

