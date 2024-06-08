package org.example.idproject.view;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.example.idproject.common.Credentials;
import org.example.idproject.viewmodel.DatabaseService;
import org.example.idproject.core.SimpleDatabaseService;
import org.example.idproject.App;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class LoginSceneController {
    private final Stage primaryStage;
    private final DatabaseService databaseService;

    public LoginSceneController(Stage primaryStage) {
        this.primaryStage = primaryStage;
        databaseService = new SimpleDatabaseService();
    }

    private void displayMainStage() throws IOException {
        ScreenManager screenManager = new ScreenManager(databaseService, primaryStage);

        FXMLLoader managerLoader = new FXMLLoader(App.class.getResource(FXMLAddresses.SCREEN_MANAGER));
        managerLoader.setController(screenManager);


        primaryStage.setScene(new Scene(managerLoader.load()));
        primaryStage.show();
    }

    private boolean captureDataAndTryToLogIn() {
        return databaseService.tryLogIn(new Credentials(usernameField.getText(), passwordField.getText()));
    }

    public boolean loggedInWithSavedCredentials() {
        try {
            Properties credentials = new Properties();
            InputStream stream = App.class.getResourceAsStream("localData/credentials.properties");

            credentials.load(stream);
//            System.out.println(new Credentials(credentials.getProperty("username"), credentials.getProperty("password")));
            if (databaseService.tryLogIn(new Credentials(credentials.getProperty("username"), credentials.getProperty("password")))) {
                displayMainStage();
                return true;
            }
        }
        catch (Exception ignored) {
            throw new RuntimeException(ignored);
        }

        return false;
    }

    @SuppressWarnings("unused")
    @FXML
    private void initialize() {
        logInButton.setOnAction(event -> {
            if (captureDataAndTryToLogIn()) {
                try {
                    displayMainStage();
                } catch (IOException e) {
                    throw new RuntimeException("Failed to load screenManager", e);
                }
            }
        });
    }

    @SuppressWarnings("unused")
    @FXML private TextField usernameField;

    @SuppressWarnings("unused")
    @FXML private PasswordField passwordField;

    @SuppressWarnings("unused")
    @FXML private Button logInButton;
}
