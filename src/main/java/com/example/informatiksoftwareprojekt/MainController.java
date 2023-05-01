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
        stage.setTitle("Card Night");
        stage.show();

        loadViews();
        ScreenController.activate("main-menu-view");
    }

    private void loadViews() throws IOException {
        ScreenController.addScreen("main-menu-view", new FXMLLoader(getClass().getResource("main-menu-view.fxml")).load());
    }
}
