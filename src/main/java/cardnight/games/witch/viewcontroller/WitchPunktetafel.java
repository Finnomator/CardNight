package cardnight.games.witch.viewcontroller;

import cardnight.games.Spieler;
import cardnight.games.witch.Witch;
import cardnight.games.witch.WitchSpieler;
import javafx.scene.layout.GridPane;
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

        for (int zeile = 0; zeile < anzahlZeilen; ++zeile)
            root.add(new Text(String.valueOf(zeile + 1)), 0, zeile + 1);

        for (int spalte = 0; spalte < spieler.length; spalte++) {
            WitchSpieler s = spieler[spalte];

            root.add(new Text(s.name), spalte + 1, 0);

            for (int zeile = 0; zeile < spiel.gibRundenNummer(); ++zeile) {

                Text uiText = new Text(String.valueOf(s.gibPunkte(zeile)));

                root.add(uiText, spalte + 1, zeile + 1);
            }
        }

        root.add(new Text("Total"), 0, anzahlZeilen + 1);

        for (int spalte = 0; spalte < spieler.length; spalte++)
            root.add(new Text(String.valueOf(spieler[spalte].gibPunkte())), spalte + 1, anzahlZeilen + 1);
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
