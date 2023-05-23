package cardnight.games.witch.viewcontroller;

import cardnight.GameOver;
import cardnight.PauseMenu;
import cardnight.games.SpielView;
import cardnight.games.witch.Witch;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.concurrent.atomic.AtomicBoolean;

public class WitchView extends SpielView {

    public StackPane root;
    public TextField schaetzungsEingabeFeld;
    public VBox schaetzungsRoot;
    private Witch witch;
    private WitchUiKarte trumpfUiKarte;
    private final AtomicBoolean hatStichSchaetzungBestaetigt = new AtomicBoolean(false);

    public void initialize() throws IOException {

        witch = new Witch(4, this);

        FXMLLoader trumpfKartenLoader = new FXMLLoader(getClass().getResource("/cardnight/game-views/witch/witch-karte.fxml"));
        Node uiTrumpfKarte = trumpfKartenLoader.load();
        root.getChildren().add(uiTrumpfKarte);
        trumpfUiKarte = trumpfKartenLoader.getController();
        numericOnly(schaetzungsEingabeFeld);

        witch.game();
    }

    public int frageNachSchaetzung() {

        while (!hatStichSchaetzungBestaetigt.get())
            Witch.delay(50);

        hatStichSchaetzungBestaetigt.set(false);

        return Integer.parseInt(schaetzungsEingabeFeld.getText());
    }

    public void updateUi() {
        trumpfUiKarte.uiErstellen(witch.gibTrumpfKarte());
    }

    @Override
    public void beendeSpiel() {
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
        return new FXMLLoader(WitchView.class.getResource("/cardnight/game-views/witch-view.fxml")).load();
    }

    public void schaetzungOkKlick() {
        hatStichSchaetzungBestaetigt.set(true);
    }

    public static void numericOnly(final TextField field) {
        field.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*"))
                field.setText(newValue.replaceAll("\\D", ""));
        });
    }
}

