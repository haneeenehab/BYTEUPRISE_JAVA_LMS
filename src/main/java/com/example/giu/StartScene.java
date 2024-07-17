package com.example.giu;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import model.MYJDBC;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class StartScene extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        try {
            Connection connection = DriverManager.getConnection(
                    "jdbc:mysql://127.0.0.1:3306/lms", "root", "1234"
            );
            MYJDBC.init(connection);
            //   Image icon = new Image();
            // stage.getIcons().add(icon);
            stage.setTitle("BookWise LMS");
            Group root = new Group();
            Scene scene = new Scene(root, 200, 200);
            prepareLoginButton(connection, root, stage);
            prepareRegisterButton(root, stage);
            stage.setScene(scene);
            stage.setFullScreen(true);
            TextField b = new TextField();
            stage.show();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void prepareLoginButton(Connection connection, Group root, Stage stage) {
        Button login = new Button("Login");
        login.setTranslateX(550);
        login.setTranslateY(60);
        login.setPrefSize(200, 100);
        //login.setEffect(shadow);
        login.onMouseClickedProperty();
        root.getChildren().add(login);
        login.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            public void handle(MouseEvent me) {
                try {
                    Login.logIn(connection, stage);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }
    public static void prepareRegisterButton(Group root, Stage stage) {
        Button register = new Button("Register");
        register.setTranslateX(550);
        register.setTranslateY(400);
        register.setPrefSize(200, 100);
        //register.setEffect(shadow);
        register.onMouseClickedProperty();
        root.getChildren().add(register);
     //   StartSceneController.register(register, stage);
    }
    public static void main(String[] args) {
        launch();
    }
}