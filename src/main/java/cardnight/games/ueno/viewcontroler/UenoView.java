package cardnight.games.ueno.viewcontroler;

import cardnight.*;
import cardnight.games.SpielView;
import cardnight.games.ueno.Ueno;
import cardnight.games.ueno.UenoGegner;
import cardnight.games.ueno.UenoKarte;
import cardnight.games.ueno.UenoSpieler;
import cardnight.games.witch.Witch;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.geometry.HPos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class UenoView extends SpielView {

    public Button nachziehstapelButton;
    public StackPane root;
    public HBox gegnerHaendeContainer;
    public ImageView ablagestapelImageView;
    public GridPane tableGrid;
    private Ueno ueno;
    private HashMap<UenoSpieler, UenoUiHand> spielerHaende;
    private Node uiHauptHand;
    private UenoSpieler hauptSpieler;
    private boolean spielIstBeendet;
    public static int anzahlGegner;
    public static int kartenAnzahl;


    public void initialize() throws IOException {

        ueno = new Ueno(anzahlGegner + 1, kartenAnzahl);
        Main.setzeAktuellesSpiel(ueno);
        UenoKartenBilder.ladeBilder();

        hauptSpieler = ueno.gibHauptSpieler();
        spielerHaende = new HashMap<>(anzahlGegner + 1);

        for (int i = 0; i < anzahlGegner; ++i) {
            FXMLLoader handLoader = new FXMLLoader(getClass().getResource("/cardnight/game-views/ueno/gegner-hand.fxml"));
            Node uiHand = handLoader.load();
            gegnerHaendeContainer.getChildren().add(uiHand);
            UenoGegnerUiHand hand = handLoader.getController();
            hand.uiErstellen(ueno.gibSpieler(i + 1));
            spielerHaende.put(ueno.gibSpieler(i + 1), hand);
        }

        UenoHauptspielerUiHand hauptHand = new UenoHauptspielerUiHand(hauptSpieler);
        uiHauptHand = hauptHand.root();
        GridPane.setHalignment(uiHauptHand, HPos.CENTER);
        tableGrid.add(uiHauptHand, 0, 2);
        spielerHaende.put(hauptSpieler, hauptHand);

        root.addEventFilter(UenoKarteKlickEvent.ANY, this::handleUenoKartenKlick);

        updateUi();

        disableGame(true);
        UenoSoundPlayer.start(false);

        new Thread(() -> {
            Witch.delay(3000);
            disableGame(false);
        }).start();
    }

    // Basic Methoden für alle Spieler

    private void zieheKarten(UenoSpieler spieler, int nKarten) {
        ueno.nKartenNachziehen(spieler, nKarten);
        updateUi();
    }

    private void legeKarte(UenoSpieler spieler, UenoKarte karte) {

        switch (karte.art) {
            case RICHTUNGSWECHSEL:
                UenoSoundPlayer.reverse();
                break;
            case AUSSETZEN:
                UenoSoundPlayer.skip();
                break;
            case PLUS_VIER:
                UenoSoundPlayer.vierZiehen();
                break;
            case PLUS_ZWEI:
                UenoSoundPlayer.zweiZiehen();
                break;
            case FARBWAHL:
                UenoSoundPlayer.farbwahl(karte.farbe);
                break;
        }

        ueno.karteAblegen(spieler, karte);
        updateUi();
    }

    private void disableGame(boolean disable) {
        Platform.runLater(() -> {
            uiHauptHand.setDisable(disable);
            nachziehstapelButton.setDisable(disable);
        });
    }

    // Gegner Logik

    private void gegnerZug(UenoGegner gegner) {

        if (ueno.mussAussetzen()) {
            Logger.log(gegner.name + " wird übersprungen");
            return;
        }

        if (ueno.mussVierZiehen()) {
            if (gegner.gibHandkarten().size() == 1 || gegner.gibHandkarten().size() > 12) // Da wär ich aber auch mad D:
                UenoSoundPlayer.fYou();
            zieheKarten(gegner, 4);
        } else if (ueno.mussZweiZiehen())
            zieheKarten(gegner, 2);

        updateUi();

        try {
            Thread.sleep(1500);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        if (gegner.kannKarteAblegen()) {
            legeKarte(gegner, gegner.whaeleKarteZumAblegen());

            if (gegner.istFertig()) {
                ueno.fuegeFertigenSpielerHinzu(gegner);
                UenoSoundPlayer.unoUno();
            } else if (gegner.gibHandkarten().size() == 1)
                UenoSoundPlayer.uno();

        } else {
            Logger.log(gegner.name + " konnte nicht ablegen");
            zieheKarten(gegner, 1);
            if (gegner.kannKarteAblegen())
                legeKarte(gegner, gegner.whaeleKarteZumAblegen());
            else
                Logger.log(gegner.name + " konnte wieder nicht ablegen");
        }
    }

    private void gegnerZuege() {
        disableGame(true);

        UenoSpieler naechster = ueno.nachsterSpieler(hauptSpieler);
        while (naechster != hauptSpieler) {
            gegnerZug((UenoGegner) naechster);

            if (ueno.istSpielBeendet()) {
                Platform.runLater(this::beendeSpiel);
                return;
            }

            naechster = ueno.nachsterSpieler(naechster);
        }

        updateUi();

        // Dinge tun, bevor der Spieler wieder Karten legen kann
        UenoSpieler spieler = hauptSpieler;
        if (ueno.mussAussetzen()) {
            Logger.log(spieler.name + " musste aussetzen");
            gegnerZuege();
        } else if (ueno.mussVierZiehen())
            zieheKarten(spieler, 4);
        else if (ueno.mussZweiZiehen())
            zieheKarten(spieler, 2);

        disableGame(false);
    }

    private void gegnerZuegeAsync() {
        Thread t = new Thread(this::gegnerZuege);
        t.setDaemon(true);
        t.start();
    }

    // ÜNO Ui Interaktion

    public void handleUenoKartenKlick(UenoKarteKlickEvent event) {

        disableGame(true);

        // Spieler legt eine Karte
        legeKarte(hauptSpieler, event.geklickteKarte);

        if (ueno.istSpielBeendet()) {
            beendeSpiel();
            return;
        }

        // Gegner machen ihre Züge
        gegnerZuegeAsync();

        if (ueno.istSpielBeendet())
            beendeSpiel();
    }

    public void nachziehstapelClick() {

        zieheKarten(hauptSpieler, 1);
        nachziehstapelButton.setDisable(true);

        if (!hauptSpieler.kannKarteAblegen()) {
            Logger.log(hauptSpieler.name + " konnte wieder nicht legen");
            gegnerZuegeAsync();
            return;
        }

        ArrayList<UenoKarte> ablegbareKarten = hauptSpieler.ablegbareKarten();

        updateUi();

        Logger.log("Spieler zog nach und sollte folgende Karte(n) legen können:");
        for (UenoKarte ablegbareKarte : ablegbareKarten)
            Logger.log("\t" + ablegbareKarte.datenAlsString());
    }

    // ÜNO Ui Update Methoden

    private void updateUi() {
        Platform.runLater(() -> {
            // Update Spieler
            for (UenoSpieler spieler : ueno.gibSpieler())
                spielerHaende.get(spieler).updateUi();

            // Zeige oberste abgelegte Karte
            UenoKarte oberste = ueno.gibZuletztAbgelegteKarte();

            ablagestapelImageView.setImage(UenoKartenBilder.karteZuBild(oberste));
        });
    }

    // Generelle Ui Interaktion

    @Override
    public void pauseClick() {
        SoundPlayer.klickSound();
        root.getChildren().add(PauseMenu.loadScene());
    }

    @Override
    public void beendeSpiel() {
        if (spielIstBeendet)
            return;

        spielIstBeendet = true;

        disableGame(true);

        Logger.log("Das Spiel ist vorbei, die Gewinner:");
        ArrayList<UenoSpieler> gewinner = ueno.gibGewinner();

        for (int i = 0; i < gewinner.size(); ++i)
            Logger.log((i + 1) + ".\t" + gewinner.get(i).name);

        if (gewinner.get(0) == hauptSpieler) {
            UenoSoundPlayer.duHastGewonnen();
            GameOver.setzeNachricht("Du hast gewonnen!");
        } else {
            UenoSoundPlayer.rundeVorbei();
            String nachricht = "Die Gewinner:";
            for (int i = 0; i < gewinner.size(); ++i)
                nachricht += "\n" + (i + 1) + ".\t" + gewinner.get(i).name;
            GameOver.setzeNachricht(nachricht);
        }

        root.getChildren().add(GameOver.loadScene());
    }

    public static void showScene() throws IOException {
        ScreenController.show(new FXMLLoader(UenoView.class.getResource("/cardnight/game-views/ueno-view.fxml")).load());
    }
}
