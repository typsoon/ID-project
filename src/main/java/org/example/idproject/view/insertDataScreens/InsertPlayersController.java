package org.example.idproject.view.insertDataScreens;

import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import org.example.idproject.viewmodel.ViewModel;

public class InsertPlayersController extends AbstractInsertDataController {
    public InsertPlayersController(ViewModel viewModel) {
        super(viewModel);
    }

    @FXML
    TextField loginField;

    @FXML
    PasswordField passwordField;

    @FXML
    TextField nicknameField;

    @Override
    void initialize() {
        addSimpleDataButton.setOnAction(actionEvent -> insertSimpleData());
    }

    @Override
    void insertSimpleData() {
        viewModel.insertPlayer(loginField.getText(), passwordField.getText(), nicknameField.getText());
    }
}
