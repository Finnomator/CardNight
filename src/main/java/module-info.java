module com.example.informatiksoftwareprojekt {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.informatiksoftwareprojekt to javafx.fxml;
    exports com.example.informatiksoftwareprojekt;
}