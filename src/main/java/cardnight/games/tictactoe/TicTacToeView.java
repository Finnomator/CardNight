package cardnight.games.tictactoe;

import cardnight.GameOver;
import cardnight.Main;
import cardnight.PauseMenu;
import cardnight.games.SpielView;
import cardnight.games.Spieler;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
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

    private TicTacToe ttt;
    private AtomicBoolean zugGemacht = new AtomicBoolean();
    private AtomicInteger gesetztesFeld = new AtomicInteger();
    private Button[] feldBtns;

    public void initialize() {

        feldBtns = new Button[9];

        ttt = new TicTacToe(this, true);
        Main.setzeAktuellesSpiel(ttt);

        ttt.spielAblauf();

        int i = 0;
        for (Node node : tttFeld.getChildren()) {
            if (!(node instanceof Button))
                continue;

            Button btn = (Button) node;
            feldBtns[i] = btn;

            btn.setUserData(i);
            btn.setOnAction(this::btnAction);
            ++i;
        }
    }

    public void updateUi() {
        String[] feld = ttt.gibFeld();

        for (int i = 0; i < 9; ++i) {
            if (feld[i].equals("x"))
                feldBtns[i].setStyle("-fx-background-image: url('/cardnight/game-views/tictactoe/images/Kreuz.png')");
            else if (feld[i].equals("o"))
                feldBtns[i].setStyle("-fx-background-image: url('/cardnight/game-views/tictactoe/images/Kreis.png')");
        }
    }

    public int warteAufSpielerZug(TicTacToeSpieler spieler) {
        // Gibt zurück, auf welches Feld der Spieler sein Zug macht.

        System.out.println("Warte auf Eingabe von " + spieler.name);

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
    protected void beendeSpiel() {

        Spieler gewinner = ttt.gibGewinner();

        System.out.println("Das Spiel ist vorbei, der Gewinner: " + gewinner.name);

        gewinnerText.setText(gewinner.name + " hat gewonnen");

        try {
            root.getChildren().add(GameOver.loadScene());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void pauseClick() throws IOException {
        root.getChildren().add(PauseMenu.loadScene());
    }

    public static Pane loadScene() throws IOException {
        return new FXMLLoader(TicTacToeView.class.getResource("/cardnight/game-views/tictactoe-view.fxml")).load();
    }
}
