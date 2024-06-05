package org.example.idproject.view.insertDataScreens;

import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import org.example.idproject.viewmodel.DatabaseService;

public class InsertPlayersController extends AbstractInsertDataController {
    public InsertPlayersController(DatabaseService databaseService) {
        super(databaseService);
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
        databaseService.insertPlayer(loginField.getText(), passwordField.getText(), nicknameField.getText());
    }
}
