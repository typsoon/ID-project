package org.example.idproject.view;

import javafx.application.Platform;
import javafx.fxml.FXML;


public class MainSceneController {
    private final ScreenManager screenManager;

    @FXML
    protected void onBrowsePlayersClick() {
        screenManager.showBrowsePlayersScreen();
    }

    @FXML
    public void onBrowseClansClick() {
        screenManager.showBrowseClansScreen();
    }

    @FXML
    protected void onExitButtonClick() {
        Platform.exit();
        System.exit(0);
    }

    MainSceneController(ScreenManager screenManager) {
        this.screenManager = screenManager;
    }
}
