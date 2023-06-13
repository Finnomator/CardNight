package cardnight;

import javafx.application.Platform;

public class Logger {

    public static void log(String text) {
        System.out.println(text);
        if (Main.debugMode)
            Platform.runLater(() -> DebugWindow.fuegeLogNachrichtHinzu(text));
    }
}
