package cardnight.games.tictactoe.viewcontroller;

import cardnight.*;
import cardnight.games.SpielView;
import cardnight.games.Spieler;

import cardnight.games.tictactoe.TicTacToe;
import cardnight.games.tictactoe.TicTacToeSpieler;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import javafx.event.ActionEvent;

import java.io.IOException;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

public class TicTacToeView extends SpielView {
    public StackPane root;

    public Text gewinnerText;
    public GridPane tttFeld;
    public GridPane tableGrid;
    public HBox handContainer;

    private TicTacToe ttt;
    private AtomicBoolean zugGemacht = new AtomicBoolean();
    private AtomicInteger gesetztesFeld = new AtomicInteger();
    private Button[] feldBtns;
    private TTTGegnerUiHand gegnerUiHand;
    private TTTUiXHand uiXHand;
    private TTTUiOHand uiOHand;
    protected static boolean spielGegenComputer;
    private Image XQuadratisch;
    private Image OQuadratisch;

    @Override
    public void initialize() throws IOException {

        feldBtns = new Button[9];
        OQuadratisch = new Image(getClass().getResourceAsStream("/cardnight/game-views/tictactoe/images/O_quadratisch.png"),
                87, 0, true, true);
        XQuadratisch = new Image(getClass().getResourceAsStream("/cardnight/game-views/tictactoe/images/X_quadratisch.png"),
            87, 0, true, true);

        TTTSoundPlayer.ladeSounds();

        ttt = new TicTacToe(this, spielGegenComputer);
        Main.setzeAktuellesSpiel(ttt);

        FXMLLoader xHandLoader = new FXMLLoader(getClass().getResource("/cardnight/game-views/tictactoe/x-hand.fxml"));
        Node xHandNode = xHandLoader.load();
        uiXHand = xHandLoader.getController();
        uiXHand.uiErstellen(ttt.xSpieler);
        handContainer.getChildren().add(xHandNode);

        if (spielGegenComputer) {
            FXMLLoader gegnerHandLoader = new FXMLLoader(getClass().getResource("/cardnight/game-views/tictactoe/gegner-hand.fxml"));

            Node gegnerHandNode = gegnerHandLoader.load();
            gegnerUiHand = gegnerHandLoader.getController();
            gegnerUiHand.uiErstellen(ttt.oSpieler);
            GridPane.setMargin(gegnerHandNode, new Insets(50, 0, 0, 0));
            tableGrid.add(gegnerHandNode, 0, 0);
        } else {
            FXMLLoader oHandLoader = new FXMLLoader(getClass().getResource("/cardnight/game-views/tictactoe/o-hand.fxml"));
            Node oHandNode = oHandLoader.load();
            uiOHand = oHandLoader.getController();
            uiOHand.uiErstellen(ttt.oSpieler);
            handContainer.getChildren().add(oHandNode);
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
                if (feld[i].equals("x"))
                    feldBtns[i].setGraphic(new ImageView(XQuadratisch));
                else if (feld[i].equals("o"))
                    feldBtns[i].setGraphic(new ImageView(OQuadratisch));

                feldBtns[i].setMouseTransparent((spielGegenComputer && ttt.oSpieler.istAmZug()) || feld[i].equals("x") || feld[i].equals("o"));
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

    @Override
    public void beendeSpiel() {

        Spieler gewinner = ttt.gibGewinner();

        if (gewinner == null) {
            Logger.log("Das Spiel ist vorbei, Unentschieden!");
            Platform.runLater(() -> GameOver.setzeNachricht("UNENTSCHIEDEN!"));
        } else {
            Logger.log("Das Spiel ist vorbei, der Gewinner: " + gewinner.name);
            Platform.runLater(() -> {
                gewinnerText.setText(gewinner.name + " hat gewonnen");

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

        Platform.runLater(() -> {
            try {
                root.getChildren().add(GameOver.loadScene());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }

    @Override
    public void pauseClick() throws IOException {
        SoundPlayer.klickSound();
        root.getChildren().add(PauseMenu.loadScene());
    }

    public static Pane loadScene() throws IOException {
        return new FXMLLoader(TicTacToeView.class.getResource("/cardnight/game-views/tictactoe-view.fxml")).load();
    }
}
