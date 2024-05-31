package org.example.idproject;

import javafx.application.Application;
import javafx.stage.Stage;
import org.example.idproject.view.ScenesHolder;
import org.example.idproject.viewmodel.SimpleViewModel;

import java.io.IOException;

public class App extends Application {
    private static Stage primaryStage;
    public static ScenesHolder scenesHolder;

    static {
        try {
            scenesHolder = new ScenesHolder(new SimpleViewModel());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void start(Stage stage) {
        primaryStage = stage;

        stage.setTitle("Welcome to IDProject!");
        stage.setScene(scenesHolder.mainScene);
        stage.show();
    }

    public static Stage getPrimaryStage() { return primaryStage; }

    public static void main(String[] args) {
        launch();
    }
}