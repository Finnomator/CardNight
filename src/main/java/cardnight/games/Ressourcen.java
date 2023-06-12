package cardnight.games;

import cardnight.Main;
import javafx.scene.image.Image;

public class Ressourcen {
    public static final Image stickmanImage = ladeBild("game-views/images/stickman.png");
    public static final Image stickmanHappyImage = ladeBild("game-views/images/stickman-happy.png");

    // Alte Designs

    public static final Image dieDiebin = ladeBild("alte-designs/Witch_1_gelb.png", Main.HANDKARTE_BREITE, 0);
    public static final Image einWuerdigerGegner = ladeBild("alte-designs/IPS_Gegen_Mensch.png", 0, 100);

    private static Image ladeBild(String pfad) {
        return new Image(Main.class.getResourceAsStream("/cardnight/" + pfad));
    }

    private static Image ladeBild(String pfad, double breite, double hoehe) {
        return new Image(Main.class.getResourceAsStream("/cardnight/" + pfad), breite, hoehe, true, true);
    }
}
