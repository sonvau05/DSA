module com.example.personalbudget {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;

    requires org.controlsfx.controls;
    requires org.kordamp.ikonli.javafx;
    requires jdk.compiler;

    opens com.example.personalbudget to javafx.fxml;
    exports com.example.personalbudget;
}