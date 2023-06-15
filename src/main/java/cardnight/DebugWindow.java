package cardnight;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class DebugWindow {
    private static boolean istOffen;
    private static Stage stage;
    private static final Label logLabel = new Label();

    public static void oeffnen() {

        if (istOffen)
            return;

        istOffen = true;

        VBox root = new VBox();
        root.setMaxSize(Double.NEGATIVE_INFINITY, Double.NEGATIVE_INFINITY);
        root.setAlignment(Pos.TOP_CENTER);

        root.getChildren().add(new Label("Log"));

        ScrollPane scrollPane = new ScrollPane(logLabel);

        root.getChildren().add(scrollPane);

        stage = new Stage();
        stage.setOnCloseRequest((e) -> {
            istOffen = false;
            Main.debugMode = false;
        });
        stage.setTitle("Debug Window");
        stage.setScene(new Scene(root, 450, 450));
        stage.show();
    }

    public static void fuegeLogNachrichtHinzu(String text) {
        logLabel.setText(logLabel.getText() + text + "\n");
    }

    public static void schliessen() {
        if (!istOffen)
            return;
        istOffen = false;
        Main.debugMode = false;
        stage.close();
    }
}
