package com.example.giu;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
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
//        prepareBackground(root);
//        prepareStartButton(root, stage);
//        prepareDescription(root);
//        prepareName(root);
        stage.setScene(scene);
        stage.setFullScreen(true);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}