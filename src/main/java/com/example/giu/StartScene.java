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

import java.io.IOException;

public class StartScene extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        //   Image icon = new Image();
        // stage.getIcons().add(icon);
        stage.setTitle("BookWise LMS");
        Group root = new Group();
        Scene scene = new Scene(root, 200, 200);
        prepareLoginButton(root, stage);
        prepareRegisterButton(root, stage);
//        prepareStartButton(root, stage);
//        prepareDescription(root);
//        prepareName(root);
        stage.setScene(scene);
        stage.setFullScreen(true);
        TextField b = new TextField();
       // Label l = new Label("no text");
        EventHandler<ActionEvent> event = new EventHandler<ActionEvent>() {
            public void handle(ActionEvent e)
            {
                //l.setText(b.getText());
            }
        };
        b.setOnAction(event);
       // root.getChildren().add(b);
      //  root.getChildren().add(l);
        stage.show();
        //TextInputPopup.test(stage);
    }

    public static void prepareLoginButton(Group root, Stage stage) {
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
                    Login.logIn(stage);
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