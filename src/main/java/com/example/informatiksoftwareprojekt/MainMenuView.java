package com.example.informatiksoftwareprojekt;

public class MainMenuView {

    public void onTicTacToeClick() {
        ScreenController.activate("game-views.tictactoe-view");
    }

    public void onExitClick() {
        System.exit(0);
    }
}
