package org.example.idproject.view.infoPanes;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import org.example.idproject.common.FriendData;
import org.example.idproject.common.FullPlayerData;
import org.example.idproject.view.ScreenManager;
import org.example.idproject.view.utils.FriendDataTable;
import org.example.idproject.viewmodel.DatabaseService;

import java.io.IOException;


public class PlayerInfoController extends AbstractInfoController {
    public PlayerInfoController(DatabaseService databaseService, ScreenManager screenManager) {
        super(databaseService, screenManager);
    }

    @FXML private Text currentClanName;

    @FXML private Text currentNickname;

    @FXML private Button seeClan;

    @FXML private AnchorPane tablePane;

    @FXML private Button pastNicknames;

    private FullPlayerData fullPlayerData;

    @Override
    protected void initialize() {
        seeClan.setOnAction(event -> {
            if (fullPlayerData != null && fullPlayerData.currentClanId() != null) {
                screenManager.showClanInfo(fullPlayerData.currentClanId());
            }
        });

        pastNicknames.setOnAction(event -> {
            if (fullPlayerData != null) {
                tablePane.getChildren().clear();

                try {
                    TableView<FriendData> friendsTable = new FriendDataTable().getTableView();


                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }

    @Override
    public void update(int id) {
        try {
            fullPlayerData = databaseService.getFullPlayerData(id);

            currentClanName.setText(fullPlayerData.currentClanName() == null ? "" : fullPlayerData.currentClanName());
            currentNickname.setText(fullPlayerData.basicPlayerData().currentNickname());
        }
        catch (Exception e) {
            screenManager.displayAlert(e);
        }
    }
}
