package org.example.idproject.view;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;


public class MainSceneController {
    private final ScreenManager screenManager;
    public Button browsePlayersButton;
    public Button browseClansButton;
    public Button exitButton;

    @SuppressWarnings("unused")
    @FXML
    private void initialize()
    {
        browsePlayersButton.setOnAction(event -> screenManager.showBrowsePlayersScreen());
        browseClansButton.setOnAction(event -> screenManager.showBrowseClansScreen());
        exitButton.setOnAction(event -> {Platform.exit(); System.exit(0);});
    }

    MainSceneController(ScreenManager screenManager) {
        this.screenManager = screenManager;
    }
}
