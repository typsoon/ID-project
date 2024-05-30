package org.example.idproject.view;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.example.idproject.ApplicationLauncher;

import java.io.IOException;

public class MainSceneController {
    public static Stage primaryStage;

    @FXML
    protected void onBrowsePlayersClick() throws IOException {
        FXMLLoader loader = new FXMLLoader(ApplicationLauncher.class.getResource("browse-players.fxml"));
        Scene scene = new Scene(loader.load());

        primaryStage.setScene(scene);
        primaryStage.show();
    }

    @FXML
    protected void onExitButtonClick() {
        Platform.exit();
        System.exit(0);
    }
}
