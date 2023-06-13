package cardnight;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.io.IOException;

public class MainController extends Application {

    @Override
    public void start(Stage stage) throws IOException {

        stage.getIcons().add(new Image(getClass().getResourceAsStream("/cardnight/images/Taskbar-Icon.png")));

        Main.mainStage = stage;

        loadViews();

        Scene mainScene = new Scene(ScreenController.getScreen("main-menu-view"));
        ScreenController.setScene(mainScene);

        stage.setScene(mainScene);
        stage.setTitle("Card Night");
        stage.show();

        ScreenController.activate("main-menu-view");
    }

    private void loadViews() throws IOException {
        ScreenController.addScreen("main-menu-view", new FXMLLoader(getClass().getResource("main-menu-view.fxml")).load());
        ScreenController.addScreen("pause-menu", new FXMLLoader(getClass().getResource("pause-menu.fxml")).load());
    }
}
