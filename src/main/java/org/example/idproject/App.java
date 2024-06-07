package org.example.idproject;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.example.idproject.view.LoginSceneController;

import java.io.IOException;

public class App extends Application {

    @Override
    public void start(Stage stage) throws IOException {
        stage.setTitle("Welcome to IDProject");
        LoginSceneController loginSceneController = new LoginSceneController(stage);
        FXMLLoader loginScreenLoader = new FXMLLoader(getClass().getResource("login-screen.fxml"));
        loginScreenLoader.setController(loginSceneController);

        if (!loginSceneController.loggedInWithSavedCredentials()) {
            stage.setScene(new Scene(loginScreenLoader.load()));
            stage.show();
        }
    }

    public static void main(String[] args) {
        launch();
    }
}