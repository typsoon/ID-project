package org.example.idproject.view.insertDataScreens;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import org.example.idproject.viewmodel.ViewModel;

public abstract class AbstractInsertDataController {
    protected final ViewModel viewModel;

    protected AbstractInsertDataController(ViewModel viewModel) {
        this.viewModel = viewModel;
    }

    @FXML
    protected Button addSimpleDataButton;

    @FXML
    abstract void initialize();

    abstract void insertSimpleData();
}
