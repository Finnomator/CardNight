package cardnight.games.tictactoe;

import cardnight.GameOver;
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

public class TicTacToeView extends SpielView {
    public StackPane root;

    public Text gewinnerText;
    public GridPane tttFeld;

    private TicTacToe ttt;

    public void initialize() {
        ttt = new TicTacToe();

        for (Node node : tttFeld.getChildren()) {
            if (!(node instanceof Button))
                continue;

            Button btn = (Button) node;

            btn.setOnAction(this::btnAction);
        }
    }

    private void btnAction(ActionEvent actionEvent) {
        Button src = (Button) actionEvent.getSource();
        src.setMouseTransparent(true);

        if (ttt.gibSpielerAmZug().istX)
            src.setStyle("-fx-background-image: url('/cardnight/game-views/tictactoe/images/Kreuz.png')");
        else
            src.setStyle("-fx-background-image: url('/cardnight/game-views/tictactoe/images/Kreis.png')");

        if (ttt.istSpielBeendet())
            beendeSpiel();

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
