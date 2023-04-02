package com.example.informatiksoftwareprojekt;

public class GameSelectView {

    public void onTicTacToeClick() {
        ScreenController.activate("game-views.tictactoe-view");
    }

    public void onBackClick() {
        ScreenController.activate("welcome-view");
    }
}
