package com.example.informatiksoftwareprojekt;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MainController extends Application {

    @FXML
    private Scene mainScene;
    ScreenController screenController;

    @Override
    public void start(Stage stage) throws Exception {

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("main-view.fxml"));
        Scene scene = fxmlLoader.load();

        mainScene = scene;
        screenController = new ScreenController(mainScene);

        stage.setScene(mainScene);
        stage.setTitle("Epic game");
        stage.show();

        screenController.addScreen("welcome-view", new FXMLLoader(WelcomeView.class.getResource("welcome-view.fxml")).load());
        screenController.addScreen("credits-view", new FXMLLoader(WelcomeView.class.getResource("credits-view.fxml")).load());
        screenController.activate("welcome-view");
    }

    @FXML
    void initialize() {
        // System.out.println("Got initialized");
    }
}
