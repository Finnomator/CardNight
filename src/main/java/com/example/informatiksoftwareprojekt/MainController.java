package com.example.informatiksoftwareprojekt;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class MainController extends Application {


    @Override
    public void start(Stage stage) throws IOException {

        Scene mainScene = new FXMLLoader(getClass().getResource("main-view.fxml")).load();
        ScreenController.setScene(mainScene);

        stage.setScene(mainScene);
        stage.setTitle("Epic game");
        stage.show();

        loadViews();
        ScreenController.activate("welcome-view");
    }

    private void loadViews() throws IOException {
        ScreenController.addScreen("welcome-view", new FXMLLoader(getClass().getResource("welcome-view.fxml")).load());
        ScreenController.addScreen("credits-view", new FXMLLoader(getClass().getResource("credits-view.fxml")).load());
        ScreenController.addScreen("game-select-view", new FXMLLoader(getClass().getResource("game-select-view.fxml")).load());
        ScreenController.addScreen("game-views.tictactoe-view", new FXMLLoader(getClass().getResource("/com/example/informatiksoftwareprojekt/game-views/tictactoe-view.fxml")).load());
    }
}
