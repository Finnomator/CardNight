package cardnight.games.ueno.viewcontroler;

import cardnight.ScreenController;
import cardnight.games.ueno.*;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class UenoView {

    public Button nachziehstapelButton;
    public Button ablageStapelButton;
    public StackPane root;
    public Text gewinnerText;
    private Ueno ueno;
    private HashMap<UenoSpieler, UenoUiHand> spielerHaende;

    public void initialize() {

        int gegner = 3;

        spielerHaende = new HashMap<>(gegner + 1);

        ueno = new Ueno(gegner + 1, 7);

        for (int i = 0; i < gegner; ++i) {
            FXMLLoader handLoader = new FXMLLoader(getClass().getResource("/cardnight/game-views/ueno/gegner-hand.fxml"));
            UenoGegnerUiHand hand;
            try {
                HBox uiHand = handLoader.load();
                StackPane.setMargin(uiHand, new Insets(50, 0, 0, i * 200.0));
                root.getChildren().add(uiHand);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            hand = handLoader.getController();
            hand.uiErstellen(ueno.gibSpieler(i+1));
            spielerHaende.put(ueno.gibSpieler(i+1), hand);
        }

        FXMLLoader handLoader = new FXMLLoader(getClass().getResource("/cardnight/game-views/ueno/hauptspieler-hand.fxml"));
        UenoHauptspielerUiHand hauptHand;
        try {
            root.getChildren().add(handLoader.load());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        hauptHand = handLoader.getController();
        hauptHand.uiErstellen(ueno.gibHauptSpieler());
        spielerHaende.put(ueno.gibHauptSpieler(), hauptHand);

        root.addEventFilter(UenoKarteKlickEvent.ANY, this::handleUenoKartenKlick);

        zeigeObersteAbgelegteKarte();
        updateHandkarten();
        updateGegnerKarten();
    }

    public void nachziehstapelClick() {
        zieheKarten(ueno.gibHauptSpieler(), 1);
        nachziehstapelButton.setDisable(true);

        if (!ueno.gibHauptSpieler().kannKarteAblegen()) {
            System.out.println(ueno.gibHauptSpieler().name + " konnte wieder nicht legen");
            gegnerZuege();
        } else {
            System.out.println("Spieler zog nach und sollte folgende Karte legen können:");
            UenoKarte ablegbareKarte = ueno.gibHauptSpieler().ablegbareKarten().get(0);
            if (ablegbareKarte.art == UenoKartenArt.FARBWAHL || ablegbareKarte.art == UenoKartenArt.PLUS_VIER)
                ablegbareKarte.setzeFarbe(ueno.gibZuletztAbgelegteKarte().farbe);
            updateHandkarten();
            System.out.println("\t" + ablegbareKarte.datenAlsString());
        }
    }

    private void zieheKarten(UenoSpieler spieler, int nKarten) {
        ueno.nKartenNachziehen(spieler, nKarten);
        updateGegnerKarten();
        updateHandkarten();
    }

    private void legeKarte(UenoSpieler spieler, UenoKarte karte) {
        ueno.karteAblegen(spieler, karte);
        zeigeObersteAbgelegteKarte();
        updateHandkarten();
        updateGegnerKarten();
    }

    private void zeigeObersteAbgelegteKarte() {
        UenoKarte oberste = ueno.gibZuletztAbgelegteKarte();
        ablageStapelButton.setStyle("-fx-background-color: " + UenoUiKarte.farbeZuString(oberste.farbe) + ";");
        ablageStapelButton.setText(UenoUiKarte.uenoKartenArtZuString(oberste));
    }

    private void gegnerZug(UenoGegner gegner) {

        if (ueno.mussAussetzen()) {
            System.out.println(gegner.name + " wird übersprungen");
            return;
        }

        if (ueno.mussVierZiehen())
            zieheKarten(gegner, 4);
        else if (ueno.mussZweiZiehen())
            zieheKarten(gegner, 2);

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

    public void handleUenoKartenKlick(UenoKarteKlickEvent event) {

        // Spieler legt eine Karte
        legeKarte(ueno.gibHauptSpieler(), event.geklickteKarte);

        // Gegner machen ihre Züge
        gegnerZuege();

        if (ueno.istSpielBeendet()) {
            System.out.println("Das Spiel ist vorbei, die Gewinner:");
            ArrayList<UenoSpieler> gewinner = ueno.gibGewinner();
            for (int i = 0; i < gewinner.size(); ++i)
                System.out.println((i+1) + ".\t" + gewinner.get(i).name);
        }
    }

    private void gegnerZuege() {

        UenoSpieler naechster = ueno.nachsterSpieler(ueno.gibHauptSpieler());
        while (naechster != ueno.gibHauptSpieler()) {
            gegnerZug((UenoGegner) naechster);

            if (ueno.istSpielBeendet())
                return;

            naechster = ueno.nachsterSpieler(naechster);
        }

        // Dinge tun, bevor der Spieler wieder Karten legen kann
        UenoSpieler spieler = ueno.gibHauptSpieler();
        if (ueno.mussAussetzen()) {
            System.out.println(spieler.name + " musste aussetzen");
            gegnerZuege();
        } else if (ueno.mussVierZiehen())
            zieheKarten(spieler, 4);
        else if (ueno.mussZweiZiehen())
            zieheKarten(spieler, 2);

        nachziehstapelButton.setDisable(false);
    }

    private void updateGegnerKarten() {
        for (int i = 1; i < ueno.gibSpieler().length; ++i)
            updateSpielerKarten(ueno.gibSpieler(i));
    }

    private void updateSpielerKarten(UenoSpieler spieler) {
        spielerHaende.get(spieler).updateUi();
    }

    private void updateHandkarten() {
        updateSpielerKarten(ueno.gibHauptSpieler());
    }

    public void zurueckClick() {
        ScreenController.activate("main-menu-view");
    }

    public static Pane loadScene() throws IOException {
        return new FXMLLoader(UenoView.class.getResource("/cardnight/game-views/ueno-view.fxml")).load();
    }
}