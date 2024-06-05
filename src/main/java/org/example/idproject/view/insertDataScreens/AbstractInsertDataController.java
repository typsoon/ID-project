package org.example.idproject.view.insertDataScreens;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import org.example.idproject.viewmodel.DatabaseService;

public abstract class AbstractInsertDataController {
    protected final DatabaseService databaseService;

    protected AbstractInsertDataController(DatabaseService databaseService) {
        this.databaseService = databaseService;
    }

    @FXML
    protected Button addSimpleDataButton;

    @FXML
    abstract void initialize();

    abstract void insertSimpleData();
}
