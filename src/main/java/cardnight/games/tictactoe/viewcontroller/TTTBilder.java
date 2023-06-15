package cardnight.games.tictactoe.viewcontroller;

import cardnight.Main;
import javafx.scene.image.Image;

public class TTTBilder {
    public static final Image xHandkarte = ladeBild("X_Handkarte.png");
    public static final Image oHandkarte = ladeBild("O_Handkarte.png");

    private static Image ladeBild(String pfad) {
        return new Image(Main.class.getResourceAsStream(
                "/cardnight/game-views/tictactoe/images/" + pfad),
                0, 0, true, true);
    }
}
