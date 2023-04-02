package com.example.informatiksoftwareprojekt;

import javafx.scene.Scene;
import javafx.scene.layout.Pane;

import java.util.HashMap;

public class ScreenController {
    private static final HashMap<String, Pane> screenMap = new HashMap<>();
    private static Scene main;

    public static void setScene(Scene scene) {
        main = scene;
    }

    public static void addScreen(String name, Pane pane) {
        screenMap.put(name, pane);
    }

    public static void activate(String name) {
        if (!screenMap.containsKey(name))
            throw new RuntimeException(name + " was not added to screenMap");
        main.setRoot(screenMap.get(name));
    }
}

