package com.example.giu;

import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.sql.Connection;

public class HomePage {
    public static void home(Connection connection, Stage stage) {
        Group root = new Group();
        Scene scene = new Scene(root, 200, 200);
        TextField idBox = new TextField();
        idBox.setPromptText("Enter ID");
        TextField passwordBox = new TextField();
        passwordBox.setPromptText("Enter Password");
        passwordBox.setTranslateY(100);
        root.getChildren().addAll(idBox, passwordBox);
        stage.setScene(scene);
        stage.setFullScreen(true);
        stage.show();
    }
}
