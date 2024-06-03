package org.example.idproject.view;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.example.idproject.common.Credentials;
import org.example.idproject.viewmodel.SimpleViewModel;
import org.example.idproject.viewmodel.ViewModel;
import org.example.idproject.App;

import java.io.IOException;

public class LoginSceneController {
    private final Stage primaryStage;
    private final ViewModel viewModel;

    public LoginSceneController(Stage primaryStage) {
        this.primaryStage = primaryStage;
        viewModel = new SimpleViewModel();
    }

    private void displayMainStage() throws IOException {
        ScreenManager screenManager = new ScreenManager(viewModel, primaryStage);

        FXMLLoader managerLoader = new FXMLLoader(App.class.getResource("screen-manager.fxml"));
        managerLoader.setController(screenManager);

        primaryStage.setScene(new Scene(managerLoader.load()));
        primaryStage.show();
    }

    private boolean captureDataAndTryToLogIn() {
        return viewModel.tryLogIn(new Credentials(usernameField.getText(), passwordField.getText()));
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

    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Button logInButton;
}
