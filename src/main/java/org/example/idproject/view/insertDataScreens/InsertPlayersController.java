package org.example.idproject.view.insertDataScreens;

import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import org.example.idproject.view.ScreenManager;
import org.example.idproject.viewmodel.DatabaseService;

public class InsertPlayersController extends AbstractInsertDataController {

    @FXML
    TextField loginField;

    @FXML
    PasswordField passwordField;

    @FXML
    TextField nicknameField;

    public InsertPlayersController(DatabaseService databaseService, ScreenManager screenManager) {
        super(databaseService, screenManager);
    }

    @Override
    void initialize() {
        addSimpleDataButton.setOnAction(actionEvent -> insertSimpleData());
    }

    @Override
    void insertSimpleData() {
        try {
            databaseService.insertPlayer(loginField.getText(), passwordField.getText(), nicknameField.getText());
        }
        catch (Exception e) {
            screenManager.displayAlert(e);
        }
    }
}
