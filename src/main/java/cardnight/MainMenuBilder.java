package cardnight;

import javafx.scene.image.Image;

public class MainMenuBilder {

    private static Image ticTacToeHintergrund;
    private static Image uenoHintergrund;
    private static Image witchHintergrund;
    private static Image tttButton;
    private static Image tttButtonHovered;
    private static Image uenoButton;
    private static Image uenoButtonHovered;
    private static Image witchButton;
    private static Image witchButtonHovered;
    private static Image beendenButton;
    private static Image beendenButtonHovered;

    public static void ladeBilder() {
        ticTacToeHintergrund = ladeBild("hintergruende/TicTacToe_Background.png");
        uenoHintergrund = ladeBild("hintergruende/UNO_Background.png");
        witchHintergrund = ladeBild("hintergruende/Witch_Background.png");

        tttButton = ladeBild("button-hintergruende/TicTacToe_Button.png", 0, 70);
        tttButtonHovered = ladeBild("button-hintergruende/TicTacToe_Button_hovered.png", 0, 70);
        uenoButton = ladeBild("button-hintergruende/UNO_Button.png", 0, 70);
        uenoButtonHovered = ladeBild("button-hintergruende/UNO_Button_hovered.png", 0, 70);
        witchButton = ladeBild("button-hintergruende/Witch_Button.png", 0, 70);
        witchButtonHovered = ladeBild("button-hintergruende/Witch_Button_hovered.png", 0, 70);
        beendenButton = ladeBild("button-hintergruende/End_Button.png", 0, 70);
        beendenButtonHovered = ladeBild("button-hintergruende/End_Button_hovered.png", 0, 70);
    }

    public static Image gibButtonBild(SpielTyp spiel, boolean hovered) {
        switch (spiel) {
            case TIC_TAC_TOE:
                return hovered? tttButtonHovered : tttButton;
            case UENO:
                return hovered? uenoButtonHovered : uenoButton;
            case WITCH:
                return hovered? witchButtonHovered : witchButton;
        }

        throw new RuntimeException();
    }

    public static Image beendenButtonBild(boolean hovered) {
        return hovered? beendenButtonHovered : beendenButton;
    }

    public static Image gibHintergrundBild(SpielTyp spiel) {
        switch (spiel) {

            case TIC_TAC_TOE:
                return ticTacToeHintergrund;
            case UENO:
                return uenoHintergrund;
            case WITCH:
                return witchHintergrund;
        }

        throw new RuntimeException();
    }

    private static Image ladeBild(String pfad) {
        return ladeBild(pfad, 0, 0);
    }

    private static Image ladeBild(String pfad, double breite, double hoehe) {
        return new Image(Main.class.getResourceAsStream("/cardnight/images/" + pfad), breite, hoehe, true, true);
    }
}
