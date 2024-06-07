package org.example.idproject.view.infoPanes;

import javafx.fxml.FXML;
import javafx.scene.text.Text;
import org.example.idproject.common.FullPlayerData;
import org.example.idproject.view.ScreenManager;
import org.example.idproject.viewmodel.DatabaseService;

public class PlayerInfoController extends AbstractInfoController {
    public PlayerInfoController(DatabaseService databaseService, ScreenManager screenManager) {
        super(databaseService, screenManager);
    }

    @FXML private Text currentClanName;

    @FXML private Text currentNickname;

    @Override
    protected void initialize() {

    }

    @Override
    public void update(int id) {
        FullPlayerData fullPlayerData = databaseService.getFullPlayerData(id);

        currentClanName.setText(fullPlayerData.currentClanName() == null ? "" : fullPlayerData.currentClanName());
        currentNickname.setText(fullPlayerData.basicPlayerData().currentNickname());
    }
}
