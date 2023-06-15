package cardnight.games.witch.viewcontroller;

import cardnight.games.witch.Witch;
import cardnight.games.witch.WitchSpieler;
import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

public class WitchPunktetafel extends GridPane {

    private final WitchSpieler[] spieler;
    private final Witch spiel;
    private final ImageView background = new ImageView(WitchKartenBilder.punkteTafelBackground);

    public WitchPunktetafel(Witch spiel) {
        this.spiel = spiel;
        spieler = spiel.gibSpieler();

        background.setOpacity(0.5);
        background.fitHeightProperty().bind(heightProperty().subtract(50));
        background.setPreserveRatio(true);
        setValignment(background, VPos.CENTER);
        setHalignment(background, HPos.CENTER);

        setStyle("-fx-background-color: linear-gradient(to bottom, #1c379b, #032d73); -fx-border-color: white; " +
                "-fx-border-width: 3; -fx-border-radius: 5; -fx-font-family: 'Segoe Script'");
        setMaxSize(Double.NEGATIVE_INFINITY, Double.NEGATIVE_INFINITY);

        updateUi();
    }

    public void updateUi() {
        int anzahlZeilen = berechneZeilenAnzahl(spieler);

        getChildren().clear();

        add(background, 0, 0, 100, 100);

        linienErstellen(anzahlZeilen);
        bezeichnungenEintragen(anzahlZeilen);
        punkteEintragen(anzahlZeilen);
    }

    private void punkteEintragen(int anzahlZeilen) {
        for (int spalte = 0; spalte < spieler.length; spalte++) {
            WitchSpieler s = spieler[spalte];

            for (int zeile = 0; zeile < spiel.gibRundenNummer(); ++zeile) {
                Text uiText = new Text(String.valueOf(s.gibPunkte(zeile)));
                uiText.setFill(Color.WHITE);
                GridPane.setHalignment(uiText, HPos.CENTER);
                add(uiText, (spalte + 1) * 4, (zeile + 1) * 2);
                Text text = new Text(String.valueOf(s.gibStichSchaetzung(zeile)));
                text.setFill(Color.WHITE);
                add(text, (spalte + 1) * 4 + 2, (zeile + 1) * 2);
            }
        }


        for (int spalte = 0; spalte < spieler.length; spalte++) {
            Text text = new Text(String.valueOf(spieler[spalte].gibPunkte()));
            text.setFill(Color.WHITE);
            GridPane.setHalignment(text, HPos.RIGHT);
            add(text, (spalte + 1) * 4, (anzahlZeilen + 1) * 2);
        }
    }

    private void bezeichnungenEintragen(int anzahlZeilen) {
        for (int zeile = 0; zeile < anzahlZeilen; ++zeile) {
            Text text = new Text(String.valueOf(zeile + 1));
            text.setFill(Color.WHITE);
            add(text, 0, (zeile + 1) * 2);
        }

        Text total = new Text("Total");
        total.setFill(Color.WHITE);

        add(total, 0, (anzahlZeilen + 1) * 2);

        for (int spalte = 0; spalte < spieler.length; spalte++) {
            WitchSpieler s = spieler[spalte];
            Text text = new Text(s.name);
            text.setFill(Color.WHITE);
            add(text, (spalte + 1) * 4, 0);
        }
    }

    private void linienErstellen(int anzahlZeilen) {

        AnchorPane pane = new AnchorPane();
        pane.setStyle("-fx-background-color: white");
        pane.setPrefWidth(1);
        GridPane.setRowSpan(pane, Integer.MAX_VALUE);
        add(pane, 1, 0);

        AnchorPane pane2 = new AnchorPane();
        pane2.setStyle("-fx-background-color: white");
        pane2.setPrefHeight(1);
        GridPane.setColumnSpan(pane2, Integer.MAX_VALUE);
        add(pane2, 0, 1);

        for (int spalte = 0; spalte < spieler.length; spalte++) {
            int[] is = {1, 3};
            for (int i : is) {
                AnchorPane pane1 = new AnchorPane();
                pane1.setStyle("-fx-background-color: white");
                pane1.setPrefWidth(1);
                GridPane.setRowSpan(pane1, anzahlZeilen * 2 + (i + 1));
                add(pane1, (spalte + 1) * 4 + i, 0);
            }
        }

        for (int zeile = 0; zeile < anzahlZeilen; ++zeile) {
            AnchorPane pane1 = new AnchorPane();
            pane1.setStyle("-fx-background-color: white");
            pane1.setPrefHeight(1);
            GridPane.setColumnSpan(pane1, Integer.MAX_VALUE);
            add(pane1, 0, (zeile + 1) * 2 + 1);
        }
    }

    private static int berechneZeilenAnzahl(WitchSpieler[] spieler) {
        switch (spieler.length) {
            case 3:
                return 20;
            case 4:
                return 15;
            case 5:
                return 12;
            case 6:
                return 10;
            default:
                throw new RuntimeException();
        }
    }
}
