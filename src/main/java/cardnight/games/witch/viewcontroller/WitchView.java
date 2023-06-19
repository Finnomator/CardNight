package cardnight.games.witch.viewcontroller;

import cardnight.*;
import cardnight.games.SpielView;
import cardnight.games.witch.Witch;
import cardnight.games.witch.WitchGegner;
import cardnight.games.witch.WitchKarte;
import cardnight.games.witch.WitchSpieler;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

public class WitchView extends SpielView {

    public StackPane root;
    public VBox schaetzungsRoot;
    public Button schaetzungsOkButton;
    public HBox gegnerUiHaendeContainer;
    public Label rundenNummerText;
    public Label erhalteneSticheText;
    public GridPane tableGrid;
    public ImageView trumpfImageView;
    public GridPane tischContentContainer;
    public Slider schaetzungsSlider;
    private Witch witch;
    private WitchHauptspielerUiHand hauptspielerUiHand;
    private WitchPunktetafel punktetafel;
    private final AtomicBoolean hatStichSchaetzungBestaetigt = new AtomicBoolean(false);
    private final AtomicBoolean hatKarteGeklickt = new AtomicBoolean(false);
    private final AtomicReference<WitchKarte> geklickteKarte = new AtomicReference<>();
    private HashMap<WitchGegner, WitchGegnerUiHand> gegnerUiHaende;
    private WitchUiStichStapel uiStichStapel;
    public static int anzahlGegner = 5;

    public void initialize() throws IOException {

        WitchKartenBilder.bilderLaden();

        witch = new Witch(anzahlGegner + 1, 1000, this);
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

        uiStichStapel = new WitchUiStichStapel(witch);
        tischContentContainer.add(uiStichStapel, 2, 0);

        hauptspielerUiHand = new WitchHauptspielerUiHand(witch.gibHauptspieler());
        GridPane.setHalignment(hauptspielerUiHand, HPos.CENTER);
        tableGrid.add(hauptspielerUiHand, 0, 2);

        punktetafel = new WitchPunktetafel(witch);
        StackPane.setAlignment(punktetafel, Pos.CENTER_RIGHT);
        root.getChildren().add(punktetafel);

        root.addEventFilter(WitchKartenKlickEvent.ANY, this::handleWitchKartenClick);

        schaetzungsSlider.valueProperty().addListener((obs, oldval, newVal) ->
                schaetzungsSlider.setValue(Math.round(newVal.doubleValue())));

        witch.game();
    }

    private void handleWitchKartenClick(WitchKartenKlickEvent event) {
        hatKarteGeklickt.set(true);
        geklickteKarte.set(event.geklickteKarte);
    }

    public int warteAufSchaetzung() {

        Logger.log("\t\tWarte auf Schätzung vom Spieler...");

        Platform.runLater(() -> {
            schaetzungsSlider.setMax(witch.gibRundenNummer() + 1);
            hauptspielerUiHand.disableAllCards();
            schaetzungsRoot.setDisable(false);
        });

        hatStichSchaetzungBestaetigt.set(false);

        while (!hatStichSchaetzungBestaetigt.get())
            Witch.delay(50);

        Platform.runLater(() -> schaetzungsRoot.setDisable(true));

        return (int) schaetzungsSlider.getValue();
    }

    public WitchKarte warteAufKartenauswahl() {
        // Wartet, bis der Spieler eine Karte geklickt hat, die er ablegen will und gibt diese zurück

        Logger.log("\t\t\tWarte bis Spieler Karte ausgewählt hat...");

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

        String nachricht = "Platzierung:";

        ArrayList<WitchSpieler> sortierteSpieler = witch.platzierung();

        if (sortierteSpieler.get(0) == witch.gibHauptspieler())
            WitchSounds.youWin();
        else
            WitchSounds.rundeVorbei();

        for (int i = 0; i < sortierteSpieler.size(); i++) {
            WitchSpieler s = sortierteSpieler.get(i);
            nachricht += "\t" + (i + 1) + ". " + s.name + " (" + s.gibPunkte() + " Punkte)\n";
        }

        GameOver.setzeNachricht(nachricht);

        root.getChildren().add(GameOver.loadScene());
    }

    @Override
    public void pauseClick() {
        SoundPlayer.klickSound();
        root.getChildren().add(PauseMenu.loadScene());
    }

    public static void showScene() throws IOException {
        ScreenController.show(new FXMLLoader(WitchView.class.getResource("/cardnight/game-views/witch-view.fxml")).load());
    }

    public void schaetzungOkKlick() {
        SoundPlayer.klickSound();
        hatStichSchaetzungBestaetigt.set(true);
    }
}

