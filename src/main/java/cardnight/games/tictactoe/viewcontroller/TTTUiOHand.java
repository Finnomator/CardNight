package cardnight.games.tictactoe.viewcontroller;

import cardnight.Main;
import cardnight.games.tictactoe.TicTacToeSpieler;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;

public class TTTUiOHand extends TTTUiHand {
    
    public static final Image oHandkartenBild = new Image(Main.class.getResourceAsStream(
            "/cardnight/game-views/tictactoe/images/O_Handkarte.png"),
            Main.HANDKARTE_BREITE, 0, true, true);

    public TTTUiOHand(TicTacToeSpieler spieler) {
        super(spieler);
    }

    @Override
    public void updateUi() {

        setOpacity(spieler.istAmZug()? 1 : 0.5);

        getChildren().clear();

        for (int i = 0; i < spieler.gibAnzahlHandKarten(); ++i)
            getChildren().add(new ImageView(oHandkartenBild));
    }
}
