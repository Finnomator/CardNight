package cardnight.games.tictactoe.viewcontroller;

import cardnight.games.tictactoe.TicTacToeSpieler;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.HBox;

import java.io.IOException;

public class TTTUiOHand extends TTTUiHand {
    public HBox kartenBox;

    @Override
    public void uiErstellen(TicTacToeSpieler spieler) {
        this.spieler = spieler;
        updateUi();
    }

    @Override
    public void updateUi() {

        kartenBox.setDisable(!spieler.istAmZug());

        kartenBox.getChildren().clear();

        for (int i = 0; i < spieler.gibAnzahlHandKarten(); ++i) {
            FXMLLoader loader;
            loader = new FXMLLoader(getClass().getResource("/cardnight/game-views/tictactoe/o-karte.fxml"));
            try {
                kartenBox.getChildren().add(loader.load());
            } catch (IOException ex) {
                throw new RuntimeException();
            }
        }
    }
}
