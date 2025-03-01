package com.example.pixiedixie;

import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) {
        // Set the initial size of the window
        primaryStage.setWidth(925);
        primaryStage.setHeight(575);
        primaryStage.setTitle("PixieDixie");
        primaryStage.setResizable(false);
        ScreenController screenController = new ScreenController(primaryStage);
        screenController.changeScreen("/com/example/pixiedixie/signup.fxml");
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
