module com.example.byteuprise_java_lms {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires java.sql;

    opens com.example.giu to javafx.fxml;
    exports com.example.giu;
}