package cardnight.games.witch.viewcontroller;

import cardnight.games.witch.Witch;
import cardnight.games.witch.WitchSpieler;
import javafx.geometry.HPos;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class WitchPunktetafel {

    public GridPane root;
    private WitchSpieler[] spieler;
    private Witch spiel;

    public void uiErstellen(Witch spiel) {
        this.spiel = spiel;
        spieler = spiel.gibSpieler();
        updateUi();
    }

    public void updateUi() {
        //TODO: die Anzahl der geschätzten Stiche neben die Punktezahl schreiben (am besten direkt nach Schätzung)
        int anzahlZeilen = berechneZeilenAnzahl(spieler);

        root.getChildren().clear();

        linienErstellen(anzahlZeilen);
        bezeichnungenEintragen(anzahlZeilen);
        punkteEintragen(anzahlZeilen);
    }

    private void punkteEintragen(int anzahlZeilen) {
        for (int spalte = 0; spalte < spieler.length; spalte++) {
            WitchSpieler s = spieler[spalte];

            for (int zeile = 0; zeile < spiel.gibRundenNummer(); ++zeile) {
                Text uiText = new Text(String.valueOf(s.gibPunkte(zeile)));
                uiText.setFont(new Font(9));
                GridPane.setHalignment(uiText, HPos.CENTER);
                root.add(uiText, (spalte + 1) * 4, (zeile + 1) * 2);
                Text text = new Text(String.valueOf(s.gibStichSchaetzung(zeile)));
                text.setFont(new Font(9));
                root.add(text, (spalte + 1) * 4 + 2, (zeile + 1) * 2);
            }
        }


        for (int spalte = 0; spalte < spieler.length; spalte++) {
            Text text = new Text(String.valueOf(spieler[spalte].gibPunkte()));
            GridPane.setHalignment(text, HPos.RIGHT);
            root.add(text, (spalte + 1) * 4, (anzahlZeilen + 1) * 2);
        }
    }

    private void bezeichnungenEintragen(int anzahlZeilen) {
        for (int zeile = 0; zeile < anzahlZeilen; ++zeile) {
            Text text = new Text(String.valueOf(zeile + 1));
            text.setFont(new Font(9));
            root.add(text, 0, (zeile + 1) * 2);
        }

        root.add(new Text("Total"), 0, (anzahlZeilen + 1) * 2);

        for (int spalte = 0; spalte < spieler.length; spalte++) {
            WitchSpieler s = spieler[spalte];
            root.add(new Text(s.name), (spalte + 1) * 4, 0);
        }
    }

    private void linienErstellen(int anzahlZeilen) {

        AnchorPane pane = new AnchorPane();
        pane.setStyle("-fx-background-color: black");
        pane.setPrefWidth(1);
        GridPane.setRowSpan(pane, Integer.MAX_VALUE);
        root.add(pane, 1, 0);

        AnchorPane pane2 = new AnchorPane();
        pane2.setStyle("-fx-background-color: black");
        pane2.setPrefHeight(1);
        GridPane.setColumnSpan(pane2, Integer.MAX_VALUE);
        root.add(pane2, 0, 1);

        for (int spalte = 0; spalte < spieler.length; spalte++) {
            int[] is = {1, 3};
            for (int i : is) {
                AnchorPane pane1 = new AnchorPane();
                pane1.setStyle("-fx-background-color: black");
                pane1.setPrefWidth(1);
                GridPane.setRowSpan(pane1, anzahlZeilen * 2 + (i + 1));
                root.add(pane1, (spalte + 1) * 4 + i, 0);
            }
        }

        for (int zeile = 0; zeile < anzahlZeilen; ++zeile) {
            AnchorPane pane1 = new AnchorPane();
            pane1.setStyle("-fx-background-color: black");
            pane1.setPrefHeight(1);
            GridPane.setColumnSpan(pane1, Integer.MAX_VALUE);
            root.add(pane1, 0, (zeile + 1) * 2 + 1);
        }
    }

    private int berechneZeilenAnzahl(WitchSpieler[] spieler) {
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
