package org.example.idproject.view;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.stage.Stage;
import org.example.idproject.App;


public class MainSceneController {

    @FXML
    protected void onBrowsePlayersClick() {
        Stage primaryStage = App.getPrimaryStage();

        primaryStage.setTitle("Browse Players");
        primaryStage.setScene(App.scenesHolder.browsePlayers);
        primaryStage.show();
    }

    @FXML
    public void onBrowseClansClick() {
        Stage primaryStage = App.getPrimaryStage();

        primaryStage.setTitle("Browse Clans");
        primaryStage.setScene(App.scenesHolder.browseClans);
        primaryStage.show();
    }

    @FXML
    protected void onExitButtonClick() {
        Platform.exit();
        System.exit(0);
    }
}
