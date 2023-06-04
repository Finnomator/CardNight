package cardnight.games.ueno.viewcontroler;

import cardnight.GameOver;
import cardnight.Main;
import cardnight.PauseMenu;
import cardnight.games.SpielView;
import cardnight.games.ueno.Ueno;
import cardnight.games.ueno.UenoGegner;
import cardnight.games.ueno.UenoKarte;
import cardnight.games.ueno.UenoSpieler;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.geometry.HPos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class UenoView extends SpielView {

    public Button nachziehstapelButton;
    public StackPane root;
    public Text gewinnerText;
    public HBox gegnerHaendeContainer;
    public Circle hauptSpielerTurnIndicator;
    public ImageView ablagestapelImageView;
    public GridPane tableGrid;
    private Ueno ueno;
    private HashMap<UenoSpieler, UenoUiHand> spielerHaende;
    private UenoSpieler hauptSpieler;

    public void initialize() throws IOException {

        int gegnerAnzahl = 3;

        ueno = new Ueno(gegnerAnzahl + 1, 7);
        Main.setzeAktuellesSpiel(ueno);
        UenoKartenBilder.ladeBilder();

        hauptSpieler = ueno.gibHauptSpieler();
        spielerHaende = new HashMap<>(gegnerAnzahl + 1);

        for (int i = 0; i < gegnerAnzahl; ++i) {
            FXMLLoader handLoader = new FXMLLoader(getClass().getResource("/cardnight/game-views/ueno/gegner-hand.fxml"));
            Node uiHand = handLoader.load();
            gegnerHaendeContainer.getChildren().add(uiHand);
            UenoGegnerUiHand hand = handLoader.getController();
            hand.uiErstellen(ueno.gibSpieler(i+1));
            spielerHaende.put(ueno.gibSpieler(i+1), hand);
        }

        FXMLLoader handLoader = new FXMLLoader(getClass().getResource("/cardnight/game-views/ueno/hauptspieler-hand.fxml"));
        Node uiHauptHand = handLoader.load();
        GridPane.setRowIndex(uiHauptHand, 2);
        GridPane.setHalignment(uiHauptHand, HPos.CENTER);
        tableGrid.getChildren().add(uiHauptHand);
        UenoHauptspielerUiHand hauptHand = handLoader.getController();
        hauptHand.uiErstellen(hauptSpieler);
        spielerHaende.put(hauptSpieler, hauptHand);

        root.addEventFilter(UenoKarteKlickEvent.ANY, this::handleUenoKartenKlick);

        updateUi();
    }

    // Basic Methoden für alle Spieler

    private void zieheKarten(UenoSpieler spieler, int nKarten) {
        ueno.nKartenNachziehen(spieler, nKarten);
        updateUi();
    }

    private void legeKarte(UenoSpieler spieler, UenoKarte karte) {
        ueno.karteAblegen(spieler, karte);
        updateUi();
    }

    // Gegner Logik

    private void gegnerZug(UenoGegner gegner) {

        if (ueno.mussAussetzen()) {
            System.out.println(gegner.name + " wird übersprungen");
            return;
        }

        if (ueno.mussVierZiehen())
            zieheKarten(gegner, 4);
        else if (ueno.mussZweiZiehen())
            zieheKarten(gegner, 2);

        try {
            Thread.sleep(1500);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        if (gegner.kannKarteAblegen()) {
            legeKarte(gegner, gegner.whaeleKarteZumAblegen());
        } else {
            System.out.println(gegner.name + " konnte nicht ablegen");
            zieheKarten(gegner, 1);
            if (gegner.kannKarteAblegen())
                legeKarte(gegner, gegner.whaeleKarteZumAblegen());
            else
                System.out.println(gegner.name + " konnte wieder nicht ablegen");
        }
    }

    private void gegnerZuege() {
        new Thread(() -> {

            UenoSpieler naechster = ueno.nachsterSpieler(hauptSpieler);
            while (naechster != hauptSpieler) {
                gegnerZug((UenoGegner) naechster);

                if (ueno.istSpielBeendet()) {
                    beendeSpiel();
                    return;
                }

                naechster = ueno.nachsterSpieler(naechster);
            }

            updateUi();

            // Dinge tun, bevor der Spieler wieder Karten legen kann
            UenoSpieler spieler = hauptSpieler;
            if (ueno.mussAussetzen()) {
                System.out.println(spieler.name + " musste aussetzen");
                gegnerZuege();
            } else if (ueno.mussVierZiehen())
                zieheKarten(spieler, 4);
            else if (ueno.mussZweiZiehen())
                zieheKarten(spieler, 2);

            nachziehstapelButton.setDisable(false);
            hauptSpielerTurnIndicator.setFill(Color.GREEN);

        }).start();
    }

    // ÜNO Ui Interaktion

    public void handleUenoKartenKlick(UenoKarteKlickEvent event) {

        // Spieler legt eine Karte
        legeKarte(hauptSpieler, event.geklickteKarte);

        hauptSpielerTurnIndicator.setFill(Color.RED);

        // Gegner machen ihre Züge
        gegnerZuege();

        if (ueno.istSpielBeendet())
            beendeSpiel();
    }

    public void nachziehstapelClick() {

        zieheKarten(hauptSpieler, 1);
        nachziehstapelButton.setDisable(true);

        if (!hauptSpieler.kannKarteAblegen()) {
            System.out.println(hauptSpieler.name + " konnte wieder nicht legen");
            hauptSpielerTurnIndicator.setFill(Color.RED);
            gegnerZuege();
            return;
        }

        UenoKarte ablegbareKarte = hauptSpieler.ablegbareKarten().get(0);
        if (ablegbareKarte.istSchwarz())
            ablegbareKarte.setzeFarbe(ueno.gibZuletztAbgelegteKarte().farbe);
        updateUi();

        System.out.println("Spieler zog nach und sollte folgende Karte legen können:");
        System.out.println("\t" + ablegbareKarte.datenAlsString());
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
    public void pauseClick() throws IOException {
        root.getChildren().add(PauseMenu.loadScene());
    }

    @Override
    public void beendeSpiel() {

        System.out.println("Das Spiel ist vorbei, die Gewinner:");
        ArrayList<UenoSpieler> gewinner = ueno.gibGewinner();

        for (int i = 0; i < gewinner.size(); ++i)
            System.out.println((i+1) + ".\t" + gewinner.get(i).name);

        gewinnerText.setText(gewinner.get(0).name + " hat gewonnen");

        try {
            root.getChildren().add(GameOver.loadScene());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static Pane loadScene() throws IOException {
        return new FXMLLoader(UenoView.class.getResource("/cardnight/game-views/ueno-view.fxml")).load();
    }
}
