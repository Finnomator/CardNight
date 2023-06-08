package cardnight;

import cardnight.games.Spiel;
import cardnight.games.tictactoe.TicTacToe;
import cardnight.games.ueno.Ueno;
import cardnight.games.witch.Witch;
import javafx.scene.image.Image;

public class MainMenuBilder {

    private static Image ticTacToeHintergrund;
    private static Image uenoHintergrund;
    private static Image witchHintergrund;
    private static Image defaultHintergrund;

    public static void ladeBilder() {
        ticTacToeHintergrund = ladeBild("hintergruende/TicTacToe_Background.png");
    }

    public static Image gibDefaultHintergrund() {
        return defaultHintergrund;
    }

    public static Image gibHintergrundBild(SpielTyp spiel) {
        if (spiel == SpielTyp.TIC_TAC_TOE)
            return ticTacToeHintergrund;

        if (spiel == SpielTyp.UENO)
            return null;

        if (spiel == SpielTyp.WITCH)
            return null;

        throw new RuntimeException();
    }

    private static Image ladeBild(String pfad) {
        return new Image(Main.class.getResourceAsStream("/cardnight/images/" + pfad));
    }
}
