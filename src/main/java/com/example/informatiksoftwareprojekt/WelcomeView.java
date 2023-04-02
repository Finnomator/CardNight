package com.example.informatiksoftwareprojekt;

import javafx.fxml.FXML;

public class WelcomeView {

    @FXML
    public void onCreditsButtonClick() {
        ScreenController.activate("credits-view");
    }

    public void onExitButtonClick() {
        System.exit(0);
    }

    public void onStartButtonClick() {
        ScreenController.activate("game-select-view");
    }
}
