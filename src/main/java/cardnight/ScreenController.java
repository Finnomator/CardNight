package cardnight;

import cardnight.games.tictactoe.viewcontroller.TTTGegnerWahl;
import cardnight.games.ueno.viewcontroller.UenoGegnerWahl;
import cardnight.games.witch.viewcontroller.WitchGegnerWahl;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;

public class ScreenController {

    public static final Pane tttGegnerWahl = TTTGegnerWahl.loadScene();
    public static final Pane uenoGegnerWahl = UenoGegnerWahl.loadScene();
    public static final Pane witchGegnerWahl = WitchGegnerWahl.loadScene();

    private static Scene main;

    public static void setScene(Scene scene) {
        main = scene;
    }

    public static void show(Pane pane) {
        main.setRoot(pane);
    }
}

