package cardnight;

import javafx.application.Application;
import javafx.scene.PerspectiveCamera;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.PrintWriter;
import java.io.StringWriter;

public class MainController extends Application {

    @Override
    public void start(Stage stage) {

        Thread.setDefaultUncaughtExceptionHandler(MainController::handleExceptions);

        stage.getIcons().add(new Image(getClass().getResourceAsStream("/cardnight/images/Taskbar-Icon.png")));

        Main.mainStage = stage;

        Scene mainScene = new Scene(ScreenController.hautptemue);
        PerspectiveCamera cam = new PerspectiveCamera();
        mainScene.setCamera(cam);
        mainScene.getStylesheets().add(getClass().getResource("/cardnight/styles/background-gradient.css").toExternalForm());
        ScreenController.setScene(mainScene);

        stage.setScene(mainScene);
        stage.setTitle("Card Night");
        stage.show();
    }

    private static void handleExceptions(Thread t, Throwable e) {

        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        e.printStackTrace(pw);
        String sStackTrace = sw.toString(); // stack trace as a string

        Logger.log("Exception in " + t.toString() + "\n" + e.getMessage());
        Logger.log(sStackTrace);
    }
}
