package com.example.giu;

import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import model.LibraryException;

import java.sql.Connection;

public class Login {
    public static void logIn(Connection connection, Stage stage) {
        Group root = new Group();
        Scene scene = new Scene(root, 200, 200);
        TextField idBox = new TextField();
        idBox.setPromptText("Enter ID");
        TextField passwordBox = new TextField();
        passwordBox.setPromptText("Enter Password");
        passwordBox.setTranslateY(100);
        prepareLogin(connection, root, idBox, passwordBox, stage);
        root.getChildren().addAll(idBox, passwordBox);
        stage.setScene(scene);
        stage.setFullScreen(true);
        stage.show();
    }

    public static void prepareLogin(Connection connection, Group root, TextField id, TextField pass, Stage stage) {
        Button login = new Button("login");
        login.setTranslateX(550);
        login.setTranslateY(400);
        login.setPrefSize(200, 100);
        root.getChildren().add(login);
        login.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            public void handle(MouseEvent me) {
                try {
                    if (LoginController.verifyLogin(connection, id.getText(), pass.getText()))
                        HomePage.home(connection, stage);
                    else
                        throw new LibraryException("Incorrect email or password");
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        });

    }
}
