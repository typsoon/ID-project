package org.example.idproject.view.insertDataScreens;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import org.example.idproject.view.ScreenManager;
import org.example.idproject.viewmodel.DatabaseService;

public abstract class AbstractInsertDataController {
    protected final DatabaseService databaseService;
    protected final ScreenManager screenManager;

    protected AbstractInsertDataController(DatabaseService databaseService, ScreenManager screenManager) {
        this.databaseService = databaseService;
        this.screenManager = screenManager;
    }

    @FXML
    protected Button addSimpleDataButton;

    @FXML
    abstract void initialize();

    abstract void insertSimpleData();
}
