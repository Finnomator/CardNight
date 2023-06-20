package cardnight.games.tictactoe.viewcontroller;

import cardnight.*;
import cardnight.games.SpielView;
import cardnight.games.Spieler;
import cardnight.games.tictactoe.TicTacToe;
import cardnight.games.tictactoe.TicTacToeSpieler;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;

import java.io.IOException;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

public class TicTacToeView extends SpielView {
    public StackPane root;

    public GridPane tttFeld;
    public GridPane tableGrid;
    public HBox handContainer;

    private TicTacToe ttt;
    private final AtomicBoolean zugGemacht = new AtomicBoolean();
    private final AtomicInteger gesetztesFeld = new AtomicInteger();
    private Button[] feldBtns;
    private TTTGegnerUiHand gegnerUiHand;
    private TTTUiHand uiXHand;
    private TTTUiHand uiOHand;
    protected static boolean spielGegenComputer;
    private Image XQuadratisch;
    private Image OQuadratisch;

    @Override
    public void initialize() throws IOException {

        feldBtns = new Button[9];
        OQuadratisch = new Image(getClass().getResourceAsStream("/cardnight/game-views/tictactoe/images/O_quadratisch.png"),
                88, 0, true, true);
        XQuadratisch = new Image(getClass().getResourceAsStream("/cardnight/game-views/tictactoe/images/X_quadratisch.png"),
                88, 0, true, true);

        ttt = new TicTacToe(this, spielGegenComputer);
        Main.setzeAktuellesSpiel(ttt);

        uiXHand = new TTTUiHand(ttt.xSpieler);
        handContainer.getChildren().add(uiXHand);

        if (spielGegenComputer) {
            FXMLLoader gegnerHandLoader = new FXMLLoader(getClass().getResource("/cardnight/game-views/tictactoe/gegner-hand.fxml"));

            Node gegnerHandNode = gegnerHandLoader.load();
            gegnerUiHand = gegnerHandLoader.getController();
            gegnerUiHand.uiErstellen(ttt.oSpieler);
            GridPane.setMargin(gegnerHandNode, new Insets(50, 0, 0, 0));
            tableGrid.add(gegnerHandNode, 0, 0);
        } else {
            uiOHand = new TTTUiHand(ttt.oSpieler);
            handContainer.getChildren().add(uiOHand);
        }


        ttt.spielAblauf();

        int i = 0;
        for (Node node : tttFeld.getChildren()) {
            if (!(node instanceof Button))
                continue;

            Button btn = (Button) node;
            btn.setPadding(new Insets(0.001));
            feldBtns[i] = btn;

            btn.setUserData(i);
            btn.setOnAction(this::btnAction);
            ++i;
        }

        if (spielGegenComputer && SoundPlayer.soundVolume > 0) {
            disableGame(true);
            TTTSoundPlayer.start();

            new Thread(() -> {
                delay(3500);
                disableGame(false);
            }).start();
        }
    }

    public void updateUi() {

        Platform.runLater(() -> {

            uiXHand.updateUi();

            if (spielGegenComputer)
                gegnerUiHand.updateUi();
            else
                uiOHand.updateUi();

            String[] feld = ttt.gibFeld();

            for (int i = 0; i < 9; ++i) {
                if ("x".equals(feld[i]))
                    feldBtns[i].setGraphic(new ImageView(XQuadratisch));
                else if ("o".equals(feld[i]))
                    feldBtns[i].setGraphic(new ImageView(OQuadratisch));

                feldBtns[i].setMouseTransparent((spielGegenComputer && ttt.oSpieler.istAmZug()) || "x".equals(feld[i]) || "o".equals(feld[i]));
            }
        });
    }

    public int warteAufSpielerZug(TicTacToeSpieler spieler) {
        // Gibt zurück, auf welches Feld der Spieler sein Zug macht.

        Logger.log("Warte auf Eingabe von " + spieler.name);

        zugGemacht.set(false);

        while (!zugGemacht.get())
            delay(50);

        return gesetztesFeld.get();
    }

    public static void delay(long millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    private void btnAction(ActionEvent actionEvent) {
        Button src = (Button) actionEvent.getSource();
        src.setMouseTransparent(true);

        gesetztesFeld.set((Integer) src.getUserData());
        zugGemacht.set(true);

        actionEvent.consume();
    }

    public void disableGame(boolean disable) {
        Platform.runLater(() -> {
            tableGrid.setDisable(disable);
            handContainer.setOpacity(disable ? 0.5 : 1);
        });
    }

    @Override
    public void beendeSpiel() {

        disableGame(true);

        Spieler gewinner = ttt.gibGewinner();

        if (gewinner == null) {
            Logger.log("Das Spiel ist vorbei, Unentschieden!");
            Platform.runLater(() -> GameOver.setzeNachricht("UNENTSCHIEDEN!"));
        } else {
            Logger.log("Das Spiel ist vorbei, der Gewinner: " + gewinner.name);
            Platform.runLater(() -> {
                if (spielGegenComputer) {
                    if (gewinner == ttt.oSpieler) {
                        gegnerUiHand.setHappy(true);
                        GameOver.setzeNachricht("Der Computer hat dich geschlagen!");
                    } else {
                        TTTSoundPlayer.verloren();
                        GameOver.setzeNachricht("Du hast den Computer geschlagen!");
                    }
                } else
                    GameOver.setzeNachricht(gewinner.name + " hat gewonnen!");
            });
        }

        delay(1000);

        Platform.runLater(() -> root.getChildren().add(GameOver.loadScene()));
    }

    @Override
    public void pauseClick() {
        SoundPlayer.klickSound();
        root.getChildren().add(PauseMenu.loadScene());
    }

    public static void showScene() throws IOException {
        ScreenController.show(new FXMLLoader(TicTacToeView.class.getResource("/cardnight/game-views/tictactoe-view.fxml")).load());
    }
}
