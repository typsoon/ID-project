package org.example.idproject.view.insertDataScreens;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import org.example.idproject.viewmodel.DataProvider;

public abstract class AbstractInsertDataController {
    protected final DataProvider dataProvider;

    protected AbstractInsertDataController(DataProvider dataProvider) {
        this.dataProvider = dataProvider;
    }

    @FXML
    protected Button addSimpleDataButton;

    @FXML
    abstract void initialize();

    abstract void insertSimpleData();
}
